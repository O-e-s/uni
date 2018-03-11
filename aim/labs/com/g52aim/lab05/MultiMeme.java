package com.g52aim.lab05;

import java.util.Random;

import g52aim.domains.chesc2014_SAT.Meme;
import g52aim.domains.chesc2014_SAT.SAT;
import g52aim.satheuristics.genetics.CrossoverHeuristic;
import g52aim.satheuristics.genetics.PopulationHeuristic;
import g52aim.satheuristics.genetics.PopulationReplacement;
import g52aim.searchmethods.PopulationBasedSearchMethod;

public class MultiMeme extends PopulationBasedSearchMethod {

	/**
	 * The innovation rate setting
	 */
	private final double innovationRate;

	private final CrossoverHeuristic crossover;
	private final BitMutation mutation;
	private final PopulationReplacement replacement;
	private final TournamentSelection selection;

	private final MemeplexInheritanceMethod inheritance;

	/**
	 * The possible local search operators to use.
	 */
	private final PopulationHeuristic[] lss;

	// Constructor used for testing. Please do not remove!
	public MultiMeme(SAT problem, Random rng, int populationSize, double innovationRate, CrossoverHeuristic crossover,
			BitMutation mutation, PopulationReplacement replacement, TournamentSelection selection, MemeplexInheritanceMethod inheritance,
			PopulationHeuristic[] lss) {

		super(problem, rng, populationSize);

		this.innovationRate = innovationRate;
		this.crossover = crossover;
		this.mutation = mutation;
		this.replacement = replacement;
		this.selection = selection;
		this.inheritance = inheritance;
		this.lss = lss;
	}

	public MultiMeme(SAT problem, Random rng, int populationSize, double innovationRate) {

		this(
			problem,
			rng,
			populationSize,
			innovationRate,
			new PTX1(problem, rng), // crossover
			new BitMutation(problem, rng), // mutation
			new TransGenerationalReplacementWithElitism(), // replacement
			new TournamentSelection(populationSize, rng, problem), // parent selection
			new SimpleInheritanceMethod(problem, rng), // memeplex inheritance
			new PopulationHeuristic[] { // create mapping for local search operators used for meme in meme index 1
				new DBHC_OI(problem, rng), // [0]
				new DBHC_IE(problem, rng), // [1]
				new SDHC_OI(problem, rng), // [2]
				new SDHC_IE(problem, rng)  // [3]
			}
		);
	}

	/**
	 * MMA PSEUDOCODE:
	 *
	 * INPUT: PopulationSize, MaxGenerations, InnovationRate
	 *
	 * generateInitialPopulation();
	 * FOR 0 -> MaxGenerations
	 *
	 ####### BEGIN IMPLEMENTING HERE #######
	 *     FOR 0 -> PopulationSize / 2
	 *         select parents using tournament selection with tournament size = 3
	 *         apply crossover to generate offspring
	 *         inherit memeplex using simple inheritance method
	 *         mutate the memes within each memeplex of each child with  probability dependent on the innovation rate
	 *         apply mutation to offspring with intensity of mutation set for each solution dependent on its meme option
	 *         apply local search to offspring with choice of operator dependent on each solution's meme option
	 *     ENDFOR
	 *     do population replacement
	 ####### STOP IMPLEMENTING HERE #######
	 * ENDFOR
	 * return s_best;
	 */
	public void runMainLoop() {
		int lastChild = POPULATION_SIZE;
		for (int i=0; i < POPULATION_SIZE / 2; i++) {
			int parent1, parent2;
			int child1 = lastChild++;
			int child2 = lastChild++;

			// select parents
			parent1 = selection.tournamentSelection(3);
			parent2 = selection.tournamentSelection(3);

			// apply crossover
			crossover.applyHeuristic(parent1, parent2, child1, child2);

			// inherit memeplex
			inheritance.performMemeticInheritance(parent1, parent2, child1, child2);

			// mutate the memes within each memeplex
			performMutationOfMemeplex(child1);
			performMutationOfMemeplex(child2);

			// apply mutation to offspring
			applyMutationForChildDependentOnMeme(child1, 0);
			applyMutationForChildDependentOnMeme(child2, 0);

			// apply local search to offspring
			applyLocalSearchForChildDependentOnMeme(child1, 1);
			applyLocalSearchForChildDependentOnMeme(child2, 1);
		}

    replacement.doReplacement(problem, POPULATION_SIZE);
	}

	/**
	 * Applies mutation to the child dependent on its current meme option for mutation.
	 * Mapping of meme option to IOM: IntensityOfMutation <- memeOption
	 *
	 * @param childIndex The solution memory index of the child to mutate.
	 * @param memeIndex The meme index used for storing the meme relating to the intensity of mutation setting.
	 */
	public void applyMutationForChildDependentOnMeme(int childIndex, int memeIndex) {
		int rate = problem.getMeme(childIndex, memeIndex).getMemeOption();
		mutation.setMutationRate(rate);
		mutation.applyHeuristic(childIndex);
	}

	/**
	 * Applies the local search operator to the child as specified by its current meme option.
	 *
	 * @param childIndex The solution memory index of the child to mutate.
	 * @param memeIndex The meme index used for storing the meme relating to the local search operator setting.
	 */
	public void applyLocalSearchForChildDependentOnMeme(int childIndex, int memeIndex) {
		int search = problem.getMeme(childIndex, memeIndex).getMemeOption();
		lss[search].applyHeuristic(childIndex);
	}

	/**
	 * Applies mutation to each meme within the memeplex of the specified solution with probability
	 * dependent on the innovation rate.
	 *
	 * @param solutionIndex The solution memory index of the solution to mutate the memeplex of.
	 */
	public void performMutationOfMemeplex(int solutionIndex) {
		for (int i = 0; i < problem.getNumberOfMemes(); i++) {
			if (rng.nextDouble() >= innovationRate) continue;
      Meme meme = problem.getMeme(solutionIndex, i);
			meme.setMemeOption(
          rng.nextInt(meme.getTotalOptions()));
		}
	}

	public String toString() {

		return "Multimeme Memetic Algorithm";
	}

}
