package org.tus.icfp2013.simulator.expressions;

import org.tus.icfp2013.simulator.Expression;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by kong
 * Time: 8/10/13 1:57 AM
 */
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
        return new HashSet<String>();
    }

}

