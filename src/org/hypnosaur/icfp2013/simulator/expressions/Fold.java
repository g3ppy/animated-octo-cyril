package org.hypnosaur.icfp2013.simulator.expressions;


import org.hypnosaur.icfp2013.simulator.basicexpression.BasicNumber;
import org.hypnosaur.icfp2013.simulator.basicexpression.Const;
import org.hypnosaur.icfp2013.simulator.program.ArgsContext;

import java.util.Set;

public class Fold implements Expression {

    private final Expression e1;
    private final Expression e2;

    private final BinaryLambda lambda;

    public Fold(Expression e1, Expression e2, BinaryLambda lambda) {
        this.e1 = e1;
        this.e2 = e2;
        this.lambda = lambda;
    }

    @Override
    public int getOrder() {
        return 3;
    }

    @Override
    public Expression getSubExpression(int idx) {
        if (idx == 0)
            return e1;
        if (idx == 1)
            return e2;
        if (idx == 2)
            return lambda;

        throw new IllegalArgumentException("Fold has only three sub expression, idx was: " + idx);
    }

    @Override
    public BasicNumber eval(ArgsContext args) {
        BasicNumber n1 = e1.eval(args);
        BasicNumber rez = e2.eval(args);

        for (int i = 0; i < 8; i++) {
            args.set(lambda.getId1(), n1.getByte(i));
            args.set(lambda.getId2(), rez);
            rez = lambda.eval(args);
        }
        return rez;
    }

    @Override
    public int getSize() {
        return 1 + e1.getSize() + e2.getSize() + lambda.getSize();
    }

    @Override
    public Set<String> ops() {
        Set<String> rez = e1.ops();
        rez.addAll(e2.ops());
        rez.addAll(lambda.ops());
        if (e2 instanceof Const && e2.eval(null).equals(BasicNumber.ZERO))
            rez.add("tfold");
        else
            rez.add("fold");

        return rez;
    }

    @Override
    public String toCode() {
        return "(fold " + e1.toCode() + " " + e2.toCode() + " " + lambda.toCode() + ")";
    }

}

