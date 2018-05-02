package com.g52aim.project.tsp.solution;

import java.util.Arrays;

import com.g52aim.project.tsp.interfaces.SolutionRepresentationInterface;

/**
 * @author Warren G. Jackson
 */
public class SolutionRepresentation implements SolutionRepresentationInterface<Integer[]> {

	private Integer[] solution;

	@Override
	public Integer[] getRepresentationOfSolution() {
		return solution;
	}

	@Override
	public void setRepresentationOfSolution(Integer[] solution) {
		this.solution = solution;
	}

	@Override
	public int getNumberOfCities() {
		return solution.length;
	}

	@Override
	public SolutionRepresentationInterface<Integer[]> clone() {
		SolutionRepresentation copy = new SolutionRepresentation();
		copy.setRepresentationOfSolution(solution.clone());
		return copy;
	}

	@Override
	public String toString() {
		return Arrays.toString(solution);
	}
}
