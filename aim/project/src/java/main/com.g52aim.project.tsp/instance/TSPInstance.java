package com.g52aim.project.tsp.instance;


import java.util.Random;

import com.g52aim.project.tsp.TSPObjectiveFunction;
import com.g52aim.project.tsp.interfaces.ObjectiveFunctionInterface;
import com.g52aim.project.tsp.interfaces.TSPInstanceInterface;
import com.g52aim.project.tsp.solution.TSPSolution;

/**
 * 
 * @author Warren G. Jackson
 * 
 * A TSP instance Object containing any necessary information
 * related to the respective problem instance.
 */
public class TSPInstance implements TSPInstanceInterface {
	
	private final Location[] locations;
	
	private final int numberOfCities;
	
	private final Random random;
	
	private ObjectiveFunctionInterface f = null;
	
	public TSPInstance(int numberOfCities, Location[] locations, Random random) {
		
		this.numberOfCities = numberOfCities;
		this.random = random;
		this.locations = locations;
	}

	/**
	 * 
	 */
	@Override
	public TSPSolution createSolution(InitialisationMode mode) {
		
		return null;
	}
	
	@Override
	public int getNumberOfCities() {

		return -1;
	}

	@Override
	public Location getLocationForCity(int cityId) {

		return null;
	}

	@Override
	public ObjectiveFunctionInterface getTSPObjectiveFunction() {
		
		if(f == null) {
			this.f = new TSPObjectiveFunction(this);
		}
		
		return f;
	}
}
