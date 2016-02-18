package org.tus.icfp2013.simulator;

import org.tus.icfp2013.simulator.expressions.Id;
import org.tus.icfp2013.simulator.expressions.UnaryLambda;

import java.util.Set;

/**
 * Created by kong
 * Time: 8/9/13 10:47 PM
 */
public class Program {

    private final UnaryLambda lambda;
    private final Id xx;
    private final ArgsContext args;


    public Program(UnaryLambda lambda, Id x) {
        this.lambda = lambda;
        this.xx = x;
        args = new ArgsContext();
    }

    public int size() {
        return lambda.getSize();
    }

    public Set<String> ops() {
        return lambda.ops();
    }

    public String getCode() {
        return lambda.toCode();
    }

    public Number eval(Number x) {
        args.set(xx, x);
        return lambda.eval(args);
    }

    public UnaryLambda getLambda() {
        return lambda;
    }

    public Id getId() {
        return xx;
    }

}

