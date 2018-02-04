package com.g52aim.lab00;
import java.util.Arrays;
import java.util.Random;

import com.g52aim.ExperimentalSettings;

import g52aim.domains.chesc2014_SAT.SAT;

/**
 * Class for running the experiment(s) in Lab 0.
 *
 * @author Warren G. Jackson
 */
public class Lab_00_Runner {

	public Result runTest(long seed, int instance, int timeLimit) {

		Random random = new Random(seed);
    	SAT sat = new SAT(instance, timeLimit, random);
    	RandomWalk rw = new RandomWalk(sat, random);
    	rw.run();

    	return new Result(seed, sat.getBestSolutionValue(), rw.getTimeTaken());
	}

	public void printResult(Result result) {

		System.out.println(result.seed + "," + result.best + "," + result.timeTaken);
	}

	public static void main(String [] args) {

		Lab_00_Runner runner = new Lab_00_Runner();

		// do not change these values!
		final int instance = 1;
		final int timeLimit = 10; // note; these are nominal seconds. Your machine may take longer or might be faster!

		// change me in questions 1 and 2
		Long[] seeds = { 1222318L, 3022018L, 1022013L, 1322098L, 8028018L, 9022010L,
      1922998L, 0022000L, 1028077L, 1222000L, 9022008L, 9022018L };

		System.out.println("seed,f_best,time_taken");

		if( ExperimentalSettings.ENABLE_PARALLEL_EXECUTION ) {

			Arrays.stream(seeds).map( s -> {
				return runner.runTest(s, instance, timeLimit);
			}).forEachOrdered(runner::printResult);

		} else {

			for(int i = 0; i < seeds.length; i++) {

				long seed = seeds[i];
				Result r = runner.runTest(seed, instance, timeLimit);
				runner.printResult(r);
			}
		}
	}

	class Result {

		protected final long seed;

		protected final double best;

		protected final double timeTaken;

		public Result(long seed, double best, double timeTaken) {

			this.seed = seed;
			this.best = best;
			this.timeTaken = timeTaken;
		}
	}
}
