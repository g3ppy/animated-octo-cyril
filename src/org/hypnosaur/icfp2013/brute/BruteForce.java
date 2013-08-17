package org.hypnosaur.icfp2013.brute;

import com.google.common.collect.Sets;
import org.hypnosaur.icfp2013.simulator.basicexpression.Const;
import org.hypnosaur.icfp2013.simulator.basicexpression.Id;
import org.hypnosaur.icfp2013.simulator.binaryoperators.BinaryOperator;
import org.hypnosaur.icfp2013.simulator.expressions.Expression;
import org.hypnosaur.icfp2013.simulator.unaryoperators.UnaryOperator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

/**
 * User: vlad
 * Date: 8/17/13
 * Time: 11:57 PM
 */

public class BruteForce {

    ArrayList<LinkedList<Expression>> tree;
    LinkedList<String> unaryOperators;
    LinkedList<String> binaryOperators;

    public BruteForce() {
        this.tree = new ArrayList<>();
        this.unaryOperators = new LinkedList<>();
        this.binaryOperators = new LinkedList<>();


        for (int i = 0; i < 50; i++) {
            tree.add(i, new LinkedList<Expression>());
        }
    }

    public void addOperators(LinkedList<String> operators) {
        for (String operator : operators) {
            addOperator(operator);
        }
    }

    public void addOperator(String operator) {
        Set<String> binaries = Sets.newHashSet("and", "or", "plus", "xor");
        if (binaries.contains(operator)) {
            addBinaryOperator(operator);
        }

        Set<String> unaries = Sets.newHashSet("not", "shl1", "shr1", "shr4", "shr16");
        if (unaries.contains(operator)) {
            addUnaryOperator(operator);
        }
    }

    private void addBinaryOperator(String binaryOperator) {
        binaryOperators.add(binaryOperator);
    }

    private void addUnaryOperator(String unaryOperator) {
        unaryOperators.add(unaryOperator);
    }

    public void computeTreeForSize(int size) {
        LinkedList<Expression> firstLevel = new LinkedList<>();
        firstLevel.add(new Id());
        firstLevel.add(new Const(0));
        firstLevel.add(new Const(1));
        tree.set(1, firstLevel);


        for (int i = 2; i < size; i++) {
            System.out.println("[DEBUG] Level: " + i);

            for (String clazz : unaryOperators) {
                computeAndAddUnaryoperatorForLevel(clazz, i);
            }

            for (String clazz : binaryOperators) {
                computeAndAddBinaryOperatorForLevel(clazz, i);
            }
        }


    }

    private void computeAndAddUnaryoperatorForLevel(String unaryOperator, int level) {
        for (Expression expression : tree.get(level - 1)) {
            tree.get(level).addLast(UnaryOperator.buildByName(unaryOperator, expression));
        }

    }

    private void computeAndAddBinaryOperatorForLevel(String binaryOperator, int level) {
        for (int rightSide = 1; rightSide <= (level - 1) / 2; rightSide++) {
            int leftSide = level - 1 - rightSide;


            if (rightSide == leftSide) {
                LinkedList<Expression> levelTree = tree.get(rightSide);
                int levelSize = levelTree.size();

                for (int i = 0; i < levelSize; i++) {
                    for (int j = i; j < levelSize; j++) {
                        tree.get(level).addLast(BinaryOperator.buildByName(binaryOperator, levelTree.get(i), levelTree.get(j)));
                    }
                }

                continue;
            }

            for (Expression rightExp : tree.get(rightSide)) {
                for (Expression leftExp : tree.get(leftSide)) {
                    tree.get(level).addLast(BinaryOperator.buildByName(binaryOperator, leftExp, rightExp));
                }
            }
        }
    }

    public void printAll(int size) {
        for (int i = 1; i < size; i++) {
            System.out.println("Level --> " + i);
            System.out.println("Size --> " + tree.get(i).size());
            for (Expression expression : tree.get(i)) {
                System.out.println(expression.toCode());
            }
            System.out.println();
        }

    }
}
