package org.hypnosaur.icfp2013.client;

import com.google.common.base.Preconditions;

import java.util.Arrays;

/**
 * The response from a POST /myproblems request.
 */
public class Problem implements Comparable<Problem> {

    private final String id;
    private final Integer size;
    private final String[] operators;
    private final Boolean solved;
    private final Float timeLeft;

    public Problem(String id, Integer size, String[] operators,
                   Boolean solved, Float timeLeft) {

        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(size);
        Preconditions.checkArgument(size >= 3 && size <= 30);
        Preconditions.checkNotNull(operators);

        this.id = id;
        this.size = size;
        this.operators = operators;
        this.solved = solved;
        this.timeLeft = timeLeft;
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

    public boolean isSolved() {
        return (solved == null) ? false : solved;
    }

    public float getTimeLeft() {
        return (timeLeft == null) ? (float) 300.0 : timeLeft;
    }

    @Override
    public int compareTo(Problem o) {
        int diff = size - o.size;

        if (diff == 0)
            return id.compareTo(o.id);

        return diff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Problem problem = (Problem) o;

        return id.equals(problem.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Problem{" +
                "id='" + getId() + '\'' +
                ", size=" + getSize() +
                ", operators=" + Arrays.toString(getOperators()) +
                ", solved=" + isSolved() +
                ", timeLeft=" + getTimeLeft() +
                '}';
    }

}

