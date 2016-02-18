package org.tus.icfp2013.simulator.expressions;

import org.tus.icfp2013.simulator.ArgsContext;
import org.tus.icfp2013.simulator.Number;

/**
 * IDs exist only for lambdas, there are no "local variables" as IDs
 * and only (maximum) two lambdas per program, so 3 Ids in total
 * <p/>
 * Created by kong
 * Time: 8/9/13 1:52 PM
 */
public class Id extends BasicExpression {

    private static int uid = 0;

    private final int id; // internal unique ID
    private final String name;

    public Id() {
        id = uid++;
        name = getPrettyName(id);
    }

    public static void resetIds() {
        uid = 0;
    }

    @Override
    public Number eval(ArgsContext arg) {
        return arg.getValue(this);
    }

    @Override
    public String toCode() {
        return name;
    }

    @Override
    public String toString() {
        return toCode();
    }

    private static String getPrettyName(int id) {
        if (id < 5)
            return (char) ('x' + id) + "";
        else
            return "p" + id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Id id1 = (Id) o;

        return id == id1.id;
    }

}

