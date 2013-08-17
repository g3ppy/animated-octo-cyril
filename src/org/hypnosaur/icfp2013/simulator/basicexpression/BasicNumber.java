package org.hypnosaur.icfp2013.simulator.basicexpression;

import com.google.common.primitives.UnsignedLong;
import com.google.common.primitives.UnsignedLongs;

import java.nio.ByteBuffer;
import java.util.Objects;

public class BasicNumber {

    public static final BasicNumber ZERO = new BasicNumber(0);
    public static final BasicNumber ONE = new BasicNumber(1);
    private final UnsignedLong internalNumber;

    public BasicNumber(long number) {
        this.internalNumber = UnsignedLong.fromLongBits(number);
    }

    public BasicNumber(String s) {
        this.internalNumber = UnsignedLong.fromLongBits(UnsignedLongs.decode(s));
    }

    public BasicNumber getByte(int idx) {
        byte[] byteArray = ByteBuffer.allocate(8).putLong(toLong()).array();
        return new BasicNumber(0xFFL & byteArray[7 - idx]);
    }

    private long toLong() {
        return internalNumber.longValue();
    }

    public BasicNumber not() {
        return new BasicNumber(~toLong());
    }

    public BasicNumber shl1() {
        return new BasicNumber(toLong() << 1);
    }

    public BasicNumber shr1() {
        return new BasicNumber(toLong() >>> 1);
    }

    public BasicNumber shr4() {
        return new BasicNumber(toLong() >>> 4);
    }

    public BasicNumber shr16() {
        return new BasicNumber(toLong() >>> 16);
    }

    public BasicNumber and(BasicNumber other) {
        return new BasicNumber(toLong() & other.toLong());
    }

    public BasicNumber or(BasicNumber other) {
        return new BasicNumber(toLong() | other.toLong());
    }

    public BasicNumber xor(BasicNumber other) {
        return new BasicNumber(toLong() ^ other.toLong());
    }

    public BasicNumber plus(BasicNumber other) {
        return new BasicNumber(toLong() + other.toLong());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicNumber basicNumber = (BasicNumber) o;

        return basicNumber.toLong() == this.toLong();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(internalNumber);
    }

    @Override
    public String toString() {
        String internalToString = internalNumber.toString(16);
        String prefix = "0x";
        String number = "0000000000000000".substring(internalToString.length()) + internalToString;
        return prefix + number.toUpperCase();
    }

//    public String toBinaryString() {
//        String zeros = "00000000";
//        String rez = "";
//
//        for (byte b : bytes) {
//            String v = String.format("%s ", Integer.toBinaryString(b));
//            if (v.length() > 8) {
//                v = v.substring(v.length() - 9, v.length());
//            }
//            if (v.length() < 8) {
//                v = zeros.substring(0, 8 - v.length() + 1) + v;
//            }
//            rez += v;
//        }
//
//        return rez;
//    }

}

