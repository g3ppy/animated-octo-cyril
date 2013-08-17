package org.hypnosaur.icfp2013.simulator.basicexpression;

import org.hypnosaur.icfp2013.simulator.program.ArgsContext;


public class Const extends BasicExpression {

    private final BasicNumber value;

    public Const(int v) {
        if (v == 0)
            value = BasicNumber.ZERO;
        else if (v == getSize())
            value = BasicNumber.ONE;
        else
            throw new IllegalArgumentException("Illegal arg to Const (should be 0 or 1): " + v);
    }

    @Override
    public BasicNumber eval(ArgsContext arg) {
        return value;
    }

    @Override
    public String toCode() {
        if (value == BasicNumber.ZERO)
            return "0";
        if (value == BasicNumber.ONE)
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

        return value.equals(aConst.value);

    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

