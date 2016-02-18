package org.tus.icfp2013.simulator.expressions;

import org.tus.icfp2013.simulator.Expression;
import org.tus.icfp2013.simulator.Lambda;

/**
 * At most one BinaryLambda per program (max one fold)
 * <p/>
 * Created by kong
 * Time: 8/9/13 4:06 PM
 */
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

