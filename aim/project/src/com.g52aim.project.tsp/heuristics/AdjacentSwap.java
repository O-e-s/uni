package com.g52aim.project.tsp.heuristics;

import java.util.Random;

import com.g52aim.project.tsp.interfaces.HeuristicInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;

/**
 * @author Warren G. Jackson
 */
public class AdjacentSwap extends HeuristicOperators implements HeuristicInterface {

	public AdjacentSwap(Random random) {

		super(random);
	}

	@Override
	public double apply(TSPSolutionInterface solution, double dos, double iom) {

    double value = solution.getObjectiveFunctionValue();
    int iters = getNumberOfMutations(iom);
    Integer[] repr = solution.getSolutionRepresentation().getRepresentationOfSolution();
    for (int i = 0; i < iters; i++) {
      value += adjacentSwap(repr,
        (random.nextInt(repr.length) + i) %repr.length);
    }

    solution.setObjectiveFunctionValue(value);
		return value;
	}

	@Override
	public boolean isCrossover() {

		return false;
	}

	@Override
	public boolean usesIntensityOfMutation() {

		return true;
	}

	@Override
	public boolean usesDepthOfSearch() {

		return false;
	}

}
