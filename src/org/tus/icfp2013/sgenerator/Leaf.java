package org.tus.icfp2013.sgenerator;

/**
 * Can be one of: Id, Const
 * <p/>
 * Created by kong
 * Time: 8/10/13 2:39 AM
 */
public class Leaf implements Node {

    public static final Leaf L = new Leaf();

    private Leaf() {
    }

    @Override
    public int getEmpty() {
        return 0;  //TODO: implement me
    }

    @Override
    public void addNode(Node n, int idxEmpty) {
        throw new IllegalArgumentException("Cannot add to a leaf!");
    }

    @Override
    public Node clone() {
        return L;
    }

    @Override
    public void addLeaves() {
    }

    @Override
    public String toString() {
        return "L";
    }

}

