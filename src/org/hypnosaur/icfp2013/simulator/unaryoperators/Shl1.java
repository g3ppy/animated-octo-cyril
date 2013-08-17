package org.hypnosaur.icfp2013.simulator.unaryoperators;

import org.hypnosaur.icfp2013.simulator.basicexpression.BasicNumber;
import org.hypnosaur.icfp2013.simulator.expressions.Expression;
import org.hypnosaur.icfp2013.simulator.program.ArgsContext;

import java.util.Set;

public class Shl1 extends UnaryOperator {

    public static final String OP = "shl1";

    public Shl1(Expression operand) {
        super(operand);
    }

    @Override
    public BasicNumber eval(ArgsContext args) {
        return operand.eval(args).shl1();
    }

    @Override
    public Set<String> ops() {
        Set<String> rez = operand.ops();
        rez.add(OP);

        return rez;
    }

    @Override
    public String toCode() {
        return "(" + OP + " " + operand.toCode() + ")";
    }

}

