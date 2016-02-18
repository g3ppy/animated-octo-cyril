package org.tus.icfp2013.p01generator;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:padreati@yahoo.com">Aurelian Tutuianu</a>
 */
class LProducer implements Producer {

    private final T01Generator gen;

    public LProducer(T01Generator gen) {
        this.gen = gen;
    }

    @Override
    public Set<String> produce(Set<String> input) {
        if (!gen.getFreq().containsKey("L")) {
            return input;
        }
        Set<String> output = new HashSet<String>();
        for (String template : input) {
            String[] terms = gen.split(template);
            String[] parts = gen.split(template);
            back(0, terms, parts, output);
        }
        return output;
    }

    private void back(int pos, String[] terms, String[] parts, Set<String> output) {
        if (pos == terms.length) {
            output.add(gen.concatenate(parts));
            return;
        }
        if (!terms[pos].equals("L")) {
            back(pos + 1, terms, parts, output);
            return;
        }
        parts[pos] = "0";
        back(pos + 1, terms, parts, output);
        parts[pos] = "1";
        back(pos + 1, terms, parts, output);
    }

}
