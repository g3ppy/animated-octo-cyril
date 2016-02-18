package org.tus.icfp2013;

import org.tus.icfp2013.client.Problem;
import org.tus.icfp2013.sgenerator.StructureGenerator;
import org.tus.icfp2013.simulator.*;
import org.tus.icfp2013.simulator.Number;
import org.tus.icfp2013.p01generator.T01Generator;

import java.util.*;

/**
 * Created by kong Time: 8/11/13 3:51 PM
 */
public class SolverTest {

    public static void main2(String[] args) {
        String struct = "TLLT(C(CL(SSL))L)SSLL";
        String[] ops = new String[]{"plus", "xor", "not", "shr1", "fold"};
        List<Program> ps = new T01Generator(struct.replaceAll("\\(", "").replaceAll("\\)", ""), ops, true).generate();
        List<String> codes = new ArrayList<String>();
        for (Program pp : ps) {
            codes.add(pp.getCode());
        }
        Collections.sort(codes);
        for (String code : codes) {
            System.out.println(code);
        }
    }

    public static void main(String[] args) {
        //size":13,"operators":["if0","not","or","shr1","tfold"]},
//        Problem p = new Problem("test", 13, new String[]{"if0", "not", "or", "shr1", "tfold"}, false, 0f);
//        Problem p = new Problem("V04sL7iq9MmoPyj0vkUQ6o7B", 10, new String[]{"and", "if0", "or", "shr16"}, false, Float.MIN_VALUE);
//        Problem p = new Problem("7wQ0hXClR2yp4T1SBWsZpsHu", 15, new String[]{"fold", "not", "or", "plus", "xor"}, false, Float.MIN_VALUE);
//        Problem p = new Problem("test", 8, new String[]{"not", "and", "xor"}, false, 0f);
        Problem p = new Problem("HsQSQG9vdKssmG2Ue7ATP3ms", 10, new String[]{"and", "if0", "shr4"}, false, Float.MIN_VALUE);
//        Problem p = new Problem("test", 3, new String[]{"not"}, false, 0f);
        Map<org.tus.icfp2013.simulator.Number, HashSet<Program>> map = new SolverTest().build01Templates(p);

        double total = 0;
        for (Number number : map.keySet()) {
            total += map.get(number).size();
        }

        for (org.tus.icfp2013.simulator.Number number : map.keySet()) {
            System.out.println(String.format("%s -> count %d (%.3f%%)", number.toString(), map.get(number).size(), 10 * map.get(number).size() / total));
        }
//        Substituter subst = new Substituter();
//        Substituter.ProgramsIterator it = subst.subsituteConstWithId(programs.get(0), new Const(1));
//        while (it.hasNext()) {
//            System.out.println(it.next().getCode());
//        }

    }

    private Map<org.tus.icfp2013.simulator.Number, HashSet<Program>> build01Templates(Problem p) {
        StructureGenerator sg = new StructureGenerator(p.getSize(), new HashSet<String>(Arrays.asList(p.getOperators())));
        Set<String> structures = sg.generate();

        Set<String> compactStruct = new HashSet<String>();
        for (String struct : structures) {
            String compact = struct.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(" ", "");
//            System.out.println(struct + " -> " + compact);
            compactStruct.add(compact);
        }

        List<Program> programs = new ArrayList<Program>();
        for (String struct : compactStruct) {
            List<Program> ps = new T01Generator(struct, p.getOperators(), true).generate();
            programs.addAll(ps);
//            for (Program pp : ps) {
//                System.out.println(pp.getCode());
//            }
        }

        Map<org.tus.icfp2013.simulator.Number, HashSet<Program>> map = new HashMap<Number, HashSet<Program>>();
        for (Program program : programs) {
            org.tus.icfp2013.simulator.Number value = program.eval(org.tus.icfp2013.simulator.Number.ZERO);
            if (!map.containsKey(value)) {
                map.put(value, new HashSet<Program>());
            }
            map.get(value).add(program);
        }
        return map;
    }
}
