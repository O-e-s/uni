package com.g52aim.lab04;

import java.util.Random;

import g52aim.domains.chesc2014_SAT.SAT;
import g52aim.satheuristics.genetics.CrossoverHeuristic;
import g52aim.satheuristics.genetics.PopulationHeuristic;
import g52aim.satheuristics.genetics.PopulationReplacement;
import g52aim.searchmethods.PopulationBasedSearchMethod;

/**
 * Memetic Algorithm ( local search should to be added per the report exercise ).
 *
 * @author Warren G. Jackson
 *
 */
public class MemeticAlgorithm extends PopulationBasedSearchMethod {

	private final CrossoverHeuristic crossover;
	private final PopulationHeuristic mutation;
	private final PopulationHeuristic localSearch;
	private final PopulationReplacement replacement;
	private final TournamentSelection selection;

	public MemeticAlgorithm(SAT problem, Random rng, int populationSize, CrossoverHeuristic crossover,
			PopulationHeuristic mutation, PopulationHeuristic localSearch, PopulationReplacement replacement) {

		super(problem, rng, populationSize);

		this.crossover = crossover;
		this.mutation = mutation;
		this.localSearch = localSearch;
		this.replacement = replacement;
		this.selection = new TournamentSelection(problem, rng, populationSize);
	}

	/**
	  * Memetic Algorithm pseudocode
	  * Note there is no exact pseudocode since the purpose of this
	  * exercise is that you experiment with applying local search
	  * in different places of the MA.
	  *
	  * BASIC PSEUDO CODE (GA) not MA
	  * (population already initialised)
	  *
	  * FOR 0 -> populationSize / 2
	  *		select parents using tournament selection
	  *     apply crossover to generate offspring
	  *     apply mutation to offspring
	  * ENDFOR
	  *
	  * do population replacement
	  *
	  */
	public void runMainLoop() {
    // Track index of last child
    int lastChild = POPULATION_SIZE -1;

    for (int i=0; i < POPULATION_SIZE / 2; i++) {
      int parent1, parent2;
      // parent selection
      parent1 = tournamentSelection(3);
      parent2 = tournamentSelection(3);

      // crossover
      crossover.applyHeuristic(parent1, parent2,
        lastChild +1, lastChild +2);

      // child mutation
      mutation.applyHeuristic(lastChild +1);
      mutation.applyHeuristic(lastChild +2);

      // local search on children
      localSearch.applyHeuristic(lastChild +1);
      localSearch.applyHeuristic(lastChild +2);

      // Increment last child index
      lastChild += 2;
    }

    // population replacement
    replacement.doReplacement(problem, POPULATION_SIZE);
	}

	public int tournamentSelection(int tournamentSize) {

		return selection.tournamentSelection(tournamentSize);
	}

	@Override
	public String toString() {

		return "Memetic Algorithm";
	}
}
