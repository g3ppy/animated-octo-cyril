package org.tus.icfp2013.p01generator;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:padreati@yahoo.com">Aurelian Tutuianu</a>
 */
class SProducer implements Producer {

    private final T01Generator gen;

    public SProducer(T01Generator gen) {
        this.gen = gen;
    }

    @Override
    public Set<String> produce(Set<String> input) {
        if (!gen.getFreq().keySet().contains("S")) {
            return input;
        }

        int count = gen.getFreq().get("S");
        int unique = gen.getCandidates().get("S").size();
        int[] hit = new int[unique];
        String[] candidates = new String[unique];
        int pos = 0;
        for (String op : gen.getCandidates().get("S")) {
            candidates[pos++] = op;
        }
        String[] ops = new String[count];

        HashSet<String> output = new HashSet<String>();
        for (String template : input) {
            String[] terms = gen.split(template);
            String[] proc = new String[terms.length];
            System.arraycopy(terms, 0, proc, 0, terms.length);
            back(count, hit, candidates, ops, terms, proc, output);
        }
        return output;
    }

    private void back(int count, int[] hit, String[] candidates, String[] ops, String[] terms, String[] proc, Set<String> output) {
        if (count == 0) {
            for (int i = 0; i < hit.length; i++) {
                if (hit[i] < 1) {
                    return;
                }
            }
            int pos = 0;
            for (int i = 0; i < terms.length; i++) {
                if (terms[i].equals("S")) {
                    proc[i] = ops[pos++];
                } else {
                    proc[i] = terms[i];
                }
            }
            output.add(gen.concatenate(proc));
            return;
        }
        for (int i = 0; i < candidates.length; i++) {
            ops[count - 1] = candidates[i];
            hit[i]++;
            back(count - 1, hit, candidates, ops, terms, proc, output);
            hit[i]--;
        }
    }

}

