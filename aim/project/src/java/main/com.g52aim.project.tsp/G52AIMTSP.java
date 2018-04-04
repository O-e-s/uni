package com.g52aim.project.tsp;


import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

import com.g52aim.project.tsp.hyperheuristics.SR_IE_HH;
import com.g52aim.project.tsp.instance.Location;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

/**
 *
 * @author Warren G. Jackson
 *
 */
public class G52AIMTSP extends ProblemDomain {

	// TODO create an array of the instance file names; e.g. "dj38".
	private String[] instanceFiles = null;

	public G52AIMTSP(long seed) {

		super(seed);
	}

	public TSPSolutionInterface getSolution(int index) {

		return null;
	}

	public TSPSolutionInterface getBestSolution() {

		return null;
	}

	@Override
	public double applyHeuristic(int hIndex, int currentIndex, int candidateIndex) {

		return -1;
	}

	@Override
	public double applyHeuristic(int hIndex, int parent1Index, int parent2Index, int candidateIndex) {

		return -1;
	}

	@Override
	public String bestSolutionToString() {

		return getBestSolution().toString();
	}

	@Override
	public boolean compareSolutions(int a, int b) {

		return false;
	}

	@Override
	public void copySolution(int a, int b) {

	}

	@Override
	public double getBestSolutionValue() {

		return -1;
	}

	@Override
	public double getFunctionValue(int index) {

		return -1;
	}

	@Override
	public int[] getHeuristicsOfType(HeuristicType type) {

		return null;
	}

	@Override
	public int[] getHeuristicsThatUseDepthOfSearch() {

		return null;
	}

	@Override
	public int[] getHeuristicsThatUseIntensityOfMutation() {

		return null;
	}

	@Override
	public int getNumberOfHeuristics() {

		return -1;
	}

	@Override
	public int getNumberOfInstances() {

		return -1;
	}

	@Override
	public void initialiseSolution(int index) {

	}

	@Override
	public void loadInstance(int instanceId) {

		String SEP = FileSystems.getDefault().getSeparator();
		String instanceName = "instances" + SEP + "tsp" + SEP + instanceFiles [instanceId] + ".tsp";

		// TODO create instance reader and problem instance
		// ...

		// TODO set objective function in heuristics
		// ...

	}

	@Override
	public void setMemorySize(int size) {

	}

	@Override
	public String solutionToString(int index) {

		return null;
	}

	@Override
	public String toString() {

		String username = "psy___";
		return username +  "'s TSP";
	}

	/**
	 * You can use this for testing :)
	 *
	 * @param args
	 */
	public static void main(String [] args) {

		long seed = 527893l;
		long timeLimit = 10_000;
		G52AIMTSP tsp = new G52AIMTSP(seed);
		HyperHeuristic hh = new SR_IE_HH(seed);
		tsp.loadInstance( 0 );
		hh.setTimeLimit(timeLimit);
		hh.loadProblemDomain(tsp);
		hh.run();

		double best = hh.getBestSolutionValue();
		System.out.println(best);

		// TODO you will need to populate this based on your representation!
		List<Location> routeLocations = new ArrayList<>();
		SolutionPrinter.printSolution(routeLocations);
	}
}
