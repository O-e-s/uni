package com.g52aim.project.tsp.heuristics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;
import com.g52aim.project.tsp.interfaces.XOHeuristicInterface;

/**
 * @author Warren G. Jackson
 */
public class OX extends HeuristicOperators implements XOHeuristicInterface {

	public OX(Random random) {

		super(random);
	}

	@Override
	public double apply(TSPSolutionInterface solution, double dos, double iom) {

		// invalid operation, return the same solution!
		return -1;
	}

	@Override
	public double apply(TSPSolutionInterface p1, TSPSolutionInterface p2,
			TSPSolutionInterface c, double dos, double iom) {
		int[] fst = p1.getSolutionRepresentation().getRepresentationOfSolution(),
					snd = p2.getSolutionRepresentation().getRepresentationOfSolution(),
					child = c.getSolutionRepresentation().getRepresentationOfSolution();

		int iters = getNumberOfMutations(iom);

		for (int i = 0; i < iters; i++) {
			int a, b;

			// 50% chance of swapping roles of parents
			if (random.nextDouble() < .5) {
				int[] temp = fst;
				fst = snd;
				snd = temp;
			}

			// choose start/end of subsection
			a = random.nextInt(child.length);
			b = a + random.nextInt(child.length - a);
			System.out.println(a +" "+ b);
			ArrayList<Integer> subsection = new ArrayList<>(b -a);
			for (int j = a; j <= b; j++) {
				subsection.add(fst[j]);
			};

			// copy in subsection of 1st parent
			System.arraycopy(fst, a, child, a, b -a +1);

			// loop through 2nd parent, insert missing cities in order
			// j: index of 2nd parent, k: index of child
			int j = 0,
					k = 0;
			while (j < child.length) {
				if (k == a) {
					// skip the subsection
					k = b +1;
					continue;
				} else if (subsection.contains(snd[j])) {
					// skip cities in subsection
					j++;
					continue;
				}

				child[k] = snd[j];
				j++; k++;
			}
		}

		double val = f.getObjectiveFunctionValue(c.getSolutionRepresentation());
		c.setObjectiveFunctionValue(val);
		return val;
	}

	@Override
	public boolean isCrossover() {

		return true;
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
