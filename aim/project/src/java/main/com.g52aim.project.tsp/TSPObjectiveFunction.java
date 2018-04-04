package com.g52aim.project.tsp;

import com.g52aim.project.tsp.interfaces.ObjectiveFunctionInterface;
import com.g52aim.project.tsp.interfaces.TSPInstanceInterface;
import com.g52aim.project.tsp.solution.SolutionRepresentation;

/**
 * 
 * @author Warren G. Jackson
 *
 */
public class TSPObjectiveFunction implements ObjectiveFunctionInterface {
	
	private final TSPInstanceInterface instance;
	
	public TSPObjectiveFunction(TSPInstanceInterface instance) {
		
		this.instance = instance;
	}

	@Override
	public double getObjectiveFunctionValue(SolutionRepresentation solution) {
		
		double cost = 0.0;

		// TODO calculate cost of entire solution
		// you should complete this regardless of whether
		// you are going to be using the basic or delta evaluation
		return cost;
	}

	@Override
	public double getCost(int location_a, int location_b) {
		
		return 0.0;
	}

}
