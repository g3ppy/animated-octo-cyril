package org.hypnosaur.icfp2013.simulator.expressions;

import org.hypnosaur.icfp2013.simulator.basicexpression.Id;
import org.hypnosaur.icfp2013.simulator.program.Lambda;

public class UnaryLambda extends Lambda {

    private final Id id;

    public UnaryLambda(Id id, Expression body) {
        super(body);
        this.id = id;
    }

    public Id getId() {
        return id;
    }

    @Override
    public String toCode() {
        return "(lambda (" + id.toCode() + ") " + body.toCode() + ")";
    }

}

