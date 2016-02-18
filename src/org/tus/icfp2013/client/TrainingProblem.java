package org.tus.icfp2013.client;

/**
 *
 */
public class TrainingProblem {

    private final String challenge;
    private final String id;
    private final Integer size;
    private final String[] operators;

    public TrainingProblem(String challenge, String id, Integer size, String[] operators) {
        this.challenge = challenge;
        this.id = id;
        this.size = size;
        this.operators = operators;
    }

    public String getChallenge() {
        return challenge;
    }

    public String getId() {
        return id;
    }

    public Integer getSize() {
        return size;
    }

    public String[] getOperators() {
        return operators;
    }
}
