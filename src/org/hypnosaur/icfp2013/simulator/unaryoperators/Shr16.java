package org.hypnosaur.icfp2013.simulator.unaryoperators;

import org.hypnosaur.icfp2013.simulator.basicexpression.BasicNumber;
import org.hypnosaur.icfp2013.simulator.expressions.Expression;
import org.hypnosaur.icfp2013.simulator.program.ArgsContext;

import java.util.Set;

public class Shr16 extends UnaryOperator {

    public static final String OP = "shr16";

    public Shr16(Expression operand) {
        super(operand);
    }

    @Override
    public BasicNumber eval(ArgsContext args) {
        return operand.eval(args).shr16();
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

