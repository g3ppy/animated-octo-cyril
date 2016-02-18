package org.tus.icfp2013.simulator.expressions;

import org.tus.icfp2013.simulator.ArgsContext;
import org.tus.icfp2013.simulator.Number;

/**
 * Created by kong
 * Time: 8/9/13 1:56 PM
 */
public class Const extends BasicExpression {

    private final Number value;

    public Const(int v) {
        if (v == 0)
            value = Number.ZERO;
        else if (v == getSize())
            value = Number.ONE;
        else
            throw new IllegalArgumentException("Illegal arg to Const (should be 0 or 1): " + v);
    }

    @Override
    public Number eval(ArgsContext arg) {
        return value;
    }

    @Override
    public String toCode() {
        if (value == Number.ZERO)
            return "0";
        if (value == Number.ONE)
            return "1";
        throw new RuntimeException("Invalid internal state");
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Const aConst = (Const) o;

        if (!value.equals(aConst.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

