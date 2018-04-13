package com.g52aim.project.tsp.heuristics;

import java.util.Random;
import java.util.TreeMap;

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

		// TODO implementation of two-opt swap heuristic
		return -1;
	}

	/*
	 * TODO update the methods below to return the correct boolean value.
	 */

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
