package com.g52aim.lab04;

import java.util.Random;
import java.util.Arrays;

import g52aim.domains.chesc2014_SAT.SAT;

/**
 * @author Warren G. Jackson
 */
public class TournamentSelection {

	private Random rng;
	private int POPULATION_SIZE;
	private SAT problem;

	public TournamentSelection(SAT problem, Random rng, int POPULATION_SIZE) {

		this.problem = problem;
		this.rng = rng;
		this.POPULATION_SIZE = POPULATION_SIZE;
	}

	/**
	  * @return The index of the chosen parent solution.
	  *
	  * PSEUDOCODE
	  *
	  * INPUT: parent_pop, tournament_size
	  * solutions = getUniqueRandomSolutions(tournament_size);
	  * bestSolution = getBestSolution(solutions);
	  * index = indexOf(bestSolution);
	  * return index;
	  */
	public int tournamentSelection(int tournamentSize) {

		int bestIndex = -1;
		double bestFitness = Double.MAX_VALUE;

    // Insert tournamentSize problem indices into array
    // Randomly choose from population, check it's not already in array
    int[] tournament = new int[tournamentSize];
    int i = 0;
    while (tournament.length < tournamentSize) {
      int randomSolution = rng.nextInt(POPULATION_SIZE);
      if (Arrays.asList(tournament).contains(randomSolution))
        continue;

      tournament[i++] = randomSolution;
    }

    for (int index : tournament) {
      double fitness = problem.getObjectiveFunctionValue(index);
      if (fitness < bestFitness) {
        bestIndex = index;
        bestFitness = fitness;
      }
    }

		return bestIndex;
	}
}
