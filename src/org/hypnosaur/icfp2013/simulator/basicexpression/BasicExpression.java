package org.hypnosaur.icfp2013.simulator.basicexpression;


import org.hypnosaur.icfp2013.simulator.expressions.Expression;

import java.util.HashSet;
import java.util.Set;

public abstract class BasicExpression implements Expression {

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Expression getSubExpression(int idx) {
        throw new IllegalArgumentException("BasicExpression doesn't have sub expressions, idx was: " + idx);
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public Set<String> ops() {
        return new HashSet<>();
    }

}

