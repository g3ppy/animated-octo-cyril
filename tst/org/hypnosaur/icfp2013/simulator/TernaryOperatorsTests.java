package org.hypnosaur.icfp2013.simulator;

import org.hypnosaur.icfp2013.simulator.basicexpression.BasicNumber;
import org.hypnosaur.icfp2013.simulator.basicexpression.Const;
import org.hypnosaur.icfp2013.simulator.basicexpression.Id;
import org.hypnosaur.icfp2013.simulator.binaryoperators.Plus;
import org.hypnosaur.icfp2013.simulator.expressions.*;
import org.hypnosaur.icfp2013.simulator.program.Program;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: vlad
 * Date: 8/17/13
 * Time: 11:37 PM
 */
public class TernaryOperatorsTests {
    Expression exp;
    BasicNumber result;
    Program program;
    Id id;

    @Before
    public void setUp() throws Exception {
        Id.resetIds();
        id = new Id();
    }

    @Test
    public void testIf0() throws Exception {
        exp = new If0(new Const(0), id, new Const(0));
        program = makeProgram(exp, id);
        result = evalProgramWith(program, new BasicNumber("0xF0FF00123400FF0F"));
        assertEquals(new BasicNumber("0xF0FF00123400FF0F"), result);

        exp = new If0(new Const(1), id, new Const(0));
        program = makeProgram(exp, id);
        result = evalProgramWith(program, new BasicNumber("0xF0FF00123400FF0F"));
        assertEquals(BasicNumber.ZERO, result);
    }

    @Test
    public void testFold() throws Exception {
        Id y = new Id();
        Id z = new Id();

        exp = new Fold(id, new Const(0), new BinaryLambda(y, z, new Plus(y, z)));
        program = makeProgram(exp, id);
        System.out.println(program.getCode());
        result = evalProgramWith(program, new BasicNumber("0xF0FF00123400FF0F"));
        assertEquals(new BasicNumber("0x0000000000000343"), result);

        exp = new Fold(id, id, new BinaryLambda(y, z, new Plus(y, z)));
        program = makeProgram(exp, id);
        result = evalProgramWith(program, new BasicNumber("0xF0FF00123400FF0F"));
        assertEquals(new BasicNumber("0xF0FF001234010252"), result);
    }

    public Program makeProgram(Expression exp, Id id) {
        UnaryLambda lambda = new UnaryLambda(id, exp);
        return new Program(lambda, id);
    }

    public BasicNumber evalProgramWith(Program program, BasicNumber number) {
        return program.eval(number);
    }

}
