package org.tus.icfp2013.simulator.operators;

import org.tus.icfp2013.simulator.ArgsContext;
import org.tus.icfp2013.simulator.BinaryOperator;
import org.tus.icfp2013.simulator.Expression;
import org.tus.icfp2013.simulator.Number;

import java.util.Set;

/**
 * Created by kong
 * Time: 8/9/13 4:41 PM
 */
public class And extends BinaryOperator {

    public static final String OP = "and";


    public And(Expression op1, Expression op2) {
        super(op1, op2);
    }

    @Override
    public Number eval(ArgsContext args) {
        return op1.eval(args).and(op2.eval(args));
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

