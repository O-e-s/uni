package com.g52aim.project.tsp.heuristics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

import com.g52aim.project.tsp.interfaces.HeuristicInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;

/**
 * @author Warren G. Jackson
 */
public class Reinsertion extends HeuristicOperators implements HeuristicInterface {

	public Reinsertion(Random random) {

		super(random);
	}

	@Override
	public double apply(TSPSolutionInterface solution, double dos, double iom) {

		// convert representation to list for convenience
		Integer[] array = solution.getSolutionRepresentation().getRepresentationOfSolution();
		ArrayList<Integer> repr = new ArrayList<>(Arrays.asList(array));

		double value = solution.getObjectiveFunctionValue();
		int iters = getNumberOfMutations(iom);
		for (int i = 0; i < iters; i++) {
			int a = random.nextInt(array.length -1),
					b = a + random.nextInt(array.length -a -1) +1,
					temp;

			int beforeA = Math.floorMod(a -1, array.length);
			int afterA = (a +1) % array.length;
			int beforeB = Math.floorMod(b -1, array.length);
			int afterB = (b +1) % array.length;

			// subtract cost before and after removed city, add cost between
			// both before and after
			value -= f.getCost(repr.get(a), repr.get(beforeA));
			value -= f.getCost(repr.get(a), repr.get(afterA));
			value += f.getCost(repr.get(beforeA), repr.get(afterA));

			// reinsert
			temp = repr.remove(a);
			repr.add(b, temp);

			// subtract cost at insertion point, add cost before and after inserted
			// city both before and after
			value -= f.getCost(repr.get(beforeB), repr.get(afterB));
			value += f.getCost(repr.get(b), repr.get(beforeB));
			value += f.getCost(repr.get(b), repr.get(afterB));
		}

		repr.toArray(array);
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
