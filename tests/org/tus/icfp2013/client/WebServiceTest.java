package org.tus.icfp2013.client;

import org.junit.Before;
import org.junit.Test;

public class WebServiceTest {

    private ICFPClient icfpClient;

    @Before
    public void setUp() {
        icfpClient = ICFPWebClient.create();
    }

    @Test
    public void myProblems() {
        Problem[] problems = icfpClient.myProblems();

        for (Problem problem : problems) {
            System.out.println(problem);
        }
    }

    @Test
    public void evalProgram() {
        EvalResponse evalResponse =
                icfpClient.evalProgramId(
                        "afa696afglajf696af",
                        new String[]{"0x00000000000001", "0xEFFFFFFFFFFFFF"});

        System.out.println(evalResponse);
    }

    @Test
    public void evalCode() {
        EvalResponse evalResponse =
                icfpClient.evalProgramCode(
                        "(lambda (x) (shl1 x))",
                        new String[]{"0x00000000000001", "0xEFFFFFFFFFFFFF"});

        System.out.println(evalResponse);
    }

    @Test
    public void guess() {
        GuessResponse guessResponse =
                icfpClient.guess(
                        "tu3LZdm7ZYbi4aZMxAGxNtoZ", "(lambda (x) (not x))");

        System.out.println(guessResponse);
    }

    @Test
    public void trainWithEmpty() {
        TrainingProblem problem = icfpClient.train(3, new String[]{null});
        System.out.println(problem.getChallenge());
    }

    @Test
    public void trainWithFold() {
        TrainingProblem problem = icfpClient.train(20, new String[]{"fold"});
        System.out.println(problem.getChallenge());
    }

    @Test
    public void status() {
        String response = icfpClient.status();
        System.out.println(response);
    }

}

