package org.hypnosaur.icfp2013.simulator.program;

import org.hypnosaur.icfp2013.simulator.basicexpression.BasicNumber;
import org.hypnosaur.icfp2013.simulator.expressions.Expression;

import java.util.Set;


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
    public BasicNumber eval(ArgsContext arg) {
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

