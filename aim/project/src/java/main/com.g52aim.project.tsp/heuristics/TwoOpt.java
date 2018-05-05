package com.g52aim.project.tsp.heuristics;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.g52aim.project.tsp.interfaces.HeuristicInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;

/**
 *
 * @author Warren G. Jackson
 *
 */
public class TwoOpt extends HeuristicOperators implements HeuristicInterface {

	@SuppressWarnings("serial")
	private TreeMap<Double,Integer> iomMap = new TreeMap<Double,Integer>() {{
		put(.0, 1);
		put(.2, 2);
		put(.4, 3);
		put(.6, 4);
		put(.8, 5);
		put(1.0, 6);
	}};

	public TwoOpt(Random random) {

		super(random);
	}

	@Override
	public double apply(TSPSolutionInterface solution, double dos, double iom) {

		Integer[] repr = solution.getSolutionRepresentation().getRepresentationOfSolution();
		double value = solution.getObjectiveFunctionValue();
		int iters = getNumberOfMutations(iom);

		for (int i = 0; i < iters; i++) {
			// choose start/end of subsection
			int a = random.nextInt(repr.length -1);
			int b = a +random.nextInt(repr.length -a -1) +1;

			// subtract cost between first city in selection and city before selection
			value -= f.getCost(repr[a], repr[Math.floorMod(a -1, repr.length)]);
			// subtract cost between last city in selection and city after selection
			value -= f.getCost(repr[b], repr[(b +1) % repr.length]);

			List<Integer> subsection = Arrays.asList(repr).subList(a, b+1);
			Collections.reverse(subsection);

			// add cost between first city in selection and city before selection
			value += f.getCost(repr[a], repr[Math.floorMod(a -1, repr.length)]);
			// add cost between last city in selection and city after selection
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
