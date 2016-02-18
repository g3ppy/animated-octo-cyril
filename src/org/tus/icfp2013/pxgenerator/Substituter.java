package org.tus.icfp2013.pxgenerator;

import org.tus.icfp2013.simulator.Expression;
import org.tus.icfp2013.simulator.Program;
import org.tus.icfp2013.simulator.expressions.*;
import org.tus.icfp2013.simulator.operators.*;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Class that takes and Expression, a Const and an Id and substitutes the Const with the Id
 * everywhere in the Expression in all possible modes
 * <p/>
 * substituteConstWithId is the central function and it returns an iterator (so it wouldn't
 * consume too much memory
 * <p/>
 * note that there are two implementations of substituteConstWithId, one where you can specify
 * the size, and one where the size is computed automatically
 * <p/>
 * <p/>
 * You can also give a program and the constant as an argument, and it will know to take the ID
 * stored in the program and use it to substitute the constant
 * <p/>
 * <p/>
 * User: vlad
 * Date: 8/10/13
 * Time: 4:23 PM
 */

public class Substituter {

//    public void substituteConstWithId(Program program, Const constantine) {
//    public void substituteConstWithId(UnaryLambda unaryLambda, Const constantine) {

    private int recursiveGetNumberOfConstants(Expression expression, Const constantine) {
        if (expression instanceof Const) {
            if (constantine.equals(expression)) {
                return 1;
            }
            return 0;
        }

        int sum = 0;
        for (int i = 0; i < expression.getOrder(); i++) {
            sum += recursiveGetNumberOfConstants(expression.getSubExpression(i), constantine);
        }

        return sum;
    }

    public ProgramsIterator subsituteConstWithId(Program program, Const constantine) {
        return new ProgramsIterator(program, constantine);
    }

    public ExpressionsIterator substituteConstWithId(Expression expression, Const constantine, Id id) {
        int numberOfConstants = recursiveGetNumberOfConstants(expression, constantine);
        return substituteConstWithId(expression, constantine, id, numberOfConstants);
    }

    public ExpressionsIterator substituteConstWithId(Expression expression, Const constantine, Id id, int numberOfConstants) {
        ExpressionsIterator expressionsIterator = new ExpressionsIterator(expression, constantine, id, numberOfConstants);

        return expressionsIterator;
    }

    // WARNING: UGLIEST PISS OF CODE EVA'
    private Expression buildSubstitutedExpression(Expression expression, Const constantine, LinkedList<Expression> substExprList) {

        Expression newExpression = null;

        if (expression instanceof Const) {
            if (constantine.equals(expression)) {
                return substExprList.pollFirst();
            }
            if (expression.equals(new Const(0))) {
                return new Const(0);
            }
            return new Const(1);
        }

        if (expression instanceof Id) {
            return expression;
        }

        //Magic Stuff
        if (expression instanceof UnaryLambda) {
            Id unaryLambdaId = ((UnaryLambda) expression).getId();
            Expression operand = buildSubstitutedExpression(expression.getSubExpression(0), constantine, substExprList);
            return new UnaryLambda(unaryLambdaId, operand);
        }

        if (expression instanceof BinaryLambda) {
            Id binaryLambda1 = ((BinaryLambda) expression).getId1();
            Id binaryLambda2 = ((BinaryLambda) expression).getId2();
            Expression operand = buildSubstitutedExpression(expression.getSubExpression(0), constantine, substExprList);
            return new BinaryLambda(binaryLambda1, binaryLambda2, operand);
        }

        if (expression instanceof Fold) {
            Expression firstOperand = buildSubstitutedExpression(expression.getSubExpression(0), constantine, substExprList);
            Expression secondOperand = buildSubstitutedExpression(expression.getSubExpression(1), constantine, substExprList);
            Expression lambda = buildSubstitutedExpression(expression.getSubExpression(2), constantine, substExprList);
            return new Fold(firstOperand, secondOperand, (BinaryLambda) lambda);
        }

        if (expression instanceof If0) {
            Expression firstOperand = buildSubstitutedExpression(expression.getSubExpression(0), constantine, substExprList);
            Expression secondOperand = buildSubstitutedExpression(expression.getSubExpression(1), constantine, substExprList);
            Expression thirdOperand = buildSubstitutedExpression(expression.getSubExpression(2), constantine, substExprList);
            return new If0(firstOperand, secondOperand, thirdOperand);
        }

        //Binary Operators
        if (expression instanceof And) {
            Expression firstOperand = buildSubstitutedExpression(expression.getSubExpression(0), constantine, substExprList);
            Expression secondOperand = buildSubstitutedExpression(expression.getSubExpression(1), constantine, substExprList);
            return new And(firstOperand, secondOperand);
        }
        if (expression instanceof Or) {
            Expression firstOperand = buildSubstitutedExpression(expression.getSubExpression(0), constantine, substExprList);
            Expression secondOperand = buildSubstitutedExpression(expression.getSubExpression(1), constantine, substExprList);
            return new Or(firstOperand, secondOperand);
        }

        if (expression instanceof Plus) {
            Expression firstOperand = buildSubstitutedExpression(expression.getSubExpression(0), constantine, substExprList);
            Expression secondOperand = buildSubstitutedExpression(expression.getSubExpression(1), constantine, substExprList);
            return new Plus(firstOperand, secondOperand);
        }

        if (expression instanceof Xor) {
            Expression firstOperand = buildSubstitutedExpression(expression.getSubExpression(0), constantine, substExprList);
            Expression secondOperand = buildSubstitutedExpression(expression.getSubExpression(1), constantine, substExprList);
            return new Xor(firstOperand, secondOperand);
        }

        //Unary Operators
        if (expression instanceof Not) {
            Expression operand = buildSubstitutedExpression(expression.getSubExpression(0), constantine, substExprList);
            return new Not(operand);
        }

        if (expression instanceof Shl1) {
            Expression operand = buildSubstitutedExpression(expression.getSubExpression(0), constantine, substExprList);
            return new Shl1(operand);
        }

        if (expression instanceof Shr1) {
            Expression operand = buildSubstitutedExpression(expression.getSubExpression(0), constantine, substExprList);
            return new Shr1(operand);
        }

        if (expression instanceof Shr4) {
            Expression operand = buildSubstitutedExpression(expression.getSubExpression(0), constantine, substExprList);
            return new Shr4(operand);
        }

        if (expression instanceof Shr16) {
            Expression operand = buildSubstitutedExpression(expression.getSubExpression(0), constantine, substExprList);
            return new Shr4(operand);
        }

        return newExpression;
    }

    public class ExpressionsIterator implements Iterator {
        Expression expression;
        Const constantine;
        CombinationsIterator combinationsIterator;

        public ExpressionsIterator(Expression expression, Const constantine, Id id, int numberOfConstants) {
            combinationsIterator = new CombinationsIterator(constantine, id, numberOfConstants);
            this.expression = expression;
            this.constantine = constantine;
        }

        @Override
        public boolean hasNext() {
            return combinationsIterator.hasNext();
        }

        @Override
        public Expression next() {
            return buildSubstitutedExpression(expression, constantine, combinationsIterator.next());
        }

        @Override
        public void remove() {
        }
    }


    public class ProgramsIterator implements Iterator {

        UnaryLambda unaryLambda;
        Id id;
        ExpressionsIterator expressionsIterator;

        public ProgramsIterator(Program program, Const constantine) {
            this.unaryLambda = program.getLambda();
            this.id = program.getId();
            this.expressionsIterator = substituteConstWithId(unaryLambda, constantine, id);
        }

        @Override
        public boolean hasNext() {
            return expressionsIterator.hasNext();
        }

        @Override
        public Program next() {
            UnaryLambda expression = (UnaryLambda) expressionsIterator.next();
            return new Program(expression, id);
        }

        @Override
        public void remove() {
        }
    }

}
