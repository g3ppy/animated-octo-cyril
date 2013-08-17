package org.hypnosaur.icfp2013.simulator.program;

import org.hypnosaur.icfp2013.simulator.basicexpression.BasicNumber;
import org.hypnosaur.icfp2013.simulator.basicexpression.Id;
import org.hypnosaur.icfp2013.simulator.expressions.UnaryLambda;

import java.util.Set;


public class Program {

    private final
    UnaryLambda lambda;
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

    public BasicNumber eval(BasicNumber x) {
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

