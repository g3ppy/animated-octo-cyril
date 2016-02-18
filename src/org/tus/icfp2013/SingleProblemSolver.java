package org.tus.icfp2013;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.tus.icfp2013.client.*;
import org.tus.icfp2013.p01generator.T01Generator;
import org.tus.icfp2013.pxgenerator.Substituter;
import org.tus.icfp2013.reducer.Reducer;
import org.tus.icfp2013.sgenerator.StructureGenerator;
import org.tus.icfp2013.simulator.Number;
import org.tus.icfp2013.simulator.Program;
import org.tus.icfp2013.simulator.expressions.Const;

import java.util.*;

/**
 * @author <a href="mailto:padreati@yahoo.com">Aurelian Tutuianu</a>
 */
public class SingleProblemSolver {

    public static LinkedList<Number> paramsMin = Lists.newLinkedList();

    static {
        // do not change the first 10 params
        paramsMin.addLast(Number.ZERO);
        paramsMin.addLast(Number.ONE);
        paramsMin.addLast(new Number("0x00000000000000FF"));
        paramsMin.addLast(new Number("0x000000000000FF00"));
        paramsMin.addLast(new Number("0x0000000000FF0000"));
        paramsMin.addLast(new Number("0x00000000FF000000"));
        paramsMin.addLast(new Number("0x000000FF00000000"));
        paramsMin.addLast(new Number("0x0000FF0000000000"));
        paramsMin.addLast(new Number("0x00FF000000000000"));
        paramsMin.addLast(new Number("0xFF00000000000000"));
        paramsMin.addLast(new Number("0xFFFFFFFFFFFFFFFF"));
        paramsMin.addLast(new Number("0xFFFFFFFFFFFFFF00"));
        paramsMin.addLast(new Number("0xFFFFFFFFFFFF00FF"));
        paramsMin.addLast(new Number("0xFFFFFFFFFF00FFFF"));
        paramsMin.addLast(new Number("0xFFFFFFFF00FFFFFF"));
        paramsMin.addLast(new Number("0xFFFFFF00FFFFFFFF"));
        paramsMin.addLast(new Number("0xFFFF00FFFFFFFFFF"));
        paramsMin.addLast(new Number("0xFF00FFFFFFFFFFFF"));
        paramsMin.addLast(new Number("0x00FFFFFFFFFFFFFF"));

        // random from here to 255
    }

    public static String[] numbersToStrings(LinkedList<Number> params) {
        String[] rez = new String[params.size()];

        int i = 0;
        for (Number param : params) {
            rez[i] = param.toString();
            i++;
        }

        return rez;
    }

    private final Problem p;
    private final ICFPClient client;
    @SuppressWarnings("unchecked")
    private final LinkedList<Number> params = (LinkedList<Number>) paramsMin.clone();

    public SingleProblemSolver(Problem p, ICFPClient client) {
        this.p = p;
        this.client = client;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
//        Problem p = new Problem("0Lv5BKKtBcB0jqRlaRo38w1F", 12, new String[]{"and", "if0", "or", "shr4"}, false, Float.MIN_VALUE);
//        Problem p = new Problem("0Fa85NFBCV1EQAHA3Ocu1W0n", 14, new String[]{"if0", "or", "shl1", "tfold"}, false, Float.MIN_VALUE);
//        Problem p = new Problem("16B2Wg9RN3Qx3kjR9otSMUmE", 10, new String[]{"and", "if0", "shr4", "xor"}, false, Float.MIN_VALUE); /// FAILED PROGRAM !!!!!!!!!!!!!!!!!!!!!!

        Problem p = new Problem("3o4VaKqM0JkbEdVUFAZpKKsw", 13, new String[]{
                "if0",
                "shl1",
                "shr1",
                "tfold"}, false, Float.MIN_VALUE);


        ICFPClient wsc = ICFPWebClient.create("0550bjHM9InBehh47QR4leYJcjMAzvCh2p0G1mWL"); // vlad's team ID
//    ICFPClient wsc = ICFPWebClient.create();

        SingleProblemSolver solver = new SingleProblemSolver(p, wsc);
        solver.trySolve();
    }

    @SuppressWarnings("unchecked")
    public boolean trySolve() {
        Map<Number, Set<Program>> buckets = init(p);
        System.out.println("Init finished, First Call to the web service");
        Map<Number, Number> evaluatedParams = evalParams(p, params); // call web service
        Timer t = new Timer();

        Number val0 = evaluatedParams.get(Number.ZERO);
        Set<Program> val0Progs = buckets.get(val0);
        Number val1 = evaluatedParams.get(Number.ONE);
        Set<Program> val1Progs = buckets.get(val1);

        Reducer r = new Reducer(new ArrayList<Program>(val0Progs), new ArrayList<Program>(val1Progs));
        Iterator<Program> progsIter = r.plzGetIterator();

        System.out.println("Started Iterating");
        while (progsIter.hasNext()) {
            if (t.expired())
                return false;
            Program prog = progsIter.next();
            if (valid(prog, evaluatedParams)) {
                if (guess(p, prog, evaluatedParams)) {
                    System.out.println("Problem solved!");
                    return true;
                }
            }
        }

        System.out.println("Solution Not Found, starting HardCore Substituter...");
        //---------------------------------------------------------
        // Backup Substituter Code
        Substituter substituter = new Substituter();
        for (Program program : val0Progs) {
            if (t.expired())
                return false;
            Iterator<Program> iterator = substituter.subsituteConstWithId(program, new Const(0));
            while (iterator.hasNext()) {
                Program prog = iterator.next();
                if (valid(prog, evaluatedParams)) {
                    if (guess(p, prog, evaluatedParams)) {
                        System.out.println("Problem solved!");
                        return true;
                    }
                }
            }
        }

        substituter = new Substituter();
        for (Program program : val1Progs) {
            if (t.expired())
                return false;
            Iterator<Program> iterator = substituter.subsituteConstWithId(program, new Const(1));
            while (iterator.hasNext()) {
                Program prog = iterator.next();
                if (valid(prog, evaluatedParams)) {
                    if (guess(p, prog, evaluatedParams)) {
                        System.out.println("Problem solved!");
                        return true;
                    }
                }
            }
        }
        //---------------------------------------------------------

        System.out.println("N-am gasit solutie, ceva o mers prost, uite params: ");

        for (Map.Entry entry : evaluatedParams.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }

        return false;
    }

    private boolean guess(Problem p, Program prog, Map<Number, Number> evaluatedParams) {
        System.out.println("Incercam Solutia = " + prog.getCode());
        GuessResponse response = client.guess(p.getId(), prog.getCode());
        if (response.getStatus().equalsIgnoreCase("mismatch")) {
            String[] mismatchedValues = response.getValues();
            System.out.println("mismatch: " + Arrays.toString(mismatchedValues));
            evaluatedParams.put(new Number(mismatchedValues[0]), new Number(mismatchedValues[1]));
        }

        return response.getStatus().equalsIgnoreCase("win");
    }

    private static boolean valid(Program prog, Map<Number, Number> evaluatedParams) {
        for (Number arg : evaluatedParams.keySet())
            if (!prog.eval(arg).equals(evaluatedParams.get(arg)))
                return false;
        return true;
    }

    private Map<Number, Number> evalParams(Problem probl, LinkedList<Number> params) {
        Map<Number, Number> rez = Maps.newHashMap();
//        for (int i = 0; i < params.length; i++) {
//            rez.put(params[i], Number.ZERO); // call web service
//        }
        String[] arg = numbersToStrings(params);
        EvalResponse response = client.evalProgramId(probl.getId(), arg);
        String[] outputs = response.getOutputs();

        for (int i = 0; i < outputs.length; i++) {
            rez.put(params.get(i), new Number(outputs[i]));
        }

        return rez;
    }

    private static Map<Number, Set<Program>> init(Problem p) {
        HashSet<String> opsSet = new HashSet<String>(Arrays.asList(p.getOperators()));
        StructureGenerator gen = new StructureGenerator(p.getSize(), opsSet);
        Set<String> structures = gen.generate();
        List<Program> programs = new ArrayList<Program>();
        System.out.println("Started T01 Generator");
        for (String structure : structures) {
            T01Generator tgen = new T01Generator(structure, p.getOperators(), false);
            programs.addAll(tgen.generate());
        }

        System.out.println("Found " + programs.size() + " programs");

        System.out.println("Bucketing based on values without x parameter");
        Map<Number, Set<Program>> map = buildEvaluatedMap(programs);
        printMapStat(map);

        return map;
    }

    private static Map<Number, Set<Program>> buildEvaluatedMap(List<Program> programs) {
        Map<Number, Set<Program>> map = new HashMap<Number, Set<Program>>();
        for (Program program : programs) {
            Number value = program.eval(Number.ZERO);
            if (!map.containsKey(value)) {
                map.put(value, new HashSet<Program>());
            }
            map.get(value).add(program);
        }
        return map;
    }

    public static void printMapStat(Map<Number, Set<Program>> map) {
        int total = 0;
        for (Number number : map.keySet()) {
            total += map.get(number).size();
        }
        int max = 0;
        for (Number number : map.keySet()) {
//            System.out.println(String.format("%s -> count %d (%.3f%%)", number.toString(), map.get(number).size(), 100 * map.get(number).size() / total));
            if (map.get(number).size() > max)
                max = map.get(number).size();
        }

        System.out.println("Largest bucket: " + max + " (" + 100 * max / total + "%)");
    }

}

class Timer {
    private final long startTime = System.currentTimeMillis();

    public boolean expired() {
        return (System.currentTimeMillis() - startTime) / (5 * 60 * 1000) > 0;
    }

}

