package org.tus.icfp2013.sgenerator;

/**
 * Represents a BinaryOperator (Commutative!!!) or a tfold
 * <p/>
 * Created by kong
 * Time: 8/10/13 2:44 AM
 */
public class TwoChildrenNode implements Node {

    private Node c1;
    private Node c2;
    private final String type;

    public TwoChildrenNode(Node c1, Node c2, String type) {
        this.c1 = c1;
        this.c2 = c2;
        this.type = type;
    }

    @Override
    public int getEmpty() {
        int rez = 0;
        if (c1 == null)
            rez++;
        else
            rez += c1.getEmpty();
        if (c2 == null)
            rez++;
        else
            rez += c2.getEmpty();

        return rez;
    }

    @Override
    public void addNode(Node n, int idxEmpty) {
        int where = idxEmpty;
        if (c1 == null) {
            if (where == 0) {
                c1 = n;
                return;
            } else
                where--;
        } else {
            if (where < c1.getEmpty()) {
                c1.addNode(n, where);
                return;
            } else
                where -= c1.getEmpty();
        }

        if (c2 == null) {
            if (where == 0) {
                c2 = n;
                return;
            }
        } else {
            c2.addNode(n, where);
            return;
        }

        throw new IllegalArgumentException("Invalid idx to insert node: " + idxEmpty);
    }

    @Override
    public Node clone() {
        return new TwoChildrenNode(
                c1 != null ? c1.clone() : null,
                c2 != null ? c2.clone() : null,
                type);
    }

    @Override
    public void addLeaves() {
        if (c1 == null)
            c1 = Leaf.L;
        else
            c1.addLeaves();

        if (c2 == null)
            c2 = Leaf.L;
        else
            c2.addLeaves();
    }

    @Override
    public String toString() {
        if ("C".equals(type)) {
            String c1s = "" + c1;
            String c2s = "" + c2;
            if (c1s.compareTo(c2s) > 0) {
                String tmp = c1s;
                c1s = c2s;
                c2s = tmp;
            }
//            return "C(" + c1s + " " + c2s + ")";
            return "C" + c1s + c2s;
        } else
//            return type + "(" + c1 + " " + c2 + ")";
            return type + c1 + c2;
    }

}

