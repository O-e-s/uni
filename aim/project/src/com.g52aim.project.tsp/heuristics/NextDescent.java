package com.g52aim.project.tsp.heuristics;


import java.util.Random;

import com.g52aim.project.tsp.interfaces.HeuristicInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;


/**
 * @author Warren G. Jackson
 * Performs adjacent swap, returning the first solution with strict improvement
 */
public class NextDescent extends HeuristicOperators implements HeuristicInterface {

	public NextDescent(Random random) {

		super(random);
	}

	@Override
	public double apply(TSPSolutionInterface solution, double dos, double iom) {

		int iters = getDepthOfSearch(iom);
		Integer[] repr = solution.getSolutionRepresentation().getRepresentationOfSolution();
		double current = solution.getObjectiveFunctionValue(),
					 candidate;

		for (int i = 0; i < iters; i++) {

			int start = random.nextInt(repr.length);
			for (int j = 0; j < repr.length; j++) {

				int index = (start + j) %repr.length;
				candidate = current + adjacentSwap(repr, index);

				if (candidate < current) {
					current = candidate;
					break;
				} else {
					adjacentSwap(repr, index, false);
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
