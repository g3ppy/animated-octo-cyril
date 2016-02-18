package org.tus.icfp2013.sgenerator;

/**
 * Represents an UnaryOperator
 * <p/>
 * Created by kong
 * Time: 8/10/13 2:42 AM
 */
public class SingleChildNode implements Node {

    private Node child;

    public SingleChildNode(Node child) {
        this.child = child;
    }

    @Override
    public int getEmpty() {
        if (child == null)
            return 1;
        else
            return child.getEmpty();
    }

    @Override
    public void addNode(Node n, int idxEmpty) {
        if (child == null) {
            if (idxEmpty == 0)
                child = n;
            else
                throw new IllegalArgumentException("Invalid idx: " + idxEmpty);
        } else
            child.addNode(n, idxEmpty);
    }

    @Override
    public Node clone() {
        return new SingleChildNode(child != null ? child.clone() : null);
    }

    @Override
    public void addLeaves() {
        if (child == null)
            child = Leaf.L;
        else
            child.addLeaves();
    }

    @Override
    public String toString() {
//        return "S(" + child + ")";
        return "S" + child;
    }

}

