package org.tus.icfp2013.client;

import org.junit.Test;

import static org.junit.Assert.*;

public class MyProblemsTest {

    @Test
    public void webCall() {
        ICFPClient icfpClient = new ICFPWebClient(
                new WebServiceClient("0000abcdefghijklmnopqrstuvwxyz0123456789") {

                    @Override
                    public String call(String method, String data) {
                        return "    [\n" +
                                "     {\"id\":\"dKdeIAoZMyb5y3a74iTcLXyr\",\n" +
                                "      \"size\":30,\n" +
                                "      \"operators\":[\"shr16\",\"if0\",\"xor\",\"plus\",\"not\",\"fold\"]},\n" +
                                "     {\"id\":\"hx2XLtS756IvDv9ZNuILizxJ\",\n" +
                                "      \"size\":3,\n" +
                                "      \"operators\":[\"not\"],\n" +
                                "      \"solved\":true,\n" +
                                "      \"timeLeft\":0},\n" +
                                "     {\"id\":\"af82718a7fhla74cal8faf7a\",\n" +
                                "      \"size\":3,\n" +
                                "      \"operators\":[\"not\"],\n" +
                                "      \"timeLeft\":192.61}\n" +
                                "    ]";
                    }
                });

        Problem[] problems = icfpClient.myProblems();

        Problem problem = problems[0];
        assertEquals("dKdeIAoZMyb5y3a74iTcLXyr", problem.getId());
        assertEquals(30, problem.getSize().intValue());
        assertArrayEquals(new String[]{"shr16", "if0", "xor", "plus", "not", "fold"}, problem.getOperators());
        assertFalse(problem.isSolved());
        assertEquals(300.0, problem.getTimeLeft(), 0.01);

        problem = problems[1];
        assertEquals("hx2XLtS756IvDv9ZNuILizxJ", problem.getId());
        assertEquals(3, problem.getSize().intValue());
        assertArrayEquals(new String[]{"not"}, problem.getOperators());
        assertTrue(problem.isSolved());
        assertEquals(0.0, problem.getTimeLeft(), 0.01);

        problem = problems[2];
        assertEquals("af82718a7fhla74cal8faf7a", problem.getId());
        assertEquals(3, problem.getSize().intValue());
        assertArrayEquals(new String[]{"not"}, problem.getOperators());
        assertFalse(problem.isSolved());
        assertEquals(192.61, problem.getTimeLeft(), 0.01);
    }
}
