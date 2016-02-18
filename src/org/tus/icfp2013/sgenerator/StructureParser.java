package org.tus.icfp2013.sgenerator;

/**
 * Created by kong
 * Time: 8/11/13 7:27 AM
 */
public class StructureParser {

    private static class Pint {
        int idx;
    }

    public static String canonical(String s) {
        if (s.lastIndexOf('C') < 0)
            return s;
        return parseNode2(s, new Pint()).toString();
    }

    public static Node parseNode(String s, Pint p) {
        if (s == null)
            return null;
        consumeWhitespaces(s, p);
        if (s.charAt(p.idx) == 'L') {
            p.idx++;
            return Leaf.L;
        }
        if (s.charAt(p.idx) == 'S') {
            p.idx++;
            consumeWhitespaces(s, p);
            if (s.charAt(p.idx) != '(')
                throw new RuntimeException("Invalid expression: expecting '(' found '" + s.charAt(p.idx) + "'");
            else {
                p.idx++;
                Node child = parseNode(s, p);
                consumeWhitespaces(s, p);
                if (s.charAt(p.idx) == ')') {
                    p.idx++;
                    return new SingleChildNode(child);
                } else
                    throw new RuntimeException("Invalid expression: expecting ')' found '" + s.charAt(p.idx) + "'");
            }
        }

        if (s.charAt(p.idx) == 'C' || s.charAt(p.idx) == 'N') {
            char type = s.charAt(p.idx);
            p.idx++;
            consumeWhitespaces(s, p);
            if (s.charAt(p.idx) != '(')
                throw new RuntimeException("Invalid expression: expecting '(' found '" + s.charAt(p.idx) + "'");
            else {
                p.idx++;
                Node n1 = parseNode(s, p);
                consumeWhitespaces(s, p);
                Node n2 = parseNode(s, p);
                consumeWhitespaces(s, p);
                if (s.charAt(p.idx) == ')') {
                    p.idx++;
                } else
                    throw new RuntimeException("Invalid expression: expecting ')' found '" + s.charAt(p.idx) + "'");

                return new TwoChildrenNode(n1, n2, (type == 'C' ? "C" : (type == 'N' ? "N" : "")));
            }
        }

        if (s.charAt(p.idx) == 'T') {
            p.idx++;
            consumeWhitespaces(s, p);
            if (s.charAt(p.idx) != '(')
                throw new RuntimeException("Invalid expression: expecting '(' found '" + s.charAt(p.idx) + "'");
            else {
                p.idx++;
                Node n1 = parseNode(s, p);
                consumeWhitespaces(s, p);
                Node n2 = parseNode(s, p);
                consumeWhitespaces(s, p);
                Node n3 = parseNode(s, p);
                consumeWhitespaces(s, p);
                if (s.charAt(p.idx) == ')') {
                    p.idx++;
                } else
                    throw new RuntimeException("Invalid expression: expecting ')' found '" + s.charAt(p.idx) + "'");

                return new ThreeChildrenNode(n1, n2, n3);
            }
        }

        throw new RuntimeException("Parse exception: " + s);
    }

    public static Node parseNode2(String s, Pint p) {
        if (s == null)
            return null;
        if (p.idx >= s.length())
            System.out.println();
        consumeWhitespaces(s, p);
        if (s.charAt(p.idx) == 'L') {
            p.idx++;
            return Leaf.L;
        }
        if (s.charAt(p.idx) == 'S') {
            p.idx++;
            consumeWhitespaces(s, p);
            Node child = parseNode2(s, p);
            consumeWhitespaces(s, p);

            return new SingleChildNode(child);
        }

        if (s.charAt(p.idx) == 'C' || s.charAt(p.idx) == 'N') {
            char type = s.charAt(p.idx);
            p.idx++;
            consumeWhitespaces(s, p);
            Node n1 = parseNode2(s, p);
            consumeWhitespaces(s, p);
            Node n2 = parseNode2(s, p);
            consumeWhitespaces(s, p);

            return new TwoChildrenNode(n1, n2, (type == 'C' ? "C" : (type == 'N' ? "N" : "")));
        }

        if (s.charAt(p.idx) == 'T') {
            p.idx++;
            consumeWhitespaces(s, p);
            Node n1 = parseNode2(s, p);
            consumeWhitespaces(s, p);
            Node n2 = parseNode2(s, p);
            consumeWhitespaces(s, p);
            Node n3 = parseNode2(s, p);
            consumeWhitespaces(s, p);

            return new ThreeChildrenNode(n1, n2, n3);
        }

        throw new RuntimeException("Parse exception: " + s);
    }


    private static void consumeWhitespaces(String s, Pint p) {
        while (p.idx < s.length() - 1 && s.charAt(p.idx) == ' ')
            p.idx++;
    }

    public static void main(String[] args) {
        String s = "L";
        Node rez = parseNode(s, new Pint());
        System.out.println(rez);

        s = "S(S(L))";
        rez = parseNode(s, new Pint());
        System.out.println(rez);

        s = "C(S(S(L))L)";
        rez = parseNode(s, new Pint());
        System.out.println(rez);

        s = "S(T(C(S(S(L)) L) L L))";
        rez = parseNode(s, new Pint());
        System.out.println(rez);

        s = "SSL";
        rez = parseNode2(s, new Pint());
        System.out.println(rez);

        s = "CSSLL";
        rez = parseNode2(s, new Pint());
        System.out.println(rez);

        s = "STCSSL L L L";
        rez = parseNode2(s, new Pint());
        System.out.println(rez);
    }

}

