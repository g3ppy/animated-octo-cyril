package org.tus.icfp2013.simulator;

import org.tus.icfp2013.simulator.expressions.Id;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kong
 * Time: 8/9/13 1:21 PM
 */
public class ArgsContext {

    private final Map<Id, Number> args = new HashMap<Id, Number>();

    public Number getValue(Id id) {
        return args.get(id);
    }

    public void set(Id id, Number val) {
        args.put(id, val);
    }

    @Override
    public String toString() {
        return args.toString();
    }

}

