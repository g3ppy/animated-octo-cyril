package org.tus.icfp2013.simulator;

import org.tus.icfp2013.simulator.operators.And;
import org.tus.icfp2013.simulator.operators.Or;
import org.tus.icfp2013.simulator.operators.Plus;
import org.tus.icfp2013.simulator.operators.Xor;

/**
 * Created by kong
 * Time: 8/9/13 4:37 PM
 */
public abstract class BinaryOperator implements Expression {

    protected final Expression op1;
    protected final Expression op2;

    public BinaryOperator(Expression op1, Expression op2) {
        this.op1 = op1;
        this.op2 = op2;
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

}

