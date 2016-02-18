package org.tus.icfp2013;

import org.tus.icfp2013.client.ICFPClient;
import org.tus.icfp2013.client.ICFPWebClient;
import org.tus.icfp2013.client.Problem;

import java.util.Set;
import java.util.TreeSet;

/**
 * This client will attempt to solve the game.
 */
public class Solver {

    public static void main(String[] args) {
        int minSize = 0;
        if (args.length > 0)
            minSize = Integer.parseInt(args[0]);

        Solver game = new Solver();
        game.solve(minSize);
    }

    public Problem[] getMyProblems(ICFPClient client) {
        return client.myProblems();
//        return new Problem[] {new Problem("BM1O7v44ELFxtIaGFKlN38o3", 12, new String[]{"and", "if0", "not", "plus", "shl1", "shr4"}, false, Float.MIN_VALUE)};
    }

    private void solve(int minSize) {
        ICFPClient client = ICFPWebClient.create();
//        ICFPClient client = ICFPWebClient.create("0550bjHM9InBehh47QR4leYJcjMAzvCh2p0G1mWL"); // vlad's team ID

        // retrieve the problems and
        // create a sorted list of problems that have not been solved
        Problem[] problems = getMyProblems(client);
        Set<Problem> problemsToBeSolved = new TreeSet<Problem>();

        for (Problem problem : problems) {
            if (problem.isSolved()) {
                System.out.println(String.format(
                        "Problem %s was already solved", problem.getId()));
            } else {
                if (problem.getSize() > minSize)
                    problemsToBeSolved.add(problem);
            }
        }

        // some statistics
        System.out.println(String.format(
                "%d total problems, %d solved, %d not solved",
                problems.length,
                problems.length - problemsToBeSolved.size(),
                problemsToBeSolved.size()));

        // solve the problems one by one
        for (Problem problem : problemsToBeSolved) {
            if (problem.getTimeLeft() <= 0.0) {
                System.out.println(String.format("Time limit exceeded for problem %s. Skipping ... ", problem.getId()));
                continue;
            }

            System.out.println(String.format(
                    "Solving problem %s, size %d ...", problem.getId(), problem.getSize()));
            boolean solved = false;
            SingleProblemSolver problemSolver = new SingleProblemSolver(problem, client);

            try {
                boolean success = problemSolver.trySolve();
                if (success)
                    System.out.println("Problem solved!");
            } catch (Throwable e) {
                // the exceptions are ignored but logged
                System.out.println(String.format(
                        "Could not solve problem %s because of exception of type %s. Message was: %s",
                        problem.getId(), e.getClass().getCanonicalName(), e.getMessage()));
                e.printStackTrace();
            }
        }
    }

}

