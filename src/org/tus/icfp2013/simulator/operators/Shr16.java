package org.tus.icfp2013.simulator.operators;

import org.tus.icfp2013.simulator.ArgsContext;
import org.tus.icfp2013.simulator.Expression;
import org.tus.icfp2013.simulator.Number;
import org.tus.icfp2013.simulator.UnaryOperator;

import java.util.Set;

/**
 * Created by kong
 * Time: 8/9/13 4:38 PM
 */
public class Shr16 extends UnaryOperator {

    public static final String OP = "shr16";

    public Shr16(Expression operand) {
        super(operand);
    }

    @Override
    public Number eval(ArgsContext args) {
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

