// Process scheduling - shortest job first
#include <stdio.h>
#include <stdlib.h>
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
  while ( cur && cur->iBurstTime < proc->iBurstTime) {
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

int main(int argc, char **argv) {
  struct process *head = NULL;
  struct timeval *start = malloc(sizeof(struct timeval)),
    *end = malloc(sizeof(struct timeval));

  for (int i=0; i < NUMBER_OF_PROCESSES; i++) {
    listInsert(&head, generateProcess());
  }

  int totResponse = 0, totTurnaround = 0;
  do {
    int oldBurstTime = head->iBurstTime;
    simulateSJFProcess(head, start, end);

    int response = getDifferenceInMilliSeconds(head->oTimeCreated, *start);
    totResponse += response;

    int turnaround = getDifferenceInMilliSeconds(*start, *end);
    totTurnaround += turnaround;

    printf("Process Id = %d, Previous Burst Time = %d, New Burst Time = %d, Response Time = %d, "
      "Turn Around Time = %d\n",
      head->iProcessId, oldBurstTime, head->iBurstTime,
      response, turnaround);

    listRemove(&head);
  } while(head);

  printf("Average response time = %lf\nAverage turn around time = %lf\n",
    (double) totResponse /NUMBER_OF_PROCESSES, (double) totTurnaround /NUMBER_OF_PROCESSES);

  return 0;
}
