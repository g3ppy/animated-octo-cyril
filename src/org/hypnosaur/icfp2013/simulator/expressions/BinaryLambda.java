package org.hypnosaur.icfp2013.simulator.expressions;

import org.hypnosaur.icfp2013.simulator.basicexpression.Id;
import org.hypnosaur.icfp2013.simulator.program.Lambda;

public class BinaryLambda extends Lambda {

    private final Id id1;
    private final Id id2;


    public BinaryLambda(Id id1, Id id2, Expression body) {
        super(body);
        this.id1 = id1;
        this.id2 = id2;
    }

    public Id getId1() {
        return id1;
    }

    public Id getId2() {
        return id2;
    }

    @Override
    public String toCode() {
        return "(lambda (" + id1.toCode() + " " + id2.toCode() + ") " + body.toCode() + ")";
    }

}

