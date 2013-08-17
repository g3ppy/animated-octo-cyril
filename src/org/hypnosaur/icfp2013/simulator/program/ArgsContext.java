package org.hypnosaur.icfp2013.simulator.program;

import org.hypnosaur.icfp2013.simulator.basicexpression.BasicNumber;
import org.hypnosaur.icfp2013.simulator.basicexpression.Id;

import java.util.HashMap;
import java.util.Map;

public class ArgsContext {

    private final Map<Id, BasicNumber> args = new HashMap<>();

    public BasicNumber getValue(Id id) {
        return args.get(id);
    }

    public void set(Id id, BasicNumber val) {
        args.put(id, val);
    }

    @Override
    public String toString() {
        return args.toString();
    }

}

