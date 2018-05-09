package com.g52aim.project.tsp.hyperheuristics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

/**
 * A hyperheuristic applying the concept of Simulated Annealing.
 * Lundy & Mees' method for randomly accepting a worsening solution, with
 * a gradually decreasing probability as the run continues.
 * The normal acceptance criterion is an improvement (OI).
 * Some memory is used to track the total number of times each heuristic
 * produced a better solution. This is used so that heuristics that more often
 * produce a better solution have a greater probability of being selected
 */
public class SA_LM_OI_HH extends HyperHeuristic {
	/**
	 * Parameters
	 */
	private final double
		BETA = 0.00000001,
		C = 1,
		IOM = .2,
		DOS = .8;

	/**
	 * Store heuristic information classes
	 */
	private final Map<Heuristic,Long> heuristics = new HashMap<>();

	/**
	 * Total score for all heuristics
	 */
	private long totalScore = 0;

	/**
	 * Store current solution's objective function value
	 */
	double current;

	public SA_LM_OI_HH(long seed) {
		super(seed);
	}

	/**
	 * Choose the next heuristic to run
	 * Uses each heuristic's memory and score to pick the one most likely to
	 * result in improvement
	 */
	private Heuristic nextHeuristic() {
		double choice = rng.nextDouble() *totalScore,
				total = 0;

		for (Heuristic h : heuristics.keySet()) {
			total += h.score;
			if (total >= choice) {
				return h;
			}
		}

		return null;
	}

	private double advanceTemperature(double temp) {
		return temp / (1 +(BETA *temp));
	}

	@Override
	protected void solve(ProblemDomain problem) {
		problem.setIntensityOfMutation(IOM);
		problem.setDepthOfSearch(DOS);
		problem.initialiseSolution(0);
		heuristics.clear();
		totalScore = 0;

		current = problem.getFunctionValue(0);
		double temperature = C *current;

		int hCount = problem.getNumberOfHeuristics();
		for (int i = 0; i < hCount; i++) {
			Heuristic h = new Heuristic(this, i);
			totalScore += h.score;
			heuristics.put(h, h.score);
		}

		long iteration = 0,
				 lastImprovement;
		boolean accept;
		System.err.println("Iter\tf(s)\tf(s')\tAccept");

		while(!hasTimeExpired()) {

			// Apply heuristics, replace in map with key as new score
			Heuristic h = nextHeuristic();
			totalScore -= h.score;
			double cand = h.apply(problem, 0, 1);
			totalScore += h.score;
			heuristics.put(h, h.score);

			accept = cand < current
				|| rng.nextDouble() < Math.exp(-(cand -current)/temperature);

			if (accept) {
				problem.copySolution(1, 0);
				current = cand;
			}

			System.err.println(iteration + "\t" + current + "\t" + cand + "\t" + accept+"\t"+temperature);

			iteration++;
			temperature = advanceTemperature(temperature);
		}
	}

	@Override
	public String toString() {

		return "SA_LM_OI_HH";
	}
}

class Heuristic {
	// Total number of improvements
	public long score;
	// Heuristic id in problem space
	public int id;
	// Parent hyperheuristic instance
	private SA_LM_OI_HH hh;

	public Heuristic(SA_LM_OI_HH hh, int id) {
		this.id = id;
		this.hh = hh;
		score = 1; // minimum score is 1
	}

	public double apply(ProblemDomain prob, int p1, int p2) {
		double res = prob.applyHeuristic(id, p1, p2);
		// Add 1 to score if improvement
		if (res -hh.current < 0) {
			score++;
		}
		return res;
	}
}
