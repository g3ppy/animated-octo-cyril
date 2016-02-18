package org.tus.icfp2013.pxgenerator;

import org.tus.icfp2013.simulator.Expression;
import org.tus.icfp2013.simulator.Program;
import org.tus.icfp2013.simulator.Number;
import org.tus.icfp2013.simulator.expressions.*;
import org.tus.icfp2013.simulator.operators.*;

import java.util.Iterator;

/**
 * An example of how to use the Substituter Class
 * <p/>
 * User: vlad
 * Date: 8/10/13
 * Time: 4:59 PM
 */
public class SubstituterTest {

    @SuppressWarnings("unchecked")
    public static void main(String[] notUsed) {
        Substituter substituter = new Substituter();
        Id id = new Id();
        Const constantine = new Const(0);

        Expression expression = buildSimpleExpression();
        Iterator<Expression> expressionsIterator = substituter.substituteConstWithId(expression, constantine, id); // should be 1

        System.out.println(String.format("Substituting %s with %s in expression: %s", constantine, id, expression.toCode()));
        while (expressionsIterator.hasNext()) {
            System.out.println(expressionsIterator.next().toCode());
        }
        System.out.println();

        constantine = new Const(1);
        expression = buildSimpleExpression2();
        expressionsIterator = substituter.substituteConstWithId(expression, constantine, id);// should be 3

        System.out.println(String.format("Substituting %s with %s in expression: %s", constantine, id, expression.toCode()));
        while (expressionsIterator.hasNext()) {
            System.out.println(expressionsIterator.next().toCode());
        }
        System.out.println();

        expression = buildSimpleExpression3();
        expressionsIterator = substituter.substituteConstWithId(expression, constantine, id);// should be 2

        System.out.println(String.format("Substituting %s with %s in expression: %s", constantine, id, expression.toCode()));
        while (expressionsIterator.hasNext()) {
            System.out.println(expressionsIterator.next().toCode());
        }
        System.out.println();


        Program program = buildSimpleProgram();
        Iterator<Program> programIterator = substituter.subsituteConstWithId(program, constantine);

        System.out.println(String.format("Substituting %s with %s in expression: %s", constantine, program.getId(), program.getCode()));
        while(programIterator.hasNext()) {

            Program nextProgram = programIterator.next();
            System.out.println(nextProgram.getCode());
            System.out.println("Evaluation is: " + nextProgram.eval(new Number("0x1102333455666789")));

        }
        System.out.println();

    }

    private static Expression buildSimpleExpression() {
        Id.resetIds();
        Id x = new Id();
        Id y = new Id();
        Id z = new Id();

        UnaryLambda lambda = new UnaryLambda(x, new Fold(x, new Const(0), new BinaryLambda(y, z, new Plus(y, z))));
        return lambda;
    }

    private static Expression buildSimpleExpression2() {
        Id.resetIds();
        Id x = new Id();
        Id y = new Id();
        Id z = new Id();

        UnaryLambda lambda = new UnaryLambda(x, new Fold(new And(new Xor(new Const(0), new Const(1)), new Const(1)), new Const(0), new BinaryLambda(y, z, new Plus(new Const(1), z))));
        return lambda;
    }

    private static Expression buildSimpleExpression3() {
        Id.resetIds();
        Id x = new Id();

        UnaryLambda lambda = new UnaryLambda(x, new And(new Or(new Plus(x, new Const(0)), new Const(1)), new Const(1)));
        return lambda;
    }


    private static Program buildSimpleProgram() {
        Id.resetIds();
        Id x = new Id();
        Id y = new Id();
        Id z = new Id();

        UnaryLambda lambda = new UnaryLambda(x, new And(new Fold(new And(x, new Not(new Plus(x, new Const(1)))), new Const(0), new BinaryLambda(y, z, new Or(new Plus(y, new Const(0)), new Const(1)))), x));
        return new Program(lambda, x);
    }
}

