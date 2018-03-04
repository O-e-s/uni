
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

    int bestIndex = Integer.MAX_VALUE,
        worstIndex = -1;
    double worstFitness = 0;

    for (int i=0; i < (populationSize -1) *2; i++) {
      double fitness = problem.getObjectiveFunctionValue(i);
      if (fitness == problem.getBestSolutionValue()) {
        // does getBest include problems past POPULATION_SIZE?
        // find best index
        bestIndex = i;
      } else if (fitness >= worstFitness && i >= populationSize) {
        // Track worst child
        worstIndex = i;
        worstFitness = fitness;
      }

      if (i >= populationSize) {
        // Place children into new population
        population[i -populationSize] = i;
      }
    }

    if (bestIndex < populationSize) {
      // Replace worst child with best (in parents)
      population[worstIndex -populationSize] = bestIndex;
    }

		return population;
	}

}
