package org.tus.icfp2013.simulator.expressions;

import org.tus.icfp2013.simulator.ArgsContext;
import org.tus.icfp2013.simulator.Expression;
import org.tus.icfp2013.simulator.Number;

import java.util.Set;

/**
 * Created by kong
 * Time: 8/9/13 2:02 PM
 */
public class If0 implements Expression {

    private final Expression condition;
    private final Expression if0;
    private final Expression ifNot0;


    public If0(Expression condition, Expression if0, Expression ifNot0) {
        this.condition = condition;
        this.if0 = if0;
        this.ifNot0 = ifNot0;
    }

    @Override
    public int getOrder() {
        return 3;
    }

    @Override
    public Expression getSubExpression(int idx) {
        if (idx == 0)
            return condition;
        if (idx == 1)
            return if0;
        if (idx == 2)
            return ifNot0;

        throw new IllegalArgumentException("If0 has only three sub expression, idx was: " + idx);
    }

    @Override
    public Number eval(ArgsContext args) {
        if (condition.eval(args).equals(Number.ZERO))
            return if0.eval(args);
        else
            return ifNot0.eval(args);
    }

    @Override
    public int getSize() {
        return 1 + condition.getSize() + if0.getSize() + ifNot0.getSize();
    }

    @Override
    public Set<String> ops() {
        Set<String> rez = condition.ops();
        rez.addAll(if0.ops());
        rez.addAll(ifNot0.ops());
        rez.add("if0");

        return rez;
    }

    @Override
    public String toCode() {
        return "(if0 " + condition.toCode() + " " + if0.toCode() + " " + ifNot0.toCode() + ")";
    }

}

