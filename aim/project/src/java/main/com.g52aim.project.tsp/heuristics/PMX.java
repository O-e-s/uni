package com.g52aim.project.tsp.heuristics;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;
import com.g52aim.project.tsp.interfaces.XOHeuristicInterface;

/**
 * @author Warren G. Jackson
 */
public class PMX extends HeuristicOperators implements XOHeuristicInterface {

	public PMX(Random random) {

		super(random);
	}

	@Override
	public double apply(TSPSolutionInterface solution, double dos, double iom) {

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

			// map cities in subsections onto cities of other at same index
			HashMap<Integer,Integer>
				map1 = new HashMap<>(b -a),
				map2 = new HashMap<>(b -a);
			for (int j = a; j <= b; j++) {
				map1.put(fst[j], snd[j]);
				map2.put(snd[j], fst[j]);
			}

			// copy in subsection of parent
			System.arraycopy(fst, a, child2, a, b -a +1);
			System.arraycopy(snd, a, child1, a, b -a +1);

			// make one pass through parents, inserting missing cities into children
			// pi: index of parent, ci*: index of child respective
			int ci1 = 0,
					ci2 = 0;
			for (int pi = 0; pi < fst.length; pi++) {
				if (ci1 == a) {
					// skip the subsection
					ci1 = b +1;
				}
				if (ci1 < fst.length && !map2.containsKey(snd[pi])) {
					// move city from first parent into child, using mapping if one exists
					child1[ci1] = map1.getOrDefault(fst[pi], fst[pi]);
					ci1++;
				}

				// same thing for child 2
				if (ci2 == a) {
					ci2 = b +1;
				}
				if (ci2 < fst.length && !map1.containsKey(snd[pi])) {
					child2[ci2] = map2.getOrDefault(snd[pi], snd[pi]);
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
