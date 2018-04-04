package com.g52aim.project.tsp.instance.reader;


import java.nio.file.Path;
import java.util.Random;

import com.g52aim.project.tsp.instance.Location;
import com.g52aim.project.tsp.instance.TSPInstance;
import com.g52aim.project.tsp.interfaces.TSPInstanceInterface;
import com.g52aim.project.tsp.interfaces.TSPInstanceReaderInterface;

/**
 * 
 * @author Warren G. Jackson
 *
 */
public class TSPInstanceReader implements TSPInstanceReaderInterface {

	@Override
	public TSPInstanceInterface readTSPInstance(Path path, Random random) {

		// TODO read TSP instance file to find the number of cities and
		//		create the array of locations for each city...
		int cities = -1;
		Location[] locations = null;
		// ...


		
		// create a TSP instance object
		TSPInstance instance = new TSPInstance(cities, locations, random);
		return instance;
	}
}
