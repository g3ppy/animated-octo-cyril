package org.hypnosaur.icfp2013.simulator.expressions;

import org.hypnosaur.icfp2013.simulator.basicexpression.BasicNumber;
import org.hypnosaur.icfp2013.simulator.program.ArgsContext;

import java.util.Set;

public interface Expression {

    public BasicNumber eval(ArgsContext args);

    public int getSize();

    public int getOrder();

    public Expression getSubExpression(int idx);

    public Set<String> ops();

    public String toCode();

}

