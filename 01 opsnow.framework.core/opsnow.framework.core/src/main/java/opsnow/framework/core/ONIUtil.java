package opsnow.framework.core;

import opsnow.framework.core.pooling.StringBuilderPool;
import java.util.Arrays;
import java.util.UUID;
import java.util.Iterator;
import java.util.function.Function;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
public class ONIUtil {
    private static final char[] s_conv32map = {
            '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W'
    };

    private static final char[] s_conv64map = {
            '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '!',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '_', '.'
    };


    private static final LocalDateTime s_keyTimeBegin = LocalDateTime.of(2001, 1, 1, 0, 0);
    private static byte s_ipByte = getLastIpAddressByte();

    private static final int MAX_TICK_TABLE_SIZE = 1000;
    private static long[] s_ticks32Tbl = new long[MAX_TICK_TABLE_SIZE];

    private static AtomicInteger s_uuid32 = new AtomicInteger(0);


    /**
     * Creates an array from given arguments.
     *
     * @param arg1 the first argument to include in the array.
     * @param args additional arguments to include in the array.
     * @return an array containing all provided arguments.
     */
    public static Object[] makeArrayObject(Object arg1, Object... args) {
        Object[] array = new Object[args.length + 1];
        array[0] = arg1;
        System.arraycopy(args, 0, array, 1, args.length);
        return array;
    }

    /**
     * Creates an array of a specific type from given arguments.
     *
     * @param <T>  the type of the elements in the array.
     * @param arg1 the first element of the array.
     * @param args additional elements of the array.
     * @return an array containing all provided elements.
     */
    public static <T> T[] makeArray(T arg1, T... args) {
        T[] array = Arrays.copyOf(args, args.length + 1);
        array[0] = arg1;
        System.arraycopy(args, 0, array, 1, args.length);
        return array;
    }

    /**
     * Generates a unique 32-digit key string that can be used as a filename or key.
     *
     * @param format The format to use for the UUID (ignored in Java version, only "toString" is used).
     * @return A unique key string.
     */
    public static String makeGuid(String format) {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Concatenates the elements of a specified iterable into a single string.
     *
     * @param datas the iterable of data elements to concatenate
     * @param valueSelector a function that converts elements to strings
     * @param <T> the type of elements in the iterable
     * @return a single string containing all elements concatenated
     */
    public static <T> String buildString(Iterable<T> datas, Function<T, String> valueSelector) {
        if (datas == null) {
            return "";
        }

        Iterator<T> iterator = datas.iterator();
        StringBuilder sb = StringBuilderPool.take();

        while (iterator.hasNext()) {
            sb.append(valueSelector.apply(iterator.next()));
        }

        return StringBuilderPool.toString(sb);
    }

    /**
     * Concatenates elements of a specified array into a single string.
     *
     * @param separator The separator to use between elements.
     * @param datas      The data elements to join.
     * @return A string resulting from joining the elements.
     */
    public static String joinString(String separator, Object... datas) {
        return joinString(separator, java.util.Arrays.asList(datas), Object::toString);
    }

    public static String joinString(String separator, Iterable<?> datas) {
        return joinString(separator, datas, Object::toString);
    }

    public static String joinString(String separator, Iterable<?> datas, Function<Object, String> valueSelector) {
        if (datas == null) {
            return "";
        }

        Iterator<?> iterator = datas.iterator();
        if (!iterator.hasNext()) {
            return "";
        }

        StringBuilder sb = StringBuilderPool.take();
        sb.append(valueSelector.apply(iterator.next()));

        while (iterator.hasNext()) {
            sb.append(separator);
            sb.append(valueSelector.apply(iterator.next()));
        }

        return StringBuilderPool.toString(sb);
    }

    /**
     * Formats a string safely with the given arguments.
     *
     * @param format The format string.
     * @param args   The arguments to format the string with.
     * @return A formatted string.
     */
    public static String formatString(String format, Object... args) {
        if (format == null || args == null || args.length == 0) {
            return format != null ? format : "";
        }
        return String.format(format, args);
    }

    /**
     * Generates a random string based on a pattern.
     *
     * @param pattern The pattern to use.
     * @return A randomly generated string based on the pattern.
     * /// Pattern to use:
     * /// # = Number
     * /// @ = Alpha character
     * /// * = Number or Alpha character
     * /// var result = ONIUtil.RandomString("@123###@");
     * /// //=> "B123936x"
     */
    public static String randomString(String pattern) {
        StringBuilder sb = StringBuilderPool.take();
        for (char ch : pattern.toCharArray()) {
            if (ch == '#') {
                sb.append((char) ('0' + (int) (Math.random() * 10)));
            } else if (ch == '@') {
                sb.append((char) ((int) (Math.random() * 26) + (Math.random() > 0.5 ? 'A' : 'a')));
            } else if (ch == '*') {
                if (Math.random() > 0.5) {
                    sb.append((char) ('0' + (int) (Math.random() * 10)));
                } else {
                    sb.append((char) ((int) (Math.random() * 26) + (Math.random() > 0.5 ? 'A' : 'a')));
                }
            } else {
                sb.append(ch);
            }
        }
        return StringBuilderPool.toString(sb);
    }

    /**
     * Converts a hexadecimal character to an integer.
     *
     * @param h The hexadecimal character.
     * @return The integer value of the hexadecimal character, or -1 if not valid.
     */
    public static int hexToInt(char h) {
        if (h >= '0' && h <= '9') return h - '0';
        if (h >= 'a' && h <= 'f') return h - 'a' + 10;
        if (h >= 'A' && h <= 'F') return h - 'A' + 10;
        return -1;
    }

    /**
     * Converts two hexadecimal characters into a byte.
     *
     * @param h1 The first hex character.
     * @param h2 The second hex character.
     * @return The byte corresponding to the hex characters.
     */
    public static byte hexToByte(char h1, char h2) {
        int high = hexToInt(h1);
        int low = hexToInt(h2);
        if (high >= 0 && low >= 0) {
            return (byte) ((high << 4) | low);
        }
        return 0;
    }

    /**
     * Converts an integer to a hexadecimal character.
     *
     * @param n The integer to convert (should be between 0 and 15).
     * @return The hexadecimal character.
     */
    public static char intToHex(int n) {
        if (n <= 9) {
            return (char) (n + '0');
        }
        return (char) (n - 10 + 'a');
    }

    public static String makeKey32() {
        StringBuilder result = new StringBuilder();
        long elapsedTicks = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli() - s_keyTimeBegin.toInstant(ZoneOffset.UTC).toEpochMilli();
        int uidval = s_uuid32.getAndIncrement() % MAX_TICK_TABLE_SIZE;

        if (s_ticks32Tbl[uidval] == elapsedTicks) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            elapsedTicks++;
        }

        s_ticks32Tbl[uidval] = elapsedTicks;

        for (int i = 10; i >= 0; i--) {
            int val = (int) ((elapsedTicks >> i * 5) & 0x1F);
            result.append(s_conv32map[val]);
        }

        String saltString = String.format("%d%03d", s_ipByte, uidval);
        int saltValues = Integer.parseInt(saltString) & 0x3FFFF;

        for (int i = 3; i >= 0; i--) {
            int val = (int) ((saltValues >> i * 5) & 0x1F);
            result.append(s_conv32map[val]);
        }

        return result.toString();
    }

    private static byte getLastIpAddressByte() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            byte[] ipBytes = ip.getAddress();
            return ipBytes[ipBytes.length - 1];
        } catch (UnknownHostException e) {
            throw new RuntimeException("Host address not retrievable", e);
        }
    }
}
