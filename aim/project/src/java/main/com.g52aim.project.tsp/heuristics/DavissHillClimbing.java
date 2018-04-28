package com.g52aim.project.tsp.heuristics;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.g52aim.project.tsp.heuristics.AdjacentSwap;
import com.g52aim.project.tsp.interfaces.HeuristicInterface;
import com.g52aim.project.tsp.interfaces.ObjectiveFunctionInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;


/**
 *
 * @author Warren G. Jackson
 * Performs adjacent swap, returning the first solution with strict improvement
 *
 */
public class DavissHillClimbing extends HeuristicOperators implements HeuristicInterface {

	public DavissHillClimbing(Random random) {
		super(random);
	}

	@Override
	public double apply(TSPSolutionInterface solution, double dos, double iom) {

		double current = solution.getObjectiveFunctionValue(),
					 candidate;
		int[] repr = solution.getSolutionRepresentation().getRepresentationOfSolution();
		int iters = getDepthOfSearch(dos);

		// shuffle current perturbation, attempt adjacent swaps in the order of
		// those indices

		for (int i = 0; i < iters; i++) {
			List<Integer> perm = IntStream.range(0, repr.length -1).boxed()
				.collect(Collectors.toList());
			Collections.shuffle(perm, random);

			for (int k : perm) {
				candidate = current + adjacentSwap(repr, k);

				if (candidate <= current) {
					current = candidate;
				} else {
					// undo swap
					adjacentSwap(repr, k, false);
				}
			}
		}

		solution.setObjectiveFunctionValue(current);
		return current;
	}

	@Override
	public boolean isCrossover() {

		return false;
	}

	@Override
	public boolean usesIntensityOfMutation() {

		return false;
	}

	@Override
	public boolean usesDepthOfSearch() {

		return true;
	}
}
