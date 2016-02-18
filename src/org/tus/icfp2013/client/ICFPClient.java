package org.tus.icfp2013.client;

/**
 * All interactions with the Game use this interface.
 */
public interface ICFPClient {

    Problem[] myProblems();

    EvalResponse evalProgramId(String id, String[] arguments);

    EvalResponse evalProgramCode(String programCode, String[] arguments);

    GuessResponse guess(String id, String programCode);

    TrainingProblem train(int size, String[] operators);

    String status();
}
