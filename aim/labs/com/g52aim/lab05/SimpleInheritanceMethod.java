package com.g52aim.lab05;

import java.util.Random;

import g52aim.domains.chesc2014_SAT.Meme;
import g52aim.domains.chesc2014_SAT.SAT;

public class SimpleInheritanceMethod implements MemeplexInheritanceMethod {

	private final SAT problem;
	private final Random rng;

	public SimpleInheritanceMethod(SAT problem, Random rng) {

		this.problem = problem;
		this.rng = rng;
	}

	/**
	 * Copies the memetic material of the parents to the children
	 * using the Simple Inheritance Method.
	 *
	 * @param parent1 The solution memory index of parent 1.
	 * @param parent2 The solution memory index of parent 2.
	 * @param child1 The solution memory index of child 1.
	 * @param child2 The solution memory index of child 2.
	 *
	 * Simple Inheritance Method PSEUDOCODE:
	 *
	 * INPUT: parent1, parent2, child1, child2
	 * IF f( parent1 ) == f( parent2 ) THEN
	 *
	 *     inherit = random \in { parent1, parent2 }
	 *     child1.memeplex <- inherit.memeplex;
	 *     child2.memeplex <- inherit.memeplex;
	 *
	 * ELSEIF f( parent1 ) < f( parent2 ) THEN
	 *
	 *     child1.memeplex <- parent1.memeplex;
	 *     child2.memeplex <- parent1.memeplex;
	 *
	 * ELSE // parent2 is best
	 *
	 *     child1.memeplex = parent2.memeplex;
	 *     child2.memeplex = parent2.memeplex;
	 *
	 * ENDIF
	 * return;
	 */
	@Override
	public void performMemeticInheritance(int parent1, int parent2, int child1, int child2) {
		double val1 = problem.getObjectiveFunctionValue(parent1),
			val2 = problem.getObjectiveFunctionValue(parent2);

    if (val1 == val2) {
      int from = rng.nextDouble() < 0.5 ? parent1 : parent2;
      inheritMemeplex(from, child1);
      inheritMemeplex(from, child1);
    } else {
      int from = val1 < val2 ? parent1 : parent2;
      inheritMemeplex(from, child1);
      inheritMemeplex(from, child1);
    }
	}

	private void inheritMemeplex(int from, int to) {
		for (int i = 0; i < problem.getNumberOfMemes(); i++) {
			problem.getMeme(from, i).setMemeOption(problem.getMeme(to, i).getMemeOption());
		}
	}
}
