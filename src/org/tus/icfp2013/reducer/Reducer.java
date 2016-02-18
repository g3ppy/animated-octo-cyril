package org.tus.icfp2013.reducer;

import org.tus.icfp2013.simulator.Expression;
import org.tus.icfp2013.simulator.Program;
import org.tus.icfp2013.simulator.expressions.*;
import org.tus.icfp2013.simulator.operators.*;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * User: vlad
 * Date: 8/11/13
 * Time: 2:55 PM
 */
public class Reducer {
    private final ArrayList<Program> programsWith0;
    private final ArrayList<Program> programsWith1;

    public Reducer(ArrayList<Program> programsWith0, ArrayList<Program> programsWith1) {
        this.programsWith0 = programsWith0;
        this.programsWith1 = programsWith1;
    }

    public ReducerIterator plzGetIterator() {
        return new ReducerIterator();
    }

    boolean programsAreSimilar(Program p0, Program p1) {
        if (!p0.getId().equals(p1.getId())) {
            return false;
        }

        return expressionsAreSimilar(p0.getLambda(), p1.getLambda());
    }

    boolean expressionsAreSimilar(Expression e0, Expression e1) {
        if (!e0.getClass().equals(e1.getClass())) {
            return false;
        }

        if (e0 instanceof Const) {
            if (e0.equals(e1)) {
                return true;
            } else {
                //aici trebuie verificat daca sunt compatibile sau nu
                if (e0.equals(new Const(1)) && e1.equals(new Const(0))) {
                    return false;
                }
                return true;
            }

        }

        boolean areSimilar = true;

        for (int i = 0; i < e0.getOrder(); i++) {
            areSimilar &= expressionsAreSimilar(e0.getSubExpression(i), e1.getSubExpression(i));
        }

        return areSimilar;
    }

    Program substituteMatchingConstWithId(Program p0, Program p1) {
        if (!p0.getId().equals(p1.getId())) {
            return null;
        }
        UnaryLambda unaryLambda = (UnaryLambda) substituteMatchingConstWithId(p0.getLambda(), p1.getLambda(), p0.getId());
        return new Program(unaryLambda, p0.getId());
    }

    Expression substituteMatchingConstWithId(Expression e0, Expression e1, Id id) {
        if (!e0.getClass().equals(e1.getClass())) {
            return null;
        }

        Expression expression = e0;

        if (e0 instanceof Const) {
            if (e0.equals(new Const(0)) && e1.equals(new Const(1))) {
                return id;
            }

            if (e0.equals(new Const(0))) {
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
            Expression operand = substituteMatchingConstWithId(e0.getSubExpression(0), e1.getSubExpression(0), id);
            return new UnaryLambda(unaryLambdaId, operand);
        }

        if (expression instanceof BinaryLambda) {
            Id binaryLambda1 = ((BinaryLambda) expression).getId1();
            Id binaryLambda2 = ((BinaryLambda) expression).getId2();
            Expression operand = substituteMatchingConstWithId(e0.getSubExpression(0), e1.getSubExpression(0), id);
            return new BinaryLambda(binaryLambda1, binaryLambda2, operand);
        }

        if (expression instanceof Fold) {
            Expression firstOperand = substituteMatchingConstWithId(e0.getSubExpression(0), e1.getSubExpression(0), id);
            Expression secondOperand = substituteMatchingConstWithId(e0.getSubExpression(1), e1.getSubExpression(1), id);
            Expression lambda = substituteMatchingConstWithId(e0.getSubExpression(2), e1.getSubExpression(2), id);
            return new Fold(firstOperand, secondOperand, (BinaryLambda) lambda);
        }

        if (expression instanceof If0) {
            Expression firstOperand = substituteMatchingConstWithId(e0.getSubExpression(0), e1.getSubExpression(0), id);
            Expression secondOperand = substituteMatchingConstWithId(e0.getSubExpression(1), e1.getSubExpression(1), id);
            Expression thirdOperand = substituteMatchingConstWithId(e0.getSubExpression(2), e1.getSubExpression(2), id);
            return new If0(firstOperand, secondOperand, thirdOperand);
        }

        //Binary Operators
        if (expression instanceof And) {
            Expression firstOperand = substituteMatchingConstWithId(e0.getSubExpression(0), e1.getSubExpression(0), id);
            Expression secondOperand = substituteMatchingConstWithId(e0.getSubExpression(1), e1.getSubExpression(1), id);
            return new And(firstOperand, secondOperand);
        }
        if (expression instanceof Or) {
            Expression firstOperand = substituteMatchingConstWithId(e0.getSubExpression(0), e1.getSubExpression(0), id);
            Expression secondOperand = substituteMatchingConstWithId(e0.getSubExpression(1), e1.getSubExpression(1), id);
            return new Or(firstOperand, secondOperand);
        }

        if (expression instanceof Plus) {
            Expression firstOperand = substituteMatchingConstWithId(e0.getSubExpression(0), e1.getSubExpression(0), id);
            Expression secondOperand = substituteMatchingConstWithId(e0.getSubExpression(1), e1.getSubExpression(1), id);
            return new Plus(firstOperand, secondOperand);
        }

        if (expression instanceof Xor) {
            Expression firstOperand = substituteMatchingConstWithId(e0.getSubExpression(0), e1.getSubExpression(0), id);
            Expression secondOperand = substituteMatchingConstWithId(e0.getSubExpression(1), e1.getSubExpression(1), id);
            return new Xor(firstOperand, secondOperand);
        }

        //Unary Operators
        if (expression instanceof Not) {
            Expression operand = substituteMatchingConstWithId(e0.getSubExpression(0), e1.getSubExpression(0), id);
            return new Not(operand);
        }

        if (expression instanceof Shl1) {
            Expression operand = substituteMatchingConstWithId(e0.getSubExpression(0), e1.getSubExpression(0), id);
            return new Shl1(operand);
        }

        if (expression instanceof Shr1) {
            Expression operand = substituteMatchingConstWithId(e0.getSubExpression(0), e1.getSubExpression(0), id);
            return new Shr1(operand);
        }

        if (expression instanceof Shr4) {
            Expression operand = substituteMatchingConstWithId(e0.getSubExpression(0), e1.getSubExpression(0), id);
            return new Shr4(operand);
        }

        if (expression instanceof Shr16) {
            Expression operand = substituteMatchingConstWithId(e0.getSubExpression(0), e1.getSubExpression(0), id);
            return new Shr4(operand);
        }

        return expression;
    }

    class ReducerIterator implements Iterator {
        private int maxIndexFor0;
        private int maxIndexFor1;
        private int currentIndexFor0;
        private int currentIndexFor1;


        ReducerIterator() {
            System.out.println("Entered Reducer Iterator");
            System.out.println("Programs With 0: " + programsWith0.size());
            System.out.println("Programs With 1: " + programsWith1.size());

            currentIndexFor0 = 0;
            currentIndexFor1 = 0;

            maxIndexFor0 = programsWith0.size();
            maxIndexFor1 = programsWith1.size();
        }

        @Override
        public boolean hasNext() {
            return (currentIndexFor0 < maxIndexFor0);
        }

        @Override
        public Program next() {

            for (int i = currentIndexFor0; i < maxIndexFor0; i++) {
                Program programWith0 = programsWith0.get(i);
//                System.out.println("Altceva" + programWith0.getCode());

                for (int j = currentIndexFor1; j < maxIndexFor1; j++) {
                    Program programWith1 = programsWith1.get(j);


                    if (programsAreSimilar(programWith0, programWith1)) {

                        currentIndexFor0 = i;
                        currentIndexFor1 = j + 1;

                        Program finalProgram = substituteMatchingConstWithId(programWith0, programWith1);

//                        System.out.println("Program 0" + programWith0.getCode());
//                        System.out.println("Program 1" + programWith1.getCode());
//                        System.out.println("Program 2" + finalProgram.getCode());

                        return finalProgram;
                    }
                }

                currentIndexFor1 = 0;
            }


            currentIndexFor0 = maxIndexFor0;

            Id.resetIds();
            Id id = new Id();
            return new Program(new UnaryLambda(id, new Not(id)), id);
        }

        @Override
        public void remove() {
        }
    }

}
