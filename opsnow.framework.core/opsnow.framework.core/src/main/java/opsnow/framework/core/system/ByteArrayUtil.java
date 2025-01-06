package opsnow.framework.core.system;

import opsnow.framework.core.ONIConst;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
public class ByteArrayUtil {
    // AsCharArray

    public static char[] asCharArray(String source) {
        return source != null ? source.toCharArray() : new char[0];
    }

    public static char[] asCharArray(byte[] source) {
        if (source == null || source.length == 0) return new char[0];
        char[] buff = new char[source.length];
        for (int i = 0; i < source.length; i++) {
            buff[i] = (char) source[i];
        }
        return buff;
    }

    // AsByteArray

    public static byte[] asByteArray(String source) {
        if (source == null || source.isEmpty()) return ONIConst.EmptyByteArray;
        byte[] buff = new byte[source.length()];
        for (int i = 0; i < source.length(); i++) {
            buff[i] = (byte) source.charAt(i);
        }
        return buff;
    }

    public static byte[] asByteArray(char[] source) {
        if (source == null || source.length == 0) return ONIConst.EmptyByteArray;
        byte[] buff = new byte[source.length];
        for (int i = 0; i < source.length; i++) {
            buff[i] = (byte) source[i];
        }
        return buff;
    }

    // Byte <-> Char

    public static byte[] toByteArray(char[] source, Charset encoding) {
        if (source == null || source.length == 0) return ONIConst.EmptyByteArray;
        encoding = encoding != null ? encoding : StandardCharsets.UTF_8;
        return new String(source).getBytes(encoding);
    }

    public static char[] toCharArray(byte[] source, Charset encoding) {
        if (source == null || source.length == 0) return new char[0];
        encoding = encoding != null ? encoding : StandardCharsets.UTF_8;
        return new String(source, encoding).toCharArray();
    }

    // Byte <-> String

    public static byte[] toByteArray(String source, Charset encoding) {
        if (source == null || source.isEmpty()) return ONIConst.EmptyByteArray;
        encoding = encoding != null ? encoding : StandardCharsets.UTF_8;
        return source.getBytes(encoding);
    }

    public static String toString(byte[] source, Charset encoding) {
        if (source == null || source.length == 0) return "";
        encoding = encoding != null ? encoding : StandardCharsets.UTF_8;
        return new String(source, encoding);
    }

    public static List<String> toStringList(byte[][] multiDataList, Charset encoding) {
        List<String> results = new ArrayList<>();
        if (multiDataList != null) {
            encoding = encoding != null ? encoding : StandardCharsets.UTF_8;
            for (byte[] multiData : multiDataList) {
                results.add(new String(multiData, encoding));
            }
        }
        return results;
    }

    // Byte Utility

    public static byte[] extract(byte[] source, int offset, int count) {
        byte[] result = new byte[count];
        System.arraycopy(source, offset, result, 0, count);
        return result;
    }

    public static byte[] combine(byte[] buffer1, byte[] buffer2) {
        byte[] combinedBytes = new byte[buffer1.length + buffer2.length];
        System.arraycopy(buffer1, 0, combinedBytes, 0, buffer1.length);
        System.arraycopy(buffer2, 0, combinedBytes, buffer1.length, buffer2.length);
        return combinedBytes;
    }

    public static byte[] toUtf8Bytes(int intValue) {
        return asByteArray(String.valueOf(intValue));
    }

    public static byte[] toUtf8Bytes(long longValue) {
        return asByteArray(String.valueOf(longValue));
    }

    public static byte[] toUtf8Bytes(double value) {
        return asByteArray(String.format(java.util.Locale.US, "%.16g", value));
    }

    public static byte[] toBigEndianBytes(int source) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(source);
        return buffer.array();
    }

    public static int toLittleEndian(byte[] source) {
        if (source == null || (source.length != 2 && source.length != 4 && source.length != 8)) {
            throw new IllegalArgumentException("Unsupported Size");
        }
        ByteBuffer buffer = ByteBuffer.wrap(source);
        buffer.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        return buffer.getInt();
    }
}
