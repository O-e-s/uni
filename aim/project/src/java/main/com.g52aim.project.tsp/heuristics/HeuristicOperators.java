package com.g52aim.project.tsp.heuristics;

import java.util.Random;
import java.util.TreeMap;

import com.g52aim.project.tsp.interfaces.ObjectiveFunctionInterface;


/**
 *
 * @author Warren G. Jackson
 *
 * TODO you can add any common functionality here
 *		to save having to re-implement them in all
 *		your other heuristics!
 *		( swapping two cities seems to be popular )
 *
 *
 * If this this concept it new to you, you may want
 * to read this article on inheritance:
 * https://www.tutorialspoint.com/java/java_inheritance.htm
 */
public class HeuristicOperators {

	protected final Random random;
  protected ObjectiveFunctionInterface f;

	public HeuristicOperators(Random random) {

		this.random = random;
	}

	public void setObjectiveFunction(ObjectiveFunctionInterface f) {

    this.f = f;
	}

	/**
	* Map the lower bounds of ranges onto the specified number of mutations that
	* should be performed
	*/
	@SuppressWarnings("serial")
	private TreeMap<Double,Integer> iomMap = new TreeMap<Double,Integer>() {{
		put(.0, 1);
		put(.2, 2);
		put(.4, 3);
		put(.6, 4);
		put(.8, 5);
		put(1.0, 6);
	}};

	private TreeMap<Double,Integer> dosMap = iomMap;

	public int getNumberOfMutations(double iom) {

		return iomMap.floorEntry(iom).getValue();
	}

	public int getDepthOfSearch(double dos) {

		return dosMap.floorEntry(dos).getValue();
	}
}
