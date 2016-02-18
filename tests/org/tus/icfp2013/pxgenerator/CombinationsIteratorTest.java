package org.tus.icfp2013.pxgenerator;

import org.tus.icfp2013.simulator.expressions.Const;
import org.tus.icfp2013.simulator.expressions.Id;

/**
 * User: vlad
 * Date: 8/10/13
 * Time: 5:54 PM
 */
public class CombinationsIteratorTest {
    public static void main(String[] notUsed) {
        Const aConst = new Const(0);
        Id id = new Id();
        int size = 4;

        CombinationsIterator iterator = new CombinationsIterator(aConst, id, size);

        int i = 0;
        while (iterator.hasNext()) {
            System.out.println(i + ": " + iterator.next().toString());
            i++;
        }
    }
}
