package org.tus.icfp2013.sgenerator;

/**
 * Can be: if0 or fold
 * <p/>
 * Created by kong
 * Time: 8/10/13 2:45 AM
 */
public class ThreeChildrenNode implements Node {

    private Node c1;
    private Node c2;
    private Node c3;

    public ThreeChildrenNode(Node c1, Node c2, Node c3) {
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
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
        if (c3 == null)
            rez++;
        else
            rez += c3.getEmpty();

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
            } else
                where--;
        } else {
            if (where < c2.getEmpty()) {
                c2.addNode(n, where);
                return;
            } else
                where -= c2.getEmpty();
        }
        if (c3 == null) {
            if (where == 0) {
                c3 = n;
                return;
            }
        } else {
            c3.addNode(n, where);
            return;
        }
        throw new IllegalArgumentException("Invalid idx to insert node: " + idxEmpty);
    }

    @Override
    public Node clone() {
        return new ThreeChildrenNode(
                c1 != null ? c1.clone() : null,
                c2 != null ? c2.clone() : null,
                c3 != null ? c3.clone() : null);
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

        if (c3 == null)
            c3 = Leaf.L;
        else
            c3.addLeaves();
    }

    @Override
    public String toString() {
//        return "T(" + c1 + " " + c2 + " " + c3 + ")";
        return "T" + c1 + c2 + c3;
    }

}

