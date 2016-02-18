package org.tus.icfp2013.simulator;

import org.tus.icfp2013.simulator.expressions.*;
import org.tus.icfp2013.simulator.operators.And;
import org.tus.icfp2013.simulator.operators.Not;
import org.tus.icfp2013.simulator.operators.Plus;
import org.tus.icfp2013.simulator.operators.Xor;

import java.nio.ByteBuffer;

/**
 * Created by kong
 * Time: 8/9/13 4:51 PM
 */
public class ProgramTest {

    public static void main(String[] notUsed) {
        Program prog = buildSimpleProg();

        Number param = Number.ZERO;

        Number rez = prog.eval(param);

        System.out.println("Program: " + prog.getCode());
        System.out.println("Program size: " + prog.size());
        System.out.println("Program ops: " + prog.ops());
        System.out.println("Parameter: " + param);
        System.out.println("Result: " + rez);
        System.out.println();

        prog = buildSimpleProg2();
        param = new Number("0x1122334455667788");
        rez = prog.eval(param);

        System.out.println("Program: " + prog.getCode());
        System.out.println("Program size: " + prog.size());
        System.out.println("Program ops: " + prog.ops());
        System.out.println("Parameter: " + param);
        System.out.println("Result: " + rez);
        System.out.println();

        prog = buildSimpleProg3();
        param = new Number("0x0102030405060708");
        rez = prog.eval(param);

        System.out.println("Program: " + prog.getCode());
        System.out.println("Program size: " + prog.size());
        System.out.println("Program ops: " + prog.ops());
        System.out.println("Parameter: " + param);
        System.out.println("Result: " + rez);
        System.out.println();


        prog = buildSimpleProg4();
        param = new Number("0x0102030405060708");
        rez = prog.eval(param);

        System.out.println("Program: " + prog.getCode());
        System.out.println("Program size: " + prog.size());
        System.out.println("Program ops: " + prog.ops());
        System.out.println("Parameter: " + param);
        System.out.println("Result: " + rez);
        System.out.println();


        prog = buildSimpleProg5();
        System.out.println("Program: " + prog.getCode());
        System.out.println("Program size: " + prog.size());

        for (int i = 0; i < 100; i ++) {

            byte[] bytes = ByteBuffer.allocate(8).putLong(i).array();

            param = new Number(bytes);

//            System.out.println("Param: " + param);

            rez = prog.eval(param);
//            System.out.println("Result: " + rez);

//            System.out.println(param + " " + rez);
            System.out.println(ByteBuffer.wrap(param.getBytes()).getLong() + " " + ByteBuffer.wrap(rez.getBytes()).getLong());


//            System.out.println();
        }
    }

    private static Program buildSimpleProg() {
        Id.resetIds();
        Id x = new Id();
        UnaryLambda lambda = new UnaryLambda(x, new Not(x));
        return new Program(lambda, x);
    }

    private static Program buildSimpleProg2() {
        Id.resetIds();
        Id x = new Id();
        UnaryLambda lambda = new UnaryLambda(x, new And(x, new Const(1)));
        return new Program(lambda, x);
    }

    private static Program buildSimpleProg3() {
        Id.resetIds();
        Id x = new Id();
        Id y = new Id();
        Id z = new Id();

        UnaryLambda lambda = new UnaryLambda(x, new Fold(x, new Const(0), new BinaryLambda(y, z, new Plus(y, z))));
        return new Program(lambda, x);
    }

    private static Program buildSimpleProg4() {
        Id.resetIds();
        Id x = new Id();

        UnaryLambda lambda = new UnaryLambda(x, new Xor(x, new Not(x)));
        return new Program(lambda, x);
    }

    private static Program buildSimpleProg5() {
        Id.resetIds();
        Id x = new Id();

        UnaryLambda lambda = new UnaryLambda(x, new If0(new And(x, new Plus(new Const(1), new Const(1))), new Plus(x, new Const(1)), new Not(x)));
        return new Program(lambda, x);
    }

}

