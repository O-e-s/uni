package com.g52aim.project.tsp.solution;

import java.util.Arrays;

import com.g52aim.project.tsp.interfaces.SolutionRepresentationInterface;

/**
 * @author Warren G. Jackson
 */
public class SolutionRepresentation implements SolutionRepresentationInterface<int[]> {

  private int[] solution;

  @Override
  public int[] getRepresentationOfSolution() {
    return solution;
  }

  @Override
  public void setRepresentationOfSolution(int[] solution) {
    this.solution = solution;
  }

  @Override
  public int getNumberOfCities() {
    return solution.length;
  }

  @Override
	public SolutionRepresentationInterface<int[]> clone() {
    SolutionRepresentation copy = new SolutionRepresentation();
    copy.setRepresentationOfSolution(solution.clone());
		return copy;
	}

	@Override
	public String toString() {
		return Arrays.toString(solution);
	}
}
