package com.g52aim.project.tsp.heuristics;

import java.util.Random;

import com.g52aim.project.tsp.interfaces.HeuristicInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;

/**
 *
 * @author Warren G. Jackson
 *
 */
public class AdjacentSwap extends HeuristicOperators implements HeuristicInterface {

	public AdjacentSwap(Random random) {

		super(random);
	}

	@Override
	public double apply(TSPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {

    double value = solution.getObjectiveFunctionValue();
    int iters = getNumberOfMutations(intensityOfMutation);
    int[] repr = solution.getSolutionRepresentation().getRepresentationOfSolution();
    for (int i = 0; i < iters; i++) {
      int a = random.nextInt(repr.length),
        b, temp;
      // wrap around chosen index +1
      b = (a +1) % repr.length;

      // subtract distance before and after pair from value
      value -= f.getCost(repr[a], repr[Math.floorMod(a -1, repr.length)]);
      value -= f.getCost(repr[b], repr[(b +1) % repr.length]);

      // swap
      temp = repr[a];
      repr[a] = repr[b];
      repr[b] = temp;

      // add new distance before and after pair
      value += f.getCost(repr[a], repr[Math.floorMod(a -1, repr.length)]);
      value += f.getCost(repr[b], repr[(b +1) % repr.length]);
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
