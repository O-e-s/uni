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

		// pick two points
		// take out the inclusive section
		// reverse that section
		// reinsert
		int[] repr = solution.getSolutionRepresentation().getRepresentationOfSolution();

		int iters = getNumberOfMutations(iom);

		for (int i = 0; i < iters; i++) {
			int a, b;
			// choose start/end of subsection
			a = random.nextInt(repr.length);
			b = a + random.nextInt(repr.length - a);

			// Copy subsection and reverse it
			List<Integer> subsection = IntStream.of(Arrays.copyOfRange(repr, a, b+1))
				.boxed().collect(Collectors.toList());
			Collections.reverse(subsection);

			// Replace subsection
			System.arraycopy(subsection.stream().mapToInt(x -> x).toArray(),
				0, repr, a, b -a +1);
		}

		return -1;
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
