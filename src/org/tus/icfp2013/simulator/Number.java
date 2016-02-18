package org.tus.icfp2013.simulator;

import java.util.Arrays;

/**
 * Created by kong
 * Time: 8/9/13 11:13 AM
 */
public class Number {

    public static final Number ZERO = new Number(new byte[]{0, 0, 0, 0, 0, 0, 0, 0});
    public static final Number ONE = new Number(new byte[]{0, 0, 0, 0, 0, 0, 0, 1});

    private final byte[] bytes;

    public Number(byte[] bytes) {
        this.bytes = bytes;
    }

    public Number(String s) {
        if (s.length() != 18) {
            throw new NumberFormatException("Invalid number specified: " + s);
        }
        bytes = new byte[8];
        for (int i = 0; i < 8; i++) {
            String part = s.substring(2 + 2 * i, 2 + 2 * i + 2);
            bytes[i] = (byte) (Integer.parseInt(part, 16) & 0xff);
        }
    }

    public Number getByte(int idx) {
        return new Number(new byte[]{0, 0, 0, 0, 0, 0, 0, bytes[7 - idx]});
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public static Number parseLong(long l) {
        byte[] bytes = new byte[8];
        for (int f = 7; f >= 0; f--) {
            bytes[f] = (byte) (l & 0xffL);
            l >>>= 8;
        }
        return new Number(bytes);
    }

    private long toLong() {
        long l = bytes[0];
        for (int f = 1; f < 8; f++)
            l = (l << 8) | (bytes[f] & 0xFF);
        return l;
    }

    public Number not() {
        byte[] ret = new byte[8];
        System.arraycopy(bytes, 0, ret, 0, 8);
        for (int f = 0; f < 8; f++) {
            ret[f] = (byte) ~ret[f];
        }
        return new Number(ret);
    }

    public Number shl1() {
        return parseLong(toLong() << 1);
    }

    public Number shr1() {
        return parseLong(toLong() >>> 1);
    }

    public Number shr4() {
        return parseLong(toLong() >>> 4);
    }

    public Number shr16() {
        return parseLong(toLong() >>> 16);
    }

    public Number and(Number other) {
        return parseLong(toLong() & other.toLong());
    }

    public Number or(Number other) {
        return parseLong(toLong() | other.toLong());
    }

    public Number xor(Number other) {
        return parseLong(toLong() ^ other.toLong());
    }

    public Number plus(Number other) {
        return parseLong(toLong() + other.toLong());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Number number = (Number) o;

        return Arrays.equals(bytes, number.bytes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }

    @Override
    public String toString() {
        String rez = "0x";
        for (byte b : bytes) {
            rez += String.format("%02X", b);
        }
        return rez;
    }

    public String toBinaryString() {
        String zeros = "00000000";
        String rez = "";

        for (byte b : bytes) {
            String v = String.format("%s ", Integer.toBinaryString(b));
            if (v.length() > 8) {
                v = v.substring(v.length() - 9, v.length());
            }
            if (v.length() < 8) {
                v = zeros.substring(0, 8 - v.length() + 1) + v;
            }
            rez += v;
        }

        return rez;
    }

}

