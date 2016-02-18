package org.tus.icfp2013.p01generator;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:padreati@yahoo.com">Aurelian Tutuianu</a>
 */
class NProducer implements Producer {

    private final T01Generator gen;

    public NProducer(T01Generator gen) {
        this.gen = gen;
    }

    @Override
    public Set<String> produce(Set<String> input) {
        if (!gen.getFreq().keySet().contains("N")) {
            return input;
        }
        HashSet<String> templates = new HashSet<String>();
        for (String template : input) {
            templates.add(template.replaceAll("N", "tfold"));
        }
        return templates;
    }

}

