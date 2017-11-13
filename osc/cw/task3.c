// Process scheduling - shortest job first
#include <stdio.h>
#include <stdlib.h>
#include <semaphore.h>
#include <pthread.h>
#include "coursework.h"

// Insert process in order of ascending duration
void listInsert(struct process **head, struct process *proc) {
  // If no head or head is >=, replace it
  if (*head == NULL || (*head)->iBurstTime >= proc->iBurstTime) {
    proc->oNext = *head;
    *head = proc;
    return;
  }

  // Loop until end or current has greater iBurstTime
  struct process *prev = NULL, *cur = *head;
  while ( cur && (cur)->iBurstTime < proc->iBurstTime) {
    prev = cur;
    cur = cur->oNext;
  }

  // Insert between current and previous link
  proc->oNext = cur;
  prev->oNext = proc;
}

// Remove head of list
void listRemove(struct process **head) {
  struct process *temp = *head;
  *head = (*head)->oNext;
  free(temp);
}

sem_t canProduce, canConsume;
pthread_mutex_t sync;
struct process *head = NULL; // shared pointer to head of list

void *producer(void *arg) {
  for (int i=0; i < NUMBER_OF_PROCESSES; i++) {
    struct process* proc = generateProcess();
    sem_wait(&canProduce);
    pthread_mutex_lock(&sync);

    listInsert(&head, proc);

    pthread_mutex_unlock(&sync);
    sem_post(&canConsume);
  }

  // Wake up consumers again to allow them to exit
  sem_post(&canConsume);

  return NULL;
}

int totResponse = 0, totTurnaround = 0;
void *consumer(void *arg) {
  struct timeval *start = malloc(sizeof(struct timeval)),
    *end = malloc(sizeof(struct timeval));
  int consumerID = *(int*) arg;

  while (1) {
    sem_wait(&canConsume);
    pthread_mutex_lock(&sync);

    // Receive NULL = wake up next consumer and terminate
    if (!head) {
      pthread_mutex_unlock(&sync);
      sem_post(&canConsume);
      return NULL;
    }

    int oldBurstTime = head->iBurstTime;
    simulateSJFProcess(head, start, end);

    long int response, turnaround;
    response = getDifferenceInMilliSeconds(head->oTimeCreated, *start);
    totResponse += response;

    turnaround = getDifferenceInMilliSeconds(*start, *end);
    totTurnaround += turnaround;

    printf("Consumer Id = %d, Process Id = %d, Previous Burst Time = %d, New Burst Time = %d, "
      "Response Time = %li, Turn Around Time = %li\n",
      consumerID, head->iProcessId, oldBurstTime, head->iBurstTime,
      response, turnaround);

    listRemove(&head);
    pthread_mutex_unlock(&sync); // exit critial section
    sem_post(&canProduce); // new space in buffer
  }

  return NULL;
}

int main(int argc, char **argv) {
  // Producer semaphore starts at BUFFER_SIZE to track if full
  sem_init(&canProduce, 0, BUFFER_SIZE);

  pthread_t producerT, consumerT[NUMBER_OF_CONSUMERS];
  pthread_create(&producerT, NULL, &producer, NULL);
  for (int i = 0; i < NUMBER_OF_CONSUMERS; i++) {
    pthread_create(&consumerT[i], NULL, &consumer, (int *) &i);
  }

  pthread_join(producerT, NULL);
  for (int i = 0; i < NUMBER_OF_CONSUMERS; i++) {
    pthread_join(consumerT[i], NULL);
  }

  printf("Average response time = %lf\nAverage turn around time = %lf\n",
    (double) totResponse /NUMBER_OF_PROCESSES, (double) totTurnaround /NUMBER_OF_PROCESSES);

  return 0;
}