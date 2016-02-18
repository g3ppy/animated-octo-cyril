package org.tus.icfp2013.sgenerator;

/**
 * Created by kong
 * Time: 8/11/13 1:20 AM
 */
public class NodeBuilder {

    public static Node build(String type) {
        if ("C".equals(type))
            return new TwoChildrenNode(null, null, type);
        if ("N".equals(type))
            return new TwoChildrenNode(null, null, type);
        if ("S".equals(type))
            return new SingleChildNode(null);
        if ("T".equals(type))
            return new ThreeChildrenNode(null, null, null);

        throw new IllegalArgumentException("Unknown type: " + type);
    }

}
