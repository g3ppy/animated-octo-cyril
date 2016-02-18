package org.tus.icfp2013.simulator;

/**
 * Created by kong
 * Time: 8/9/13 1:20 PM
 */
public class NumberTest {

    public static void main(String[] args) {
        Number nr = new Number("0x1122334455667788");
        System.out.println(nr.toString());
        nr = new Number("0xff22334455667788");
        System.out.println(nr.toString());
        nr = new Number("0x01fe01fe01fe013f");
        System.out.println(nr.not().toString());

        nr = Number.parseLong(11);
        Number nr2 = Number.ONE.plus(Number.ONE);
        System.out.println(nr2);
        System.out.println(nr.xor(nr2));
        nr = Number.parseLong(-1);
        System.out.println(nr);
        System.out.println(nr.plus(nr));

        nr = new Number("0x0000000000000000");
        nr = nr.not();
        System.out.println(nr.toString());
        nr = nr.not();
        System.out.println(nr.toString());
        System.out.println(nr.xor(nr.not()));
        nr = nr.not();
        System.out.println(nr.xor(nr.not()));
        System.out.println(nr.xor(nr));
    }

}

