package com.g52aim.lab06a;

import com.g52aim.hyflex.HyFlexUtilities;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import AbstractClasses.ProblemDomain.HeuristicType;

public class SR_RRT_HH extends HyperHeuristic {

	public SR_RRT_HH(long seed) {

		super(seed);
	}

	/**
	  * TODO - Implement a simple random record-to-record travel selection hyper-heuristic
	  *
	  * PSEUDOCODE:
	  *
	  * INPUT: problem_instance
	  * hs <- HyFlexUtilities.getHeuristicSetOfTypes( problem, HeuristicType, HeuristicType... );
	  * s <- initialiseSolution();
	  *
	  * WHILE termination criterion is not met DO
	  *
	  *     h <- getNextHeuristicUsingSimpleRandom( hs );
	  *     s' <- h( s );
	  *     accept <- acceptMoveUsingRRT( f( s ), f( s' ), f( s_best ) );
	  *
	  *     IF accept THEN
	  *         s <- s'; 						// HINT :: You will have to create a copy of the solution here!
	  *     END_IF
	  *
	  *		### WARNING! HYFLEX ONLY UPDATES THE BEST SOLUTION VALUE AFTER hasTimeExpired() IS CALLED! ###
	  *
	  *     IF f( s' ) < f( s_{best} ) THEN		// HINT :: This is handled by HyFlex
	  *         s_{best} <- s';
	  *     END_IF
	  *
	  * END_WHILE
	  *
	  * return s_{best};						// HINT :: Also handled by HyFlex
	  */
	public void solve(ProblemDomain problem) {
    int[] hs = HyFlexUtilities.getHeuristicSetOfTypes(problem,
      HeuristicType.MUTATION, HeuristicType.LOCAL_SEARCH,
      HeuristicType.RUIN_RECREATE);

    int CURRENT_SOLUTION_INDEX = 0,
        CANDIDATE_SOLUTION_INDEX = 1;

    problem.setMemorySize(2);
    problem.initialiseSolution(CURRENT_SOLUTION_INDEX);

    double fCur = problem.getFunctionValue(CURRENT_SOLUTION_INDEX),
           fCand;

		while (!hasTimeExpired()) {
      fCand = problem.applyHeuristic(getNextHeuristicUsingSimpleRandom(hs),
        CURRENT_SOLUTION_INDEX, CANDIDATE_SOLUTION_INDEX);

      hasTimeExpired();
      if (acceptMoveUsingRRT(fCur, fCand, problem.getBestSolutionValue())) {
        problem.copySolution(CANDIDATE_SOLUTION_INDEX, CURRENT_SOLUTION_INDEX);
        fCur = fCand;
      }
    }
	}

	/**
	 * Simple random heuristic selection. This should not "apply" the heuristic,
	 * only choose it so that it can be applied within the main hyper-heuristic loop.
	 *
	 * PSEUDOCODE:
	 *
	 * INPUT: set_of_heuristics
	 * h <- random \in set_of_heuristics;
	 * return h;
	 *
	 * @param setOfHeuristics The set of heuristics to choose from.
	 * @return The (ID of the) heuristic to apply.
	 */
	public int getNextHeuristicUsingSimpleRandom(int[] setOfHeuristics) {
    return setOfHeuristics[rng.nextInt(setOfHeuristics.length)];
	}

	/**
	 * Record-to-record Travel for move acceptance.
	 *
	 * ( see exercise sheet for formula for calculating threshold value )
	 *
	 * PSEUDOCODE for non-stochastic threshold move acceptance accepting IE moves:
	 *
	 * tau <- currentThresholdValue(); // calculated using RRT!
	 * IF f( s' ) <= max( f( s ), tau ) THEN
	 *     "accept()";
	 * ELSE
	 *     "reject()";
     * END_IF;
	 *
	 * @param currentSolutionFitness The objective value of the current solution (s).
	 * @param candidateSolutionFitness The objective value of the candidate solution (s').
	 * @param bestSolutionFitness The objective value of the best solution found so far (s_best)
	 * @return Whether to accept (true) or reject (false) the candidate solution.
	 */
	public boolean acceptMoveUsingRRT(double currentSolutionFitness, double candidateSolutionFitness,
			double bestSolutionFitness ) {

	  double tau = bestSolutionFitness *1.5;
    return candidateSolutionFitness <= Math.max(currentSolutionFitness, tau);
	}

	public String toString() {

		return "SR-RRT-HH";
	}

}
