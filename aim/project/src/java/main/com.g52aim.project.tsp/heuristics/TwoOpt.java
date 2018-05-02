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

		int iters = getNumberOfMutations(iom);

		for (int i = 0; i < iters; i++) {
			int a, b;
			// choose start/end of subsection
			a = random.nextInt(repr.length -1);
			b = a +random.nextInt(repr.length - a -1) +1;

			// Copy subsection and reverse it
			List<Integer> subsection = Arrays.asList(repr).subList(a, b+1);
			Collections.reverse(subsection);

			// Replace subsection
			System.arraycopy(subsection.toArray(), 0, repr, a, b -a +1);
		}

		double value = f.getObjectiveFunctionValue(solution.getSolutionRepresentation());
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
