package org.tus.icfp2013.simulator.expressions;

import org.tus.icfp2013.simulator.Expression;
import org.tus.icfp2013.simulator.Lambda;

/**
 * Exactly one UnaryLambda per program (the "main" program function) so UnaryLambda == Program
 * <p/>
 * Created by kong
 * Time: 8/9/13 3:06 PM
 */
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

