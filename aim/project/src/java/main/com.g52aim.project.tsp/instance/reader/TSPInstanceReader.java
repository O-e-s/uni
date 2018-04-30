package com.g52aim.project.tsp.instance.reader;


import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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

		Scanner in;
		try {
			in = new Scanner(path);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		while (in.hasNext()) {
			in.useDelimiter("\\s*:\\s*|\r\n|\n|\r");
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
					readNodes(in, locations);
					break;
				}
				default:
			}

			in.nextLine();
		}

		in.close();
		return new TSPInstance(cities, locations, random);
	}

	private void readNodes(Scanner in, Location[] locations) {
		in.useDelimiter("\\s+");
		String val;
		for (int i = 0; i < locations.length; i++) {
			in.nextInt();
			locations[i] = new Location(in.nextDouble(), in.nextDouble());
		}
	}
}
