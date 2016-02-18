package org.tus.icfp2013.p01generator;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:padreati@yahoo.com">Aurelian Tutuianu</a>
 */
public class TProducer implements Producer {

    private final T01Generator gen;

    public TProducer(T01Generator gen) {
        this.gen = gen;
    }

    @Override
    public Set<String> produce(Set<String> input) {
        if (!gen.getFreq().containsKey("T")) {
            return input;
        }

        int count = gen.getFreq().get("T");
        boolean fold = gen.getOpSet().contains("fold");

        if (!fold) {
            return produceNotFold(input);
        } else {
            return produceWithFold(input, count);
        }
    }

    private Set<String> produceNotFold(Set<String> input) {
        HashSet<String> output = new HashSet<String>();
        for (String templ : input) {
            String[] tokens = gen.split(templ);
            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i].equals("T")) {
                    tokens[i] = "if0";
                }
            }
            output.add(gen.concatenate(tokens));
        }
        return output;
    }

    private Set<String> produceWithFold(Set<String> input, int count) {
        HashSet<String> output = new HashSet<String>();
        for (String templ : input) {
            String[] tokens = templ.split(",", -1);
            int[] pos = new int[count];
            int len = 0;
            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i].equals("T")) {
                    tokens[i] = "if0";
                    pos[len++] = i;
                }
            }
            for (int i = 0; i < count; i++) {
                if (i > 0) {
                    tokens[pos[i - 1]] = "if0";
                }
                tokens[pos[i]] = "fold";
                output.add(gen.concatenate(tokens));
            }
        }
        return output;
    }

}

