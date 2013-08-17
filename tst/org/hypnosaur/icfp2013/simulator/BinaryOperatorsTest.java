package org.hypnosaur.icfp2013.simulator;

import org.hypnosaur.icfp2013.simulator.basicexpression.BasicNumber;
import org.hypnosaur.icfp2013.simulator.basicexpression.Const;
import org.hypnosaur.icfp2013.simulator.basicexpression.Id;
import org.hypnosaur.icfp2013.simulator.binaryoperators.And;
import org.hypnosaur.icfp2013.simulator.binaryoperators.Or;
import org.hypnosaur.icfp2013.simulator.binaryoperators.Plus;
import org.hypnosaur.icfp2013.simulator.binaryoperators.Xor;
import org.hypnosaur.icfp2013.simulator.expressions.Expression;
import org.hypnosaur.icfp2013.simulator.expressions.UnaryLambda;
import org.hypnosaur.icfp2013.simulator.program.ArgsContext;
import org.hypnosaur.icfp2013.simulator.program.Program;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: vlad
 * Date: 8/17/13
 * Time: 10:22 PM
 */
public class BinaryOperatorsTest {

    Expression exp;
    BasicNumber result;
    Id id;

    @Before
    public void setUp() throws Exception {
        Id.resetIds();
        id = new Id();
    }

    @Test
    public void testAnd() throws Exception {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                exp = new And(new Const(i), new Const(j));
                result = exp.eval(new ArgsContext());
                assertEquals(new BasicNumber(i & j), result);
            }
        }

        Program program = makeProgram(new And(id, new Const(1)), id);
        BasicNumber result = evalProgramWith(program, new BasicNumber("0xF0FF00123400FF0F"));
        assertEquals(new BasicNumber("0x0000000000000001"), result);
    }

    @Test
    public void testOr() throws Exception {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                exp = new Or(new Const(i), new Const(j));
                result = exp.eval(new ArgsContext());
                assertEquals(new BasicNumber(i | j), result);
            }
        }

        Program program = makeProgram(new Or(id, new Const(0)), id);
        BasicNumber result = evalProgramWith(program, new BasicNumber("0xF0FF00123400FF0F"));
        assertEquals(new BasicNumber("0xF0FF00123400FF0F"), result);
    }

    @Test
    public void testPlus() throws Exception {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                exp = new Plus(new Const(i), new Const(j));
                result = exp.eval(new ArgsContext());
                assertEquals(new BasicNumber(i + j), result);
            }
        }

        Program program = makeProgram(new Plus(id, id), id);
        BasicNumber result = evalProgramWith(program, new BasicNumber("0xF0FF00123400FF0F"));
        assertEquals(new BasicNumber("0xE1FE00246801FE1E"), result);
    }

    @Test
    public void testXor() throws Exception {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                exp = new Xor(new Const(i), new Const(j));
                result = exp.eval(new ArgsContext());
                assertEquals(new BasicNumber(i ^ j), result);
            }
        }

        Program program = makeProgram(new Xor(id, id), id);
        BasicNumber result = evalProgramWith(program, new BasicNumber("0xF0FF00123400FF0F"));
        assertEquals(BasicNumber.ZERO, result);
    }

    public Program makeProgram(Expression exp, Id id) {
        UnaryLambda lambda = new UnaryLambda(id, exp);
        return new Program(lambda, id);
    }

    public BasicNumber evalProgramWith(Program program, BasicNumber number) {
        return program.eval(number);
    }
}
