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
					child1 = new int[fst.length],
					child2 = new int[fst.length];

		int iters = getNumberOfMutations(iom);

		for (int i = 0; i < iters; i++) {
			int a, b;

			// choose start/end of subsection
			a = random.nextInt(fst.length -1);
			b = a + random.nextInt(fst.length -a -1) +1;

			ArrayList<Integer> sub1 = new ArrayList<>(b -a),
												 sub2 = new ArrayList<>(b -a);
			for (int j = a; j <= b; j++) {
				sub1.add(fst[j]);
				sub2.add(snd[j]);
			};

			// copy in subsection of parent
			System.arraycopy(fst, a, child1, a, b -a +1);
			System.arraycopy(snd, a, child2, a, b -a +1);

			// make one pass through parents, inserting missing cities into children
			// pi: index of parent, ci*: index of child respective
			int ci1 = 0,
					ci2 = 0;
			for (int pi = 0; pi < fst.length; pi++) {
				if (ci1 == a) {
					// skip the subsection
					ci1 = b +1;
				} else if (ci1 >= fst.length || sub1.contains(snd[pi])) {
					// do nothing if city is in subsection or end is reached
				} else {
					// move city from other parent into child
					child1[ci1] = snd[pi];
					ci1++;
				}

				// same thing for child 2
				if (ci2 == a) {
					ci2 = b +1;
				} else if (ci2 >= fst.length || sub2.contains(fst[pi])) {
				} else {
					child2[ci2] = fst[pi];
					ci2++;
				}
			}

			// children are new parents for next iter
			fst = child1;
			snd = child2;
		}

		// choose child randomly
		c.getSolutionRepresentation().setRepresentationOfSolution(
			random.nextBoolean()? child1 : child2);
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
