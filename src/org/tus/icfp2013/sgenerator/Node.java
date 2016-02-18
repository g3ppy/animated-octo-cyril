package org.tus.icfp2013.sgenerator;

/**
 * Created by kong
 * Time: 8/10/13 2:39 AM
 */
public interface Node {

    public int getEmpty();

    public void addNode(Node n, int idxEmpty);

    public Node clone();

    public String toString();

    public void addLeaves();

}

