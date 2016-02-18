package org.tus.icfp2013.sgenerator;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by kong
 * Time: 8/11/13 3:46 PM
 */
public class StructureGeneratorTest {

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        StructureGenerator gen = new StructureGenerator(
                12,
                new HashSet<String>() {{
                    add("if0");
                    add("and");
                    add("or");
                    add("shr4");
                }}); // problem ID: 0Lv5BKKtBcB0jqRlaRo38w1F
        Set<String> rez = gen.generate();
        time = (System.currentTimeMillis() - time) / 1000;
        System.out.println("Problem solved in " + time + " seconds");
        System.out.println();

        time = System.currentTimeMillis();
        gen = new StructureGenerator(
                18,
                new HashSet<String>() {{
                    add("and");
                    add("fold");
                    add("if0");
                    add("or");
                    add("plus");
                    add("shr1");
                    add("xor");
                }}); // problem ID: 067dorCBKDdiM5JnqrvwLGa1
        rez = gen.generate();
        time = (System.currentTimeMillis() - time) / 1000;
        System.out.println("Problem solved in " + time + " seconds");
        System.out.println();

        time = System.currentTimeMillis();
        gen = new StructureGenerator(
                20,
                new HashSet<String>() {{
                    add("if0");
                    add("not");
                    add("or");
                    add("plus");
                    add("shl1");
                    add("shr1");
                    add("shr16");
                    add("xor");
                }}); // problem ID: 0BLu958BAkvvS9LSbdQlZxPU
        rez = gen.generate();
        time = (System.currentTimeMillis() - time) / 1000;
        System.out.println("Problem solved in " + time + " seconds");
        System.out.println();

/*        time = System.currentTimeMillis();
        gen = new StructureGenerator(
                23,
                new HashSet<String>() {{
                    add("if0");
                    add("or");
                    add("plus");
                    add("shl1");
                    add("shr4");
                }}); // problem ID: 06h5xkC5PQ8kfaIk43GnsAOq
        gen.generate();
        time = (System.currentTimeMillis() - time) / 1000;
        System.out.println("Problem solved in " + time + " seconds");
        System.out.println();*/
    }

}

