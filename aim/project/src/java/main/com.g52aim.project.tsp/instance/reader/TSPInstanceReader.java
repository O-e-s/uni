package com.g52aim.project.tsp.instance.reader;


import java.nio.file.Path;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

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

  private static final List<String> IGNORE_KEYS
    = Arrays.asList("NAME", "COMMENT", "TYPE", "EDGE_WEIGHT_TYPE");

	@Override
	public TSPInstanceInterface readTSPInstance(Path path, Random random) {

		// TODO read TSP instance file to find the number of cities and
		//		create the array of locations for each city...
		int cities = -1;
		Location[] locations = null;

    Scanner in = new Scanner(path);
    in.useDelimiter(" : ");

    while (in.hasNext()) {
      String key = in.next();

      if (key == "EOF") {
        break;
      }

      switch (key) {
        case "DIMENSION": {
          cities = in.nextInt();
          locations = new Location[cities];
          break;
        }
        case "NODE_COORD_SECTION": {
          locations = readNodes(in, locations);
          break;
        }
        default:
      }

      in.nextLine();
    }

    in.close();

		// create a TSP instance object
		TSPInstance instance = new TSPInstance(cities, locations, random);
		return instance;
	}

  private Location[] readNodes(Scanner in, Location[] locations) {
    in.useDelimiter(" ");
    String val;
    for (int i = 0; i < locations.length; i++) {
      in.nextInt();
      locations[i] = new Location(in.nextFloat(), in.nextFloat());
    }

    return locations;
  }
}
