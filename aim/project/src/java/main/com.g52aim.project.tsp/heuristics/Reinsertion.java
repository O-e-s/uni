package com.g52aim.project.tsp.heuristics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

import com.g52aim.project.tsp.interfaces.HeuristicInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;

/**
 *
 * @author Warren G. Jackson
 *
 */
public class Reinsertion extends HeuristicOperators implements HeuristicInterface {

	public Reinsertion(Random random) {

		super(random);
	}

	@Override
	public double apply(TSPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {

		// convert representation to list for convenience
		int[] array = solution.getSolutionRepresentation().getRepresentationOfSolution();
		ArrayList<Integer> repr = new ArrayList<>(array.length);
		for (int i : array) {
			repr.add(i);
		}

    double value = solution.getObjectiveFunctionValue();
    int iters = getNumberOfMutations(intensityOfMutation);
		int cities = repr.size();

    for (int i = 0; i < iters; i++) {
      int a = random.nextInt(cities),
        	b = -1, temp;

      // subtract distance before and after removed city
      value -= f.getCost(repr.get(a), repr.get(Math.floorMod(a -1, cities)));
      value -= f.getCost(repr.get(a), repr.get((a +1) % cities));

			do {
				b = random.nextInt(cities);
			} while (a == b);

      // subtract distance between city at insertion point and previous
      value -= f.getCost(repr.get(b), repr.get(Math.floorMod(b -1, cities)));

			// reinsert
			temp = repr.get(a);
			repr.remove(a);
			repr.add(b, temp);

      // add distance between new city and previous, and next city
      value += f.getCost(repr.get(b), repr.get(Math.floorMod(b -1, cities)));
      value += f.getCost(repr.get(b), repr.get((b +1) % cities));
    }

		solution.getSolutionRepresentation().setRepresentationOfSolution(
			repr.stream().mapToInt(i->i).toArray());
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
