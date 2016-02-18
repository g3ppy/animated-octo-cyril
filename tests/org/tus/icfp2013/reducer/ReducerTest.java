package org.tus.icfp2013.reducer;

import com.google.common.collect.Lists;
import org.tus.icfp2013.simulator.Program;
import org.tus.icfp2013.simulator.expressions.*;
import org.tus.icfp2013.simulator.operators.And;
import org.tus.icfp2013.simulator.operators.Not;
import org.tus.icfp2013.simulator.operators.Or;
import org.tus.icfp2013.simulator.operators.Plus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * User: vlad
 * Date: 8/11/13
 * Time: 2:58 PM
 */
public class ReducerTest {

    public static void main(String[] notUsed) {
        testProgramsAreSimilar();
        testProgramsAreCorrectlyRefactored();

        testAllWorksCorrectly();

    }

    @SuppressWarnings("unchecked")
    private static void testAllWorksCorrectly() {
        System.out.println("Test All Works Correctly");
        ArrayList<Program> programsWith0 = Lists.newArrayList();
        ArrayList<Program> programsWith1 = Lists.newArrayList();

        programsWith0.add(buildComplexProgram0());
        programsWith0.add(buildAnotherProgram());
        programsWith0.add(buildAnotherProgram());
        programsWith1.add(buildAnotherProgram());
        programsWith1.add(buildComplexNotSimilarProgram1());
        programsWith1.add(buildComplexSimilarProgram1());
        programsWith1.add(buildAnotherProgram());

        Reducer reducer = new Reducer(programsWith0, programsWith1);
        Iterator<Program> programIterator = reducer.plzGetIterator();

        while(programIterator.hasNext()){
            Program program = programIterator.next();

            System.out.println(program.getCode());
        }

        System.out.println();
        System.out.println();
        System.out.println();
    }

    private static void testProgramsAreCorrectlyRefactored() {
        System.out.println("Test Programs Are Correctly Refactored");
        ArrayList<Program> programsWith0 = Lists.newArrayList();
        ArrayList<Program> programsWith1 = Lists.newArrayList();

        Reducer reducer = new Reducer(programsWith0, programsWith1);
        boolean similar;

        Program complexProgram0 = buildComplexProgram0();
        Program complexSimilarProgram1 = buildComplexSimilarProgram1();
        Program complexNotSimilarProgram1 = buildComplexNotSimilarProgram1();

        System.out.println("Should be similar: ");
        similar = reducer.programsAreSimilar(complexProgram0, complexSimilarProgram1);
        System.out.println(similar);
        System.out.println(complexProgram0.getCode());
        System.out.println(complexSimilarProgram1.getCode());
        if (similar) {
            Program reducedProgram = reducer.substituteMatchingConstWithId(complexProgram0, complexSimilarProgram1);
            System.out.println(reducedProgram.getCode());
        }

        System.out.println("Should not be similar: ");
        similar = reducer.programsAreSimilar(complexProgram0, complexNotSimilarProgram1);
        System.out.println(similar);
        System.out.println(complexProgram0.getCode());
        System.out.println(complexNotSimilarProgram1.getCode());
        if (similar) {
            Program reducedProgram = reducer.substituteMatchingConstWithId(complexProgram0, complexNotSimilarProgram1);
            System.out.println(reducedProgram.getCode());
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    public static void testProgramsAreSimilar() {
        System.out.println("Test Program Are Similar");
        ArrayList<Program> programsWith0 = Lists.newArrayList();
        ArrayList<Program> programsWith1 = Lists.newArrayList();

        Reducer reducer = new Reducer(programsWith0, programsWith1);

        System.out.println("Should be similar: ");
        System.out.println(reducer.programsAreSimilar(buildComplexProgram0(), buildComplexSimilarProgram1()));

        System.out.println("Should not be similar: ");
        System.out.println(reducer.programsAreSimilar(buildComplexProgram0(), buildComplexNotSimilarProgram1()));

        System.out.println();
        System.out.println();
        System.out.println();
    }

    private static Program buildComplexProgram0() {
        Id.resetIds();
        Id x = new Id();
        Id y = new Id();
        Id z = new Id();

        UnaryLambda lambda = new UnaryLambda(x, new And(new Fold(new And(x, new Not(new Plus(x, new Const(0)))), new Const(1), new BinaryLambda(y, z, new Or(new Plus(y, new Const(0)), new Const(0)))), new And(new Const(1), new Const(0))));
        return new Program(lambda, x);
    }

    private static Program buildComplexSimilarProgram1() {
        Id.resetIds();
        Id x = new Id();
        Id y = new Id();
        Id z = new Id();

        UnaryLambda lambda = new UnaryLambda(x, new And(new Fold(new And(x, new Not(new Plus(x, new Const(1)))), new Const(1), new BinaryLambda(y, z, new Or(new Plus(y, new Const(0)), new Const(1)))), new And(new Const(1), new Const(1))));
        return new Program(lambda, x);
    }

    private static Program buildComplexNotSimilarProgram1() {
        Id.resetIds();
        Id x = new Id();
        Id y = new Id();
        Id z = new Id();

        UnaryLambda lambda = new UnaryLambda(x, new And(new Fold(new And(x, new Not(new Plus(x, new Const(1)))), new Const(0), new BinaryLambda(y, z, new Or(new Plus(y, new Const(0)), new Const(0)))), new And(new Const(1), new Const(0))));
        return new Program(lambda, x);
    }


    private static Program buildAnotherProgram() {
        Id.resetIds();
        Id x = new Id();
        Id y = new Id();
        Id z = new Id();

        UnaryLambda lambda = new UnaryLambda(x, new Fold(x, new Const(0), new BinaryLambda(y, z, new Plus(y, z))));
        return new Program(lambda, x);
    }

}
