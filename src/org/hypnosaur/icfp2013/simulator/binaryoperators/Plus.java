package org.hypnosaur.icfp2013.simulator.binaryoperators;

import org.hypnosaur.icfp2013.simulator.basicexpression.BasicNumber;
import org.hypnosaur.icfp2013.simulator.expressions.Expression;
import org.hypnosaur.icfp2013.simulator.program.ArgsContext;

import java.util.Set;

public class Plus extends BinaryOperator {

    public static final String OP = "plus";


    public Plus(Expression op1, Expression op2) {
        super(op1, op2);
    }

    @Override
    public BasicNumber eval(ArgsContext args) {
        return op1.eval(args).plus(op2.eval(args));
    }

    @Override
    public Set<String> ops() {
        Set<String> rez = op1.ops();
        rez.addAll(op2.ops());
        rez.add(OP);

        return rez;
    }

    @Override
    public String toCode() {
        return "(" + OP + " " + op1.toCode() + " " + op2.toCode() + ")";
    }

}

