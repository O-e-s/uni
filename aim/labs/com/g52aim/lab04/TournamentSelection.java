package com.g52aim.lab04;

import java.util.Random;
import java.util.Arrays;

import g52aim.domains.chesc2014_SAT.SAT;
import g52aim.helperfunctions.ArrayMethods;

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

		// TODO implementation of tournament selection with controllable tournament size
		int[] solutions = new int[tournamentSize];
		int[] pop = new int[POPULATION_SIZE];
		//---------------
		for(int i = 0; i < POPULATION_SIZE; i++)
		{
			pop[i] = i;
		}

		pop = ArrayMethods.shuffle(pop, rng);

		for(int i = 0; i < tournamentSize; i++)
		{
			solutions[i] = pop[i];
		}


		for(int i = 0; i < tournamentSize; i++) {
			if(problem.getObjectiveFunctionValue(solutions[i]) < bestFitness) {
				bestIndex = solutions[i];
				bestFitness = problem.getObjectiveFunctionValue(solutions[i]);
			}
		}

		return bestIndex;
	}
}
