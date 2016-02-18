package org.tus.icfp2013.p01generator;

import org.tus.icfp2013.simulator.BinaryOperator;
import org.tus.icfp2013.simulator.Expression;
import org.tus.icfp2013.simulator.Program;
import org.tus.icfp2013.simulator.UnaryOperator;
import org.tus.icfp2013.simulator.expressions.*;
import org.tus.icfp2013.simulator.operators.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:padreati@yahoo.com">Aurelian Tutuianu</a>
 */
public class T01ProgramParser {

    private final String[] parts;
    private final Id x;
    private final Id y;
    private final Id z;
    ;
    private int pos = 0;

    public T01ProgramParser(String[] parts) {
        this.parts = parts;
        Id.resetIds();
        this.x = new Id();
        this.y = new Id();
        this.z = new Id();
    }

    public Program parse() {
        Expression exp = parseProg();
        UnaryLambda ul = new UnaryLambda(x, exp);
        return new Program(ul, x);
    }

    private Expression parseProg() {
        if (pos == parts.length) return null;

        // constante 

        if (parts[pos].equals("0")) {
            pos++;
            return new Const(0);
        }
        if (parts[pos].equals("1")) {
            pos++;
            return new Const(1);
        }

        // ids

        if (parts[pos].equals("x")) {
            pos++;
            return x;
        }
        if (parts[pos].equals("y")) {
            pos++;
            return y;
        }
        if (parts[pos].equals("z")) {
            pos++;
            return z;
        }

        // unary operators

        if (parts[pos].equals("not")) {
            pos++;
            Expression operand = parseProg();
            return new Not(operand);
        }
        if (parts[pos].equals("shl1")) {
            pos++;
            Expression operand = parseProg();
            return new Shl1(operand);
        }
        if (parts[pos].equals("shr1")) {
            pos++;
            Expression operand = parseProg();
            return new Shr1(operand);
        }
        if (parts[pos].equals("shr4")) {
            pos++;
            Expression operand = parseProg();
            return new Shr4(operand);
        }
        if (parts[pos].equals("shr16")) {
            pos++;
            Expression operand = parseProg();
            return new Shr16(operand);
        }

        // binary operators 

        if (parts[pos].equals("and")) {
            pos++;
            Expression op1 = parseProg();
            Expression op2 = parseProg();
            return new And(op1, op2);
        }
        if (parts[pos].equals("plus")) {
            pos++;
            Expression op1 = parseProg();
            Expression op2 = parseProg();
            return new Plus(op1, op2);
        }
        if (parts[pos].equals("or")) {
            pos++;
            Expression op1 = parseProg();
            Expression op2 = parseProg();
            return new Or(op1, op2);
        }
        if (parts[pos].equals("xor")) {
            pos++;
            Expression op1 = parseProg();
            Expression op2 = parseProg();
            return new Xor(op1, op2);
        }

        // N operators

        if (parts[pos].equals("tfold")) {
            pos++;
            Expression op1 = parseProg();
            Expression op2 = parseProg();
            BinaryLambda bl = new BinaryLambda(y, z, op2);
            Fold f = new Fold(op1, new Const(0), bl);
            return f;
        }

        // T operators

        if (parts[pos].equals("if0")) {
            pos++;
            Expression op1 = parseProg();
            Expression op2 = parseProg();
            Expression op3 = parseProg();
            return new If0(op1, op2, op3);
        }

        if (parts[pos].equals("fold")) {
            pos++;
            Expression op1 = parseProg();
            Expression op2 = parseProg();
            Expression op3 = parseProg();
            BinaryLambda b = new BinaryLambda(y, z, op3);
            return new Fold(op1, op2, b);
        }
        return null;
    }

    // for reference only
    private List<Program> buildPrograms2(List<String> templates) {

        List<Program> rez = new ArrayList<Program>();
        for (String line : templates) {
            String[] tokens = null;//split(getTemplate());
            SOp[] sops = new SOp[tokens.length];
            for (int i = 0; i < sops.length; i++) {
                sops[i] = SOp.valueOf(tokens[i]);
            }
            int[] parents = new int[tokens.length];
//            dfs(-1, 0, sops, parents);

            String[] parts = null;//split(line);

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


}
