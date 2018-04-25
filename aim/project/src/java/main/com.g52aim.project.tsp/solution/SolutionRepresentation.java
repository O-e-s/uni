package com.g52aim.project.tsp.solution;

import java.util.Arrays;

import com.g52aim.project.tsp.interfaces.SolutionRepresentationInterface;

/**
 *
 * @author Warren G. Jackson
 *
 * TODO you need to state the Object/other that will be used for representing a solution to the TSP problem.
 * 		For example, boolean[] can be used to represent the binary string for MAX-SAT problems.
 *
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
    // TODO ensure it's not the same array instance returned
    copy.setRepresentationOfSolution(getRepresentationOfSolution());
		return copy;
	}

	@Override
	public String toString() {
		return Arrays.toString(solution);
	}
}
