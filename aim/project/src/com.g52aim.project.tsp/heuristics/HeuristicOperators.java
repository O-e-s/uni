package com.g52aim.project.tsp.heuristics;

import java.util.Random;
import java.util.TreeMap;

import com.g52aim.project.tsp.interfaces.ObjectiveFunctionInterface;


/**
 * @author Warren G. Jackson
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

	protected int getNumberOfMutations(double iom) {

		return iomMap.floorEntry(iom).getValue();
	}

	protected int getDepthOfSearch(double dos) {

		return dosMap.floorEntry(dos).getValue();
	}

	/**
	 * Perform an adjacent swap with the city at indices a and a+1
	 * @return delta: The change in objective function value
	*/
	protected double adjacentSwap(Integer[] repr, int a, boolean calc) {
		double delta = 0;
		int b = (a +1) % repr.length, // wrap around chosen index +1
				temp;

		if (calc) {
			// subtract distance before and after pair from value
			delta -= f.getCost(repr[a], repr[Math.floorMod(a -1, repr.length)]);
			delta -= f.getCost(repr[b], repr[(b +1) % repr.length]);
		}

		// swap
		temp = repr[a];
		repr[a] = repr[b];
		repr[b] = temp;

		if (calc) {
			// add new distance before and after pair
			delta += f.getCost(repr[a], repr[Math.floorMod(a -1, repr.length)]);
			delta += f.getCost(repr[b], repr[(b +1) % repr.length]);
		}

		return delta;
	}
	/**
	 * Do ajacentSwap with delta evaluation
	*/
	protected double adjacentSwap(Integer[] repr, int a) {
		return adjacentSwap(repr, a, true);
	}
}
