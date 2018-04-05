package com.g52aim.project.tsp;

import com.g52aim.project.tsp.instance.Location;
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
    double total = 0;
    for (int i = 0; i < solution.getNumberOfCities() -1; i++) {
      total += getCost(i, i +1);
    }

		return cost;
	}

	@Override
	public double getCost(int location_a, int location_b) {
		Location a = instance.getLocationForCity(location_a),
		         b = instance.getLocationForCity(location_b);

    // Euclidean distance
    return Math.round(
      Math.sqrt((a.getX() - b.getX())^2 + (a.getY() - b.getY())^2));
	}

}
