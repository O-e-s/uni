package com.g52aim.project.tsp.heuristics;

import java.util.Random;

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

		double current = solution.getObjectiveFunctionValue();
		double candidate = current;
		int iters = getDepthOfSearch(dos);
		int[] repr = solution.getSolutionRepresentation().getRepresentationOfSolution();

		for (int i = 0; i < iters; i++) {
			int a = random.nextInt(repr.length),
				b, temp;
			// wrap around chosen index +1
			b = (a +1) % repr.length;

			// subtract distance before and after pair from candidate
			candidate -= f.getCost(repr[a], repr[Math.floorMod(a -1, repr.length)]);
			candidate -= f.getCost(repr[b], repr[(b +1) % repr.length]);

			// swap
			temp = repr[a];
			repr[a] = repr[b];
			repr[b] = temp;

			// add new distance before and after pair
			candidate += f.getCost(repr[a], repr[Math.floorMod(a -1, repr.length)]);
			candidate += f.getCost(repr[b], repr[(b +1) % repr.length]);

			if (candidate <= current) {
				current = candidate;
			} else {
				// undo swap
				temp = repr[a];
				repr[a] = repr[b];
				repr[b] = temp;
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
