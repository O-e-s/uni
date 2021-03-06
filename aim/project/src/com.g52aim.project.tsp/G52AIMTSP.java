package com.g52aim.project.tsp;
import java.awt.Color;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.ArrayList;

import BinPacking.BinPacking;
import FlowShop.FlowShop;
import PersonnelScheduling.PersonnelScheduling;
import SAT.SAT;
import travelingSalesmanProblem.TSP;
import VRP.VRP;
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
import com.g52aim.project.tsp.hyperheuristics.SA_LM_OI_HH;
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
		long seed = 909348l;

		// G52AIMTSP prob = new G52AIMTSP(seed);
		// prob.loadInstance(6);
		// HyperHeuristic hh = new SR_IE_HH(seed);
		// hh.setTimeLimit(60_000l);
		// hh.loadProblemDomain(prob);
		// hh.run();
		// System.out.println(hh.getBestSolutionValue());
		// ArrayList<Location> locs = new ArrayList<>();
		// for (int city : prob.bestSolution.getSolutionRepresentation().getRepresentationOfSolution()) {
		//	locs.add(prob.instance.getLocationForCity(city));
		// }
		// SolutionPrinter.printSolution(locs);

		Class<?> pClass = null;
		int[] instances = null;
		switch (Integer.parseInt(args[0])) {
			case 0:
				pClass = BinPacking.class;
				instances = new int[]{1,7,9,10,11};
				break;
			case 1:
				pClass = FlowShop.class;
				instances = new int[]{1,3,8,10,11};
				break;
			case 2:
				pClass = PersonnelScheduling.class;
				instances = new int[]{5,8,9,10,11};
				break;
			case 3:
				pClass = SAT.class;
				instances = new int[]{3,4,5,10,11};
				break;
			case 4:
				pClass = TSP.class;
				instances = new int[]{0,2,6,7,8};
				break;
			case 5:
				pClass = VRP.class;
				instances = new int[]{1,2,5,6,9};
		 }

		 for (int inst : instances) {
			ProblemDomain prob = null;
			try {
				prob = (ProblemDomain) pClass.getDeclaredConstructor(long.class).newInstance(seed);
			} catch (Exception e) {
				e.printStackTrace();
			}
			prob.loadInstance(inst);
			HyperHeuristic hh = new SA_LM_OI_HH(seed);
			hh.setTimeLimit(300_000l);
			hh.loadProblemDomain(prob);

			for (int iter = 0; iter < 11; iter++) {
				hh.run();
				System.out.print(hh.getBestSolutionValue()+"\t");
				seed += 1;
			}
			System.out.println();
		 }
	}
}
