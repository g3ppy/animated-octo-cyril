package org.tus.icfp2013.sgenerator;

import java.util.*;

/**
 * Created by kong
 * Time: 8/10/13 2:12 AM
 */
public class StructureGenerator {

    private final int size;
    private final Set<String> expressionTypes;

    private boolean debug = false;

    private static class Ingredients {
        private final int[] ing;
        private int total = 0;

        private Ingredients(int[] ing) {
            this.ing = ing.clone();
            total = ing[0] + ing[1] + ing[2] + ing[3];
        }

        private Ingredients(int c, int n, int s, int t) {
            ing = new int[4];
            ing[0] = c;
            ing[1] = n;
            ing[2] = s;
            ing[3] = t;
            total = c + n + s + t;
        }

        public List<String> availableTypes() {
            List<String> rez = new ArrayList<String>(4);
            if (ing[0] > 0)
                rez.add("C");
            if (ing[1] > 0)
                rez.add("N");
            if (ing[2] > 0)
                rez.add("S");
            if (ing[3] > 0)
                rez.add("T");

            return rez;
        }

        public void dec(String type) {
            if (type.equals("C")) {
                ing[0]--;
                total--;
                return;
            }
            if (type.equals("N")) {
                ing[1]--;
                total--;
                return;
            }
            if (type.equals("S")) {
                ing[2]--;
                total--;
                return;
            }
            if (type.equals("T")) {
                ing[3]--;
                total--;
                return;
            }
            throw new IllegalArgumentException("invalid type requested");
        }

        public Ingredients clone() {
            return new Ingredients(this.ing);
        }

        public int delta(String type) {
            if (type.equals("S"))
                return 0;
            if (type.equals("C") || type.equals("N"))
                return 1;
            if (type.equals("T"))
                return 2;
            throw new IllegalArgumentException("Invalid type");
        }
    }


    public StructureGenerator(int size, Set<String> expressionTypes) {
        this.size = size;
        this.expressionTypes = expressionTypes;
    }

    private static final Map<String, String> types = new HashMap<String, String>();

    static {
        // unary
        types.put("not", "S");
        types.put("shl1", "S");
        types.put("shr1", "S");
        types.put("shr4", "S");
        types.put("shr16", "S");

        // binary
        types.put("and", "C");
        types.put("or", "C");
        types.put("xor", "C");
        types.put("plus", "C");

        // expression
        types.put("if0", "T");
        types.put("tfold", "N");
        types.put("fold", "T");
    }

    public Set<String> generate() {
        if (debug)
            System.out.println("Problem size is: " + size);
        // compute target structure size
        int modelSize = size - 1; // program lambda
        if (expressionTypes.contains("fold"))
            modelSize -= 1;
        if (expressionTypes.contains("tfold"))
            modelSize -= 2;
        if (debug)
            System.out.println("Target size is: " + modelSize);

        // compute min max quantities for ingredients
        if (debug)
            System.out.println("Ingredients: " + expressionTypes);
        Map<String, Integer> minQuantities = new HashMap<String, Integer>();
        Map<String, Integer> maxQuantities = new HashMap<String, Integer>();
        for (String s : expressionTypes) {
            String type = types.get(s);
            int min = 0, max = Integer.MAX_VALUE;
            if (minQuantities.get(type) != null)
                min = minQuantities.get(type);
            if (maxQuantities.get(type) != null)
                max = maxQuantities.get(type);

            min++;

            if (type.equals("N")) {
                min = max = 1;
            }
            minQuantities.put(type, min);
            maxQuantities.put(type, max);
        }

        if (debug) {
            System.out.println("mins: " + minQuantities);
            System.out.println("maxs: " + maxQuantities);
        }
        int minSize = getSize(minQuantities);
        if (debug)
            System.out.println("min size is " + minSize + " target is " + modelSize);

        for (String type : minQuantities.keySet()) {
            if (maxQuantities.get(type) != Integer.MAX_VALUE)
                continue;
            Map<String, Integer> currentSizes = new HashMap<String, Integer>(minQuantities);
            while (getSize(currentSizes) <= modelSize)
                currentSizes.put(type, currentSizes.get(type) + 1);
            currentSizes.put(type, currentSizes.get(type) - 1);
            maxQuantities.put(type, currentSizes.get(type));
        }
        if (debug)
            System.out.println("maxs: " + maxQuantities);

        List<Map<String, Integer>> validQuantities = findRecipes(minQuantities, maxQuantities, modelSize);
        if (debug)
            System.out.println("Found valid recipes: " + validQuantities);
        Set<String> rez = new HashSet<String>();
        for (Map<String, Integer> receipe : validQuantities) {
//            Set<String> sol = generateStructure(receipe);
            Set<String> sol = generateStructure2(receipe);
            rez.addAll(sol);
        }

        System.out.println("Structure generator: found " + rez.size() + " structures in total");
        return rez;
    }

    private Set<String> generateStructure2(Map<String, Integer> receipe) {
        if (debug)
            System.out.println("Solving recipe: " + receipe + " (" + getLeaves(receipe) + " leaves)");
        Ingredients ing = new Ingredients(
                receipe.get("C") != null ? receipe.get("C") : 0,
                receipe.get("N") != null ? receipe.get("N") : 0,
                receipe.get("S") != null ? receipe.get("S") : 0,
                receipe.get("T") != null ? receipe.get("T") : 0
        );
        Set<String> rez = new HashSet<String>();
        generateStructure2(1, "", ing, rez);
//        System.out.println("Found "+ rez.size() + " structures: " + rez);
        return rez;
    }

    private void generateStructure2(int toClose, String s, Ingredients ing, Set<String> rez) {
        if (toClose == 0 && ing.total == 0) {
            rez.add(StructureParser.canonical(s));
            return;
        }
        if (ing.total == 0) {
            for (int i = 0; i < toClose; i++) {
                s += "L";
            }
            rez.add(StructureParser.canonical(s));
            return;
        }

        if (s.length() > 0 && toClose <= 0)
            return;

        for (String type : ing.availableTypes()) {
            int delta = ing.delta(type);
            Ingredients newIng = ing.clone();
            newIng.dec(type);
            generateStructure2(toClose + delta, s + type, newIng, rez);
        }

        if (toClose > 0)
            generateStructure2(toClose - 1, s + "L", ing, rez);
    }

    private Set<String> generateStructure(Map<String, Integer> receipe) {
        if (debug)
            System.out.println("Solving recipe: " + receipe + " (" + getLeaves(receipe) + " leaves)");
        Ingredients ing = new Ingredients(
                receipe.get("C") != null ? receipe.get("C") : 0,
                receipe.get("N") != null ? receipe.get("N") : 0,
                receipe.get("S") != null ? receipe.get("S") : 0,
                receipe.get("T") != null ? receipe.get("T") : 0
        );
        Set<String> rez = generateStructure(null, ing);
//        System.out.println("Found "+ rez.size() + " structures: " + rez);
        return rez;
    }

    private Set<String> generateStructure(Node root, Ingredients ing) {
        Set<String> rez = new HashSet<String>();
        if (ing.total == 0) {
            root.addLeaves();
            rez.add(root.toString());
            return rez;
        }
        if (root == null) {
            for (String type : ing.availableTypes()) {
                Ingredients newIng = ing.clone();
                newIng.dec(type);
                rez.addAll(generateStructure(NodeBuilder.build(type), newIng));
            }
        } else {
            for (String type : ing.availableTypes()) {
                Ingredients newIng = ing.clone();
                newIng.dec(type);
                Node toAdd = NodeBuilder.build(type);
                for (int i = 0; i < root.getEmpty(); i++) {
                    Node newRoot = root.clone();
                    newRoot.addNode(toAdd, i);
                    rez.addAll(generateStructure(newRoot, newIng));
                }
            }
        }

        return rez;
    }

    public List<Map<String, Integer>> findRecipes(Map<String, Integer> minQuantities, Map<String, Integer> maxQuantities, int targetSize) {
        List<Map<String, Integer>> rez = new ArrayList<Map<String, Integer>>();

        boolean hasS = minQuantities.get("S") != null;
        int sMin = hasS ? minQuantities.get("S") : 0, sMax = hasS ? maxQuantities.get("S") : 0;

        boolean hasC = minQuantities.get("C") != null;
        int cMin = hasC ? minQuantities.get("C") : 0, cMax = hasC ? maxQuantities.get("C") : 0;

        boolean hasN = minQuantities.get("N") != null;
        int nMin = hasN ? minQuantities.get("N") : 0, nMax = hasN ? maxQuantities.get("N") : 0;

        boolean hasT = minQuantities.get("T") != null;
        int tMin = hasT ? minQuantities.get("T") : 0, tMax = hasT ? maxQuantities.get("T") : 0;

        for (int s = sMin; s <= sMax; s++)
            for (int c = cMin; c <= cMax; c++)
                for (int n = nMin; n <= nMax; n++)
                    for (int t = tMin; t <= tMax; t++) {
                        Map<String, Integer> currentQuantities = new HashMap<String, Integer>();
                        if (hasS)
                            currentQuantities.put("S", s);
                        if (hasC)
                            currentQuantities.put("C", c);
                        if (hasN)
                            currentQuantities.put("N", n);
                        if (hasT)
                            currentQuantities.put("T", t);
                        if (getSize(currentQuantities) == targetSize)
                            rez.add(currentQuantities);
                    }

        return rez;
    }

    public int getLeaves(Map<String, Integer> blocks) {
        int in = 0, out = 0;
        for (String type : blocks.keySet()) {
            in += blocks.get(type);
            int multiplier = 1;
            if (type.equals("C") || type.equals("N"))
                multiplier = 2;
            if (type.equals("T"))
                multiplier = 3;
            out += multiplier * blocks.get(type);
        }

        return out - in + 1;
    }

    public int getSize(Map<String, Integer> blocks) {
        int in = 0, out = 0;
        for (String type : blocks.keySet()) {
            in += blocks.get(type);
            int multiplier = 1;
            if (type.equals("C") || type.equals("N"))
                multiplier = 2;
            if (type.equals("T"))
                multiplier = 3;
            out += multiplier * blocks.get(type);
        }

        return (out - in + 1) + in; // leaves + nodes
    }

}

