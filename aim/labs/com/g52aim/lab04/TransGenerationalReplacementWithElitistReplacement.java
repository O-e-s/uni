
package com.g52aim.lab04;

import g52aim.domains.chesc2014_SAT.SAT;
import g52aim.satheuristics.genetics.PopulationReplacement;

/**
 * Trans-generational Replacement with Elitism
 * @author Warren G. Jackson
 *
 */
public class TransGenerationalReplacementWithElitistReplacement extends PopulationReplacement {

	/**
	 * Replaces the current population with the offspring and replaces the worst
	 * offspring with the best solution if the best is not contained in the offspring.
	 *
	 * @return The indices of the solutions to use in the next generation.
	 *
	 * PSEUDOCODE
	 *
	 * INPUT current_pop, offspring_pop
	 * fitnesses <- evaluate( current_pop U offspring_pop );
	 * best <- min( fitnesses );
	 * next_pop <- indicesOf( offspring_pop );
	 * IF best \notin offspring_pop THEN
	 *     next_pop.replace( worst, best );
	 * ENDIF
	 * OUTPUT: next_pop; // return the indices of the next population
	 */
	@Override
  protected int[] getNextGeneration(SAT problem, int populationSize) {

		int[] population = new int[populationSize];
		int worstIndex = populationSize-1;
		int bestIndex = -1;

		// TODO implementation of replacement scheme
		for(int i = populationSize; i < populationSize*2; i++)
		{
			population[i-populationSize] = i;
		}

		for(int i = 0; i < populationSize; i++)
		{
			if(problem.getObjectiveFunctionValue(i) == problem.getBestSolutionValue()) {
				bestIndex = i;
			}
			if(problem.getObjectiveFunctionValue(i+populationSize) > problem.getObjectiveFunctionValue(worstIndex)) {
				worstIndex = i;
			}
		}

		if(bestIndex < populationSize && bestIndex >= 0 && worstIndex != populationSize-1)
		{
			population[worstIndex] = bestIndex;
		}

		return population;
	}

}
