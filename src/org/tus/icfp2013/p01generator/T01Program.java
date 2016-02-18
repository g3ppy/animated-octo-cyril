package org.tus.icfp2013.p01generator;

import org.tus.icfp2013.simulator.Number;
import org.tus.icfp2013.simulator.Program;

/**
 * @author <a href="mailto:padreati@yahoo.com">Aurelian Tutuianu</a>
 */
public class T01Program {

    private final Program program;
    private final Number value;

    public T01Program(Program program, Number value) {
        this.program = program;
        this.value = value;
    }

    public Program getProgram() {
        return program;
    }

    public Number getValue() {
        return value;
    }

}

