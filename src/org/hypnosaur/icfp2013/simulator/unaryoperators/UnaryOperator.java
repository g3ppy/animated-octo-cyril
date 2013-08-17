package org.hypnosaur.icfp2013.simulator.unaryoperators;

import org.hypnosaur.icfp2013.simulator.expressions.Expression;

public abstract class UnaryOperator implements Expression {

    protected final Expression operand;

    public UnaryOperator(Expression operand) {
        this.operand = operand;
    }

    public Expression getOp() {
        return operand;
    }

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public Expression getSubExpression(int idx) {
        if (idx != 0)
            throw new IllegalArgumentException("UnaryOperator has only one sub expression, idx was: " + idx);

        return operand;
    }

    @Override
    public int getSize() {
        return 1 + operand.getSize();
    }

    public static UnaryOperator buildByName(String name, Expression arg) {
        if (name.equalsIgnoreCase(Not.OP))
            return new Not(arg);
        if (name.equalsIgnoreCase(Shl1.OP))
            return new Shl1(arg);
        if (name.equalsIgnoreCase(Shr1.OP))
            return new Shr1(arg);
        if (name.equalsIgnoreCase(Shr4.OP))
            return new Shr4(arg);
        if (name.equalsIgnoreCase(Shr16.OP))
            return new Shr16(arg);

        throw new IllegalArgumentException("Invalid unary operation name: " + name);
    }

}

