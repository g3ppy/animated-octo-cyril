package org.tus.icfp2013.simulator;

import java.util.Set;

/**
 * Created by kong
 * Time: 8/9/13 11:02 AM
 */
public interface Expression {

    public Number eval(ArgsContext args);

    public int getSize();

    public int getOrder();

    public Expression getSubExpression(int idx);

    public Set<String> ops();

    public String toCode();

}

