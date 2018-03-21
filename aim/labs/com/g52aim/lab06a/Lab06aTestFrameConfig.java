package com.g52aim.lab06a;

import com.g52aim.hyflex.HyFlexTestFrame;

public class Lab06aTestFrameConfig extends HyFlexTestFrame {

	/*
	 * permitted run time(s) in nominal chesc seconds = { 60 }
	 */
	protected final long RUN_TIME_IN_SECONDS = 30;

	/*
	 * permitted problem domain(s) = { SAT }
	 */
	protected final String[] PROBLEM_DOMAINS = { "BP", "SAT", "TSP" };
	/*
	 * permitted instance ID's:
	 * 		BP  = { 7, 11 }
	 *		SAT = { 3, 11 }
	 *		TSP = { 0, 6 }
	 */
	protected final int[][] INSTANCE_IDs = { { 7, 11 }, { 3, 11 }, { 0, 6 } };

	@Override
	public String[] getDomains() {

		return this.PROBLEM_DOMAINS;
	}

	@Override
	public int[][] getInstanceIDs() {

		return this.INSTANCE_IDs;
	}

	@Override
	public long getRunTime() {

		return (MILLISECONDS_IN_TEN_MINUTES * RUN_TIME_IN_SECONDS) / 600;
	}

}
