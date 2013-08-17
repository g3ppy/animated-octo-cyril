package org.hypnosaur.icfp2013.simulator;

import org.hypnosaur.icfp2013.simulator.basicexpression.BasicNumber;
import org.hypnosaur.icfp2013.simulator.basicexpression.Const;
import org.hypnosaur.icfp2013.simulator.basicexpression.Id;
import org.hypnosaur.icfp2013.simulator.expressions.Expression;
import org.hypnosaur.icfp2013.simulator.expressions.UnaryLambda;
import org.hypnosaur.icfp2013.simulator.program.ArgsContext;
import org.hypnosaur.icfp2013.simulator.program.Program;
import org.hypnosaur.icfp2013.simulator.unaryoperators.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: vlad
 * Date: 8/17/13
 * Time: 10:22 PM
 */
public class UnaryOperatorsTest {

    Expression exp;
    BasicNumber result;

    Id id;

    @Before
    public void setUp() throws Exception {
        Id.resetIds();
        id = new Id();
    }

    @Test
    public void testNot() throws Exception {
        exp = new Not(new Const(0));
        result = exp.eval(new ArgsContext());
        assertEquals(new BasicNumber("0xFFFFFFFFFFFFFFFF"), result);

        exp = new Not(new Const(1));
        result = exp.eval(new ArgsContext());
        assertEquals(new BasicNumber("0xFFFFFFFFFFFFFFFE"), result);
    }

    @Test
    public void testShl1() throws Exception {
        exp = new Shl1(new Const(0));
        result = exp.eval(new ArgsContext());
        assertEquals(BasicNumber.ZERO, result);

        exp = new Shl1(new Const(1));
        result = exp.eval(new ArgsContext());
        assertEquals(new BasicNumber("0x0000000000000002"), result);

        Program program = makeProgram(new Shl1(id), id);
        BasicNumber result = evalProgramWith(program, new BasicNumber("0xF0FF00123400FF0F"));
        assertEquals(new BasicNumber("0xE1FE00246801FE1E"), result);
    }

    @Test
    public void testShr1() throws Exception {
        exp = new Shr1(new Const(0));
        result = exp.eval(new ArgsContext());
        assertEquals(BasicNumber.ZERO, result);

        exp = new Shr1(new Const(1));
        result = exp.eval(new ArgsContext());
        assertEquals(BasicNumber.ZERO, result);

        Program program = makeProgram(new Shr1(id), id);
        BasicNumber result = evalProgramWith(program, new BasicNumber("0xF0FF00123400FF0F"));
        assertEquals(new BasicNumber("0x787F80091A007F87"), result);
    }

    @Test
    public void testShr4() throws Exception {
        exp = new Shr4(new Const(0));
        result = exp.eval(new ArgsContext());
        assertEquals(BasicNumber.ZERO, result);

        exp = new Shr4(new Const(1));
        result = exp.eval(new ArgsContext());
        assertEquals(BasicNumber.ZERO, result);

        Program program = makeProgram(new Shr4(id), id);
        BasicNumber result = evalProgramWith(program, new BasicNumber("0xF0FF00123400FF0F"));
        assertEquals(new BasicNumber("0x0F0FF00123400FF0"), result);
    }

    @Test
    public void testShr16() throws Exception {
        exp = new Shr16(new Const(0));
        result = exp.eval(new ArgsContext());
        assertEquals(BasicNumber.ZERO, result);

        exp = new Shr16(new Const(1));
        result = exp.eval(new ArgsContext());
        assertEquals(BasicNumber.ZERO, result);

        Program program = makeProgram(new Shr16(id), id);
        BasicNumber result = evalProgramWith(program, new BasicNumber("0xF0FF00123400FF0F"));
        assertEquals(new BasicNumber("0x0000F0FF00123400"), result);
    }

    public Program makeProgram(Expression exp, Id id) {
        UnaryLambda lambda = new UnaryLambda(id, exp);
        return new Program(lambda, id);
    }

    public BasicNumber evalProgramWith(Program program, BasicNumber number) {
        return program.eval(number);
    }
}
