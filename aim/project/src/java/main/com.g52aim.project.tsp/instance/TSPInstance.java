package com.g52aim.project.tsp.instance;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.g52aim.project.tsp.TSPObjectiveFunction;
import com.g52aim.project.tsp.instance.InitialisationMode;
import com.g52aim.project.tsp.interfaces.ObjectiveFunctionInterface;
import com.g52aim.project.tsp.interfaces.TSPInstanceInterface;
import com.g52aim.project.tsp.solution.SolutionRepresentation;
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

	@Override
	public TSPSolution createSolution(InitialisationMode mode) {
		SolutionRepresentation repr = new SolutionRepresentation();

		switch (mode) {
			case RANDOM: {
				// Make a list in range [0,numberOfCities)
				List<Integer> perm = IntStream.range(0, numberOfCities -1).boxed()
					.collect(Collectors.toList());
				Collections.shuffle(perm, random);
				repr.setRepresentationOfSolution(perm.stream().mapToInt(i -> i).toArray());
				break;
			}
		}

		return new TSPSolution(repr,
			getTSPObjectiveFunction().getObjectiveFunctionValue(repr),
			numberOfCities);
	}

	@Override
	public int getNumberOfCities() {

		return numberOfCities;
	}

	@Override
	public Location getLocationForCity(int cityId) {

		return locations[cityId];
	}

	@Override
	public ObjectiveFunctionInterface getTSPObjectiveFunction() {

		if(f == null) {
			this.f = new TSPObjectiveFunction(this);
		}

		return f;
	}
}
