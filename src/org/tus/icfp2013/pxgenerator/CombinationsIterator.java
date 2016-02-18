package org.tus.icfp2013.pxgenerator;

import com.google.common.collect.Lists;
import org.tus.icfp2013.simulator.Expression;
import org.tus.icfp2013.simulator.expressions.Const;
import org.tus.icfp2013.simulator.expressions.Id;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * User: vlad
 * Date: 8/10/13
 * Time: 5:41 PM
 */
public class CombinationsIterator implements Iterator {

    private Const constantine;
    private Id id;
    private int maximumPosition;
    private int currentPosition;
    private int size;

    public CombinationsIterator(Const constantine, Id id, int size) {
        this.constantine = constantine;
        this.id = id;
        this.size = size;
        this.maximumPosition = 1 << size;
        this.currentPosition = 0;
    }

    private LinkedList<Expression> generateNextPermutation(int position) {
        LinkedList<Expression> expressionList = Lists.newLinkedList();
        for (int i = 0; i < size; i++) {
            Expression exp = id;
            if ((position & (1 << i)) != 0) {
                exp = constantine;
            }

            expressionList.addLast(exp);
        }
        return expressionList;
    }

    @Override
    public boolean hasNext() {
        return (currentPosition < maximumPosition);
    }

    @Override
    public LinkedList<Expression> next() {
        LinkedList<Expression> returnValue = generateNextPermutation(currentPosition);
        currentPosition++;
        return returnValue;
    }

    @Override
    public void remove() {
        throw new NotImplementedException();
    }

}

