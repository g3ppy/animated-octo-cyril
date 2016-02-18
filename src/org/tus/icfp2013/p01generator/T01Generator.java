package org.tus.icfp2013.p01generator;

import org.tus.icfp2013.simulator.BinaryOperator;
import org.tus.icfp2013.simulator.Expression;
import org.tus.icfp2013.simulator.Program;
import org.tus.icfp2013.simulator.UnaryOperator;
import org.tus.icfp2013.simulator.expressions.*;

import java.util.*;

/**
 * @author <a href="mailto:padreati@yahoo.com">Aurelian Tutuianu</a>
 */
public class T01Generator {

    private final String template;
    private final HashSet<String> opSet;
    // asta contine cate operatii de fiecare tip SOp pot avea
    private final HashMap<String, Integer> freq = new HashMap<String, Integer>();
    // asta contine cati candidati viabili pot avea pentru fiecare tip SOp
    private final HashMap<String, Set<String>> candidates = new HashMap<String, Set<String>>();
    // asta contine producatorii
    private List<Producer> producers;
    private final boolean debug;

    // separator
    private static final String SEPARATOR = ",";

    public T01Generator(String structure, String[] operators, boolean debug) {
        this.debug = debug;
        StringBuilder sb = new StringBuilder();
        char[] sops = structure.toCharArray();
        sb.append(sops[0]);
        for (int i = 1; i < sops.length; i++) {
            sb.append(SEPARATOR).append(sops[i]);
        }
        template = sb.toString();
        opSet = new HashSet<String>(Arrays.asList(operators));
        init();
    }

    public List<String> generateTemplates() {
        Set<String> t01templates = new HashSet<String>();

        t01templates.add(template);
        if (debug) {
            System.out.print("t01gen:");
        }
        int step = 1;
        for (Producer p : producers) {
            t01templates = p.produce(t01templates);
            if (debug) {
                System.out.print(t01templates.size() + ",");
            }
            step++;
        }
        if (debug) {
            System.out.println();
        }
        return new ArrayList<String>(t01templates);
    }

    public List<Program> generate() {
        return buildPrograms(generateTemplates());
    }

    private void init() {
        // lista de frecvente, e simplu, numaram aparitiile din structura
        String[] sops = template.split(SEPARATOR, -1);
        for (String sop : sops) {
            if (!freq.containsKey(sop)) {
                freq.put(sop, 0);
                candidates.put(sop, new HashSet<String>());
            }
            freq.put(sop, freq.get(sop) + 1);
        }

        // acum pun candidatii viabili
        for (String sOp : freq.keySet()) {
            for (String op : SOp.valueOf(sOp).getOperators()) {
                if (opSet.contains(op)) {
                    candidates.get(sOp).add(op);
                }
            }
        }

        producers = new ArrayList<Producer>();
        producers.add(new TProducer(this));
        producers.add(new NProducer(this));
        producers.add(new LVFilter(this));
        producers.add(new CProducer(this));
        producers.add(new SProducer(this));
        producers.add(new LProducer(this));
        producers.add(new VProducer(this));
    }

    public HashSet<String> getOpSet() {
        return opSet;
    }

    public HashMap<String, Integer> getFreq() {
        return freq;
    }

    public HashMap<String, Set<String>> getCandidates() {
        return candidates;
    }

    public String getTemplate() {
        return template;
    }

    public String[] split(String template) {
        return template.split(SEPARATOR, -1);
    }

    public String concatenate(String[] tokens) {
        StringBuilder sb = new StringBuilder();
        sb.append(tokens[0]);
        for (int i = 1; i < tokens.length; i++) {
            sb.append(",").append(tokens[i]);
        }
        return sb.toString();
    }

    private List<Program> buildPrograms(List<String> templates) {
        List<Program> rez = new ArrayList<Program>();
        for (String tmpl : templates) {
            String[] parts = split(tmpl);
            rez.add(new T01ProgramParser(parts).parse());

        }
        return rez;
    }

    private List<Program> buildProgramsOld(List<String> templates) {
        List<Program> rez = new ArrayList<Program>();
        for (String line : templates) {
            String[] tokens = split(getTemplate());
            SOp[] sops = new SOp[tokens.length];
            for (int i = 0; i < sops.length; i++) {
                sops[i] = SOp.valueOf(tokens[i]);
            }
            int[] parents = new int[tokens.length];
            dfs(-1, 0, sops, parents);

            String[] parts = split(line);

            Id.resetIds();
            Id x = new Id(), y = new Id(), z = new Id();
            Expression[] exps = new Expression[tokens.length];
            for (int i = 0; i < exps.length; i++) {
                if (sops[i] == SOp.L) {
                    if (parts[i].equals("y")) {
                        exps[i] = y;
                    }
                    if (parts[i].equals("z")) {
                        exps[i] = z;
                    }
                    if (parts[i].equals("0")) {
                        exps[i] = new Const(0);
                    }
                    if (parts[i].equals("1")) {
                        exps[i] = new Const(1);
                    }
                }
            }

            boolean completed = false;
            while (!completed) {
                completed = true;
                for (int i = 1; i < exps.length; i++) {
                    if (exps[i] != null && exps[parents[i]] == null) {
                        completed = false;
                        if (sops[parents[i]] == SOp.S) {
                            exps[parents[i]] = UnaryOperator.buildByName(parts[parents[i]], exps[i]);
                        }
                        if (sops[parents[i]] == SOp.C) {
                            int sibling = -1;
                            for (int j = 0; j < exps.length; j++) {
                                if (parents[j] == parents[i] && j != i) {
                                    sibling = j;
                                }
                            }
                            if (exps[sibling] != null) {
                                exps[parents[i]] = BinaryOperator.buildByName(parts[parents[i]], exps[i], exps[sibling]);
                            }
                        }
                        if (sops[parents[i]] == SOp.N) {
                            int sibling = -1;
                            for (int j = 0; j < exps.length; j++) {
                                if (parents[j] == parents[i] && j != i) {
                                    sibling = j;
                                }
                            }
                            if (exps[sibling] != null) {
                                int first = i, second = sibling;
                                if (second < first) {
                                    int tmp = first;
                                    first = second;
                                    second = tmp;
                                }
                                BinaryLambda bl = new BinaryLambda(y, z, exps[second]);
                                Fold f = new Fold(exps[first], new Const(0), bl);
                                exps[parents[i]] = f;
                            }
                        }
                        if (sops[parents[i]] == SOp.T) {
                            int[] s = new int[]{i, -1, -1};
                            for (int j = 0; j < exps.length; j++) {
                                if (parents[j] == parents[i] && j != i) {
                                    if (s[1] == -1) {
                                        s[1] = j;
                                    } else {
                                        s[2] = j;
                                    }
                                }
                            }
                            if (exps[s[1]] != null && exps[s[2]] != null) {
                                Arrays.sort(s);
                                if (parts[parents[i]].equals("fold")) {
                                    BinaryLambda bl = new BinaryLambda(y, z, exps[s[2]]);
                                    Fold f = new Fold(exps[s[0]], exps[s[1]], bl);
                                    exps[parents[i]] = f;
                                }
                                if (parts[parents[i]].equals("if0")) {
                                    If0 f = new If0(exps[s[0]], exps[s[1]], exps[s[2]]);
                                    exps[parents[i]] = f;
                                }
                            }
                        }
                    }
                }
            }

            UnaryLambda ul = new UnaryLambda(x, exps[0]);
            Program p = new Program(ul, x);

            rez.add(p);
        }
        return rez;
    }

    private int dfs(int parent, int pos, SOp[] sops, int[] parents) {
        if (pos == sops.length) {
            return pos;
        }
        parents[pos] = parent;
        int newparent = pos;
        pos++;
        for (int i = 0; i < sops[newparent].getChildren(); i++) {
            pos = dfs(newparent, pos, sops, parents);
        }
        return pos;
    }
}

enum SOp {

    V(0, new String[]{"0", "1", "y", "z"}),
    L(0, new String[]{"0", "1"}),
    S(1, new String[]{"not", "shl1", "shr1", "shr4", "shr16"}),
    C(2, new String[]{"plus", "xor", "or", "and"}),
    N(2, new String[]{"tfold"}),
    T(3, new String[]{"if0", "fold"}),;

    private final int children;
    private final String[] operators;

    private SOp(int children, String[] operators) {
        this.operators = operators;
        this.children = children;
    }

    public String[] getOperators() {
        return operators;
    }

    public int getChildren() {
        return children;
    }

}
