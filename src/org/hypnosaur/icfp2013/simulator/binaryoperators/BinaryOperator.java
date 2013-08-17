package org.hypnosaur.icfp2013.simulator.binaryoperators;

import org.hypnosaur.icfp2013.simulator.expressions.Expression;

public abstract class BinaryOperator implements Expression {

    protected final Expression op1;
    protected final Expression op2;

    public BinaryOperator(Expression op1, Expression op2) {
        this.op1 = op1;
        this.op2 = op2;
    }

    public static BinaryOperator buildByName(String name, Expression arg1, Expression arg2) {
        if (name.equalsIgnoreCase(And.OP))
            return new And(arg1, arg2);
        if (name.equalsIgnoreCase(Or.OP))
            return new Or(arg1, arg2);
        if (name.equalsIgnoreCase(Plus.OP))
            return new Plus(arg1, arg2);
        if (name.equalsIgnoreCase(Xor.OP))
            return new Xor(arg1, arg2);

        throw new IllegalArgumentException("Invalid binary operation name: " + name);
    }

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public Expression getSubExpression(int idx) {
        if (idx == 0)
            return op1;
        if (idx == 1)
            return op2;
        throw new IllegalArgumentException("BinaryOperator has only two sub expression, idx was: " + idx);
    }

    @Override
    public int getSize() {
        return 1 + op1.getSize() + op2.getSize();
    }

}

