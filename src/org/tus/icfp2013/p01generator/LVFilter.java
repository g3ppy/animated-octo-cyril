package org.tus.icfp2013.p01generator;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:padreati@yahoo.com">Aurelian Tutuianu</a>
 */
class LVFilter implements Producer {

    private final T01Generator gen;

    public LVFilter(T01Generator gen) {
        this.gen = gen;
    }

    @Override
    public Set<String> produce(Set<String> input) {
        String[] tokens = gen.split(gen.getTemplate());
        SOp[] sops = new SOp[tokens.length];
        for (int i = 0; i < sops.length; i++) {
            sops[i] = SOp.valueOf(tokens[i]);
        }
        int[] parents = new int[tokens.length];
        dfs(-1, 0, sops, parents);

        HashSet<String> output = new HashSet<String>();
        for (String template : input) {
            String[] chunk = gen.split(template);
            for (int i = 0; i < chunk.length; i++) {
                if (!chunk[i].equals("L")) {
                    continue;
                }
                boolean underfold = false;
                int p = i, prev = i;
                while (p >= 0) {
                    if (chunk[p].equals("fold") || chunk[p].equals("tfold")) {
                        int lastChild = -1;
                        for (int j = 0; j < chunk.length; j++) {
                            if (parents[j] == p)
                                lastChild = j;
                        }
                        if (prev == lastChild)
                            underfold = true;
                        break;
                    }
                    prev = p;
                    p = parents[p];
                }
                if (underfold) {
                    chunk[i] = "V";
                }
            }
            output.add(gen.concatenate(chunk));
        }

        return output;
    }

    private int dfs(int parent, int pos, SOp[] sops, int[] parents) {
        if (pos == sops.length) {
            return pos;
        }
        parents[pos] = parent;
        int newparent = pos;
        pos++;
        for (int i = 0; i < sops[newparent].getChildren(); i++) {
            pos = dfs(newparent, pos, sops, parents);
        }

        return pos;
    }

}

