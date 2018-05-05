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

			// reinsert
			temp = repr.get(a);
			repr.remove(a);
			repr.add(b, temp);
		}

		solution.getSolutionRepresentation().setRepresentationOfSolution(
			repr.toArray(array));
		value = f.getObjectiveFunctionValue(solution.getSolutionRepresentation());
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
