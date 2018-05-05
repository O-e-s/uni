package com.g52aim.project.tsp;
import java.awt.Color;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import com.g52aim.project.tsp.heuristics.AdjacentSwap;
import com.g52aim.project.tsp.heuristics.DavissHillClimbing;
import com.g52aim.project.tsp.heuristics.HeuristicOperators;
import com.g52aim.project.tsp.heuristics.NextDescent;
import com.g52aim.project.tsp.heuristics.OX;
import com.g52aim.project.tsp.heuristics.PMX;
import com.g52aim.project.tsp.heuristics.Reinsertion;
import com.g52aim.project.tsp.heuristics.TwoOpt;
import com.g52aim.project.tsp.hyperheuristics.SR_IE_HH;
import com.g52aim.project.tsp.hyperheuristics.SA_IE_HH;
import com.g52aim.project.tsp.instance.InitialisationMode;
import com.g52aim.project.tsp.instance.Location;
import com.g52aim.project.tsp.instance.reader.TSPInstanceReader;
import com.g52aim.project.tsp.interfaces.HeuristicInterface;
import com.g52aim.project.tsp.interfaces.TSPInstanceInterface;
import com.g52aim.project.tsp.interfaces.TSPSolutionInterface;
import com.g52aim.project.tsp.interfaces.XOHeuristicInterface;
import com.g52aim.project.tsp.visualiser.TSPView;

/**
 * @author Warren G. Jackson
 */
public class G52AIMTSP extends ProblemDomain {

	private String[] instanceFiles =
		{"T81", "circle", "dj38", "plus", "square", "u724", "example"};

	private HeuristicInterface[] heuristics = null;

	private TSPInstanceInterface instance = null;

	private TSPSolutionInterface bestSolution = null;

	private int memSize = 2;
	private ArrayList<TSPSolutionInterface> solutions = null;

	public G52AIMTSP(long seed) {
		super(seed);

		solutions = new ArrayList<>(memSize);
		setMemorySize(memSize);
		heuristics = new HeuristicInterface[]{
		 new AdjacentSwap(rng),
		 new TwoOpt(rng),
		 new Reinsertion(rng),
		 new NextDescent(rng),
		 new DavissHillClimbing(rng),
		 new OX(rng),
		 new PMX(rng)
		};
	}

	public TSPSolutionInterface getSolution(int index) {

		return solutions.get(index);
	}

	public TSPSolutionInterface getBestSolution() {

		return bestSolution;
	}

	@Override
	public double applyHeuristic(int hIndex, int currentIndex, int candidateIndex) {
		TSPSolutionInterface copy = solutions.get(currentIndex).clone();
		solutions.set(candidateIndex, copy);
		double val = heuristics[hIndex].apply(copy, depthOfSearch, intensityOfMutation);
		if (getBestSolutionValue() > val) {
			bestSolution = solutions.get(candidateIndex).clone();
		}
		return val;
	}

	@Override
	public double applyHeuristic(int hIndex, int parent1Index, int parent2Index, int candidateIndex) {
		initialiseSolution(candidateIndex);
		double val = ((XOHeuristicInterface) heuristics[hIndex]).apply(
				solutions.get(parent1Index), solutions.get(parent2Index),
				solutions.get(candidateIndex), depthOfSearch, intensityOfMutation);
		if (getBestSolutionValue() > val) {
			bestSolution = solutions.get(candidateIndex).clone();
		}
		return val;
	}

	@Override
	public String bestSolutionToString() {

		return getBestSolution().toString();
	}

	@Override
	public boolean compareSolutions(int a, int b) {

		return solutions.get(a).getSolutionRepresentation().equals(
				solutions.get(b).getSolutionRepresentation());
	}

	@Override
	public void copySolution(int a, int b) {
		solutions.set(b, solutions.get(a).clone());
	}

	@Override
	public double getBestSolutionValue() {

		return bestSolution == null? Double.POSITIVE_INFINITY
			: bestSolution.getObjectiveFunctionValue();
	}

	@Override
	public double getFunctionValue(int index) {

		return instance.getTSPObjectiveFunction().getObjectiveFunctionValue(
				solutions.get(index).getSolutionRepresentation());
	}

	@Override
	public int[] getHeuristicsOfType(HeuristicType type) {
		int a, b;
		switch (type) {
			case CROSSOVER:
				a = 5; b = 6; break;
			case LOCAL_SEARCH:
				a = 3; b = 4; break;
			case MUTATION:
				a = 0; b = 2; break;
			default:
				a = 0; b = 0;
		}

		int[] res = new int[b-a];
		for (int i = a; i < b; i++) {
			res[i-a] = i;
		}
		return res;
	}

	@Override
	public int[] getHeuristicsThatUseDepthOfSearch() {

		return new int[]{3, 4};
	}

	@Override
	public int[] getHeuristicsThatUseIntensityOfMutation() {

		return new int[]{0, 1, 5, 6};
	}

	@Override
	public int getNumberOfHeuristics() {

		return 7;
	}

	@Override
	public int getNumberOfInstances() {

		return instanceFiles.length;
	}

	@Override
	public void initialiseSolution(int index) {
		TSPSolutionInterface inst = instance.createSolution(InitialisationMode.RANDOM);
		solutions.set(index, inst);
		if (getBestSolutionValue() < inst.getObjectiveFunctionValue()) {
			bestSolution = inst.clone();
		}
	}

	@Override
	public void loadInstance(int instanceId) {
		String SEP = FileSystems.getDefault().getSeparator();
		String instanceName = "instances" + SEP + "tsp" + SEP + instanceFiles [instanceId] + ".tsp";

		TSPInstanceReader reader = new TSPInstanceReader();
		instance = reader.readTSPInstance(Paths.get(instanceName), rng);

		for (HeuristicInterface h : heuristics) {
			h.setObjectiveFunction(instance.getTSPObjectiveFunction());
		}
	}

	@Override
	public void setMemorySize(int size) {
		if (size <= 1) return;
		if (solutions == null) {
			memSize = size;
		} else {
			solutions.ensureCapacity(size);
			int currentSize = solutions.size();
			for (int i = 0; i < size - currentSize; i++) {
				solutions.add(null);
			}
		}
	}

	@Override
	public String solutionToString(int index) {

		return solutions.get(index).toString();
	}

	@Override
	public String toString() {

		return "psywv's TSP";
	}

	/**
	 * You can use this for testing :)
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		long seed = Long.parseLong(args[0]);
		G52AIMTSP tsp = new G52AIMTSP(seed);
		tsp.loadInstance(0);
		HyperHeuristic hh = new SA_IE_HH(seed);
		hh.setTimeLimit(60000l);
		hh.loadProblemDomain(tsp);
		hh.run();

		System.out.println("f(s_best) = " + hh.getBestSolutionValue());

		Location[] path = new Location[tsp.instance.getNumberOfCities()];
		Integer[] repr = tsp.getBestSolution().getSolutionRepresentation().getRepresentationOfSolution();
		for (int i = 0; i < repr.length; i++) {
			path[i] = tsp.instance.getLocationForCity(repr[i]);
		}
		SolutionPrinter.printSolution(Arrays.asList(path));
		new TSPView(path, Color.RED, Color.GREEN);
	}
}
