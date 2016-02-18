package org.tus.icfp2013.simulator;

import java.util.Set;

/**
 * Created by kong
 * Time: 8/9/13 3:05 PM
 */
public abstract class Lambda implements Expression {

    protected final Expression body;

    public Lambda(Expression body) {
        this.body = body;
    }

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public Expression getSubExpression(int idx) {
        if (idx != 0)
            throw new IllegalArgumentException("Lambda has only one sub expression, idx was: " + idx);

        return body;
    }

    @Override
    public Number eval(ArgsContext arg) {
        return body.eval(arg);
    }

    @Override
    public Set<String> ops() {
        return body.ops();
    }

    @Override
    public int getSize() {
        return 1 + body.getSize();
    }

}

