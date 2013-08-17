package org.hypnosaur.icfp2013.simulator;

import org.hypnosaur.icfp2013.simulator.basicexpression.BasicNumber;
import org.hypnosaur.icfp2013.simulator.basicexpression.Const;
import org.hypnosaur.icfp2013.simulator.basicexpression.Id;
import org.hypnosaur.icfp2013.simulator.binaryoperators.And;
import org.hypnosaur.icfp2013.simulator.binaryoperators.Plus;
import org.hypnosaur.icfp2013.simulator.binaryoperators.Xor;
import org.hypnosaur.icfp2013.simulator.expressions.BinaryLambda;
import org.hypnosaur.icfp2013.simulator.expressions.Fold;
import org.hypnosaur.icfp2013.simulator.expressions.UnaryLambda;
import org.hypnosaur.icfp2013.simulator.program.Program;
import org.hypnosaur.icfp2013.simulator.unaryoperators.Not;
import org.junit.Test;

/**
 * User: vlad
 * Date: 8/17/13
 * Time: 9:56 PM
 */
public class SimulatorTest {


    private void evalAndPrint(Program program, BasicNumber number) {
        BasicNumber result = program.eval(number);

        System.out.println("Program: " + program.getCode());
        System.out.println("Program size: " + program.size());
        System.out.println("Program ops: " + program.ops());
        System.out.println("Parameter: " + number);
        System.out.println("Result: " + result);
        System.out.println();
    }

    @Test
    public void testSimpleProgram1() {
        Program program = buildSimpleProgram1();
        evalAndPrint(program, BasicNumber.ZERO);
    }

    private Program buildSimpleProgram1() {
        Id.resetIds();
        Id x = new Id();
        UnaryLambda lambda = new UnaryLambda(x, new Not(x));
        return new Program(lambda, x);
    }

    @Test
    public void testSimpleProgram2() {
        Program program = buildSimpleProgram2();
        evalAndPrint(program, new BasicNumber("0x1122334455667788"));
    }

    private Program buildSimpleProgram2() {
        Id.resetIds();
        Id x = new Id();
        UnaryLambda lambda = new UnaryLambda(x, new And(x, new Const(1)));
        return new Program(lambda, x);
    }

    @Test
    public void testSimpleProgram3() {
        Program program = buildSimpleProgram3();
        evalAndPrint(program, new BasicNumber("0x0102030405060708"));
    }

    private Program buildSimpleProgram3() {
        Id.resetIds();
        Id x = new Id();
        Id y = new Id();
        Id z = new Id();

        UnaryLambda lambda = new UnaryLambda(x, new Fold(x, new Const(0), new BinaryLambda(y, z, new Plus(y, z))));
        return new Program(lambda, x);
    }


    @Test
    public void testSimpleProgram4() {
        Program program = buildSimpleProgram4();
        evalAndPrint(program, new BasicNumber("0x0102030405060708"));
    }

    private Program buildSimpleProgram4() {
        Id.resetIds();
        Id x = new Id();

        UnaryLambda lambda = new UnaryLambda(x, new Xor(x, new Not(x)));
        return new Program(lambda, x);
    }


}
