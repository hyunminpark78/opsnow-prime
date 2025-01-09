package opsnow.framework.core.system;

import java.math.BigDecimal;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
public class NumberUtil {
    // Prime Checking
    public static boolean isPrime(int number) {
        if (number <= 1) return false;
        if (number == 2) return true;
        if (number % 2 == 0) return false;

        int sqrt = (int) Math.sqrt(number);
        for (int i = 3; i <= sqrt; i += 2) {
            if (number % i == 0) return false;
        }
        return true;
    }

    // Decimal Validation
    public static boolean isDecimal(BigDecimal value, int maxPrecision, int maxScale, boolean ignoreTrailingZeros) {
        if (value == null) return false;
        value = ignoreTrailingZeros ? value.stripTrailingZeros() : value;
        int precision = value.precision();
        int scale = value.scale();
        return precision <= maxPrecision && scale <= maxScale;
    }

    // Range Checking
    public static <T extends Comparable<T>> boolean isInRange(T value, T min, T max) {
        return value.compareTo(min) >= 0 && value.compareTo(max) <= 0;
    }

    // Format File Size
    public static String formatSize(BigDecimal size, String format, boolean lowerCase) {
        BigDecimal kb = BigDecimal.valueOf(1024);
        BigDecimal mb = kb.multiply(kb);
        BigDecimal gb = mb.multiply(kb);

        if (size.compareTo(gb) >= 0) {
            return String.format(format, size.divide(gb)) + (lowerCase ? "gb" : "GB");
        } else if (size.compareTo(mb) >= 0) {
            return String.format(format, size.divide(mb)) + (lowerCase ? "mb" : "MB");
        } else {
            return String.format(format, size.divide(kb)) + (lowerCase ? "kb" : "KB");
        }
    }

    // Convert to Hex
    public static String toHex(int number) {
        return Integer.toHexString(number).toUpperCase();
    }

    // Number Conversion
    public static int toInt(Object value, int defaultValue) {
        try {
            return Integer.parseInt(value.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static long toLong(Object value, long defaultValue) {
        try {
            return Long.parseLong(value.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static double toDouble(Object value, double defaultValue) {
        try {
            return Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static BigDecimal toBigDecimal(Object value, BigDecimal defaultValue) {
        try {
            return new BigDecimal(value.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    // Math Operations
    public static <T extends Comparable<T>> T max(T a, T b) {
        return a.compareTo(b) >= 0 ? a : b;
    }

    public static <T extends Comparable<T>> T min(T a, T b) {
        return a.compareTo(b) <= 0 ? a : b;
    }

    public static <T extends Comparable<T>> T clamp(T value, T min, T max) {
        if (value.compareTo(min) < 0) return min;
        if (value.compareTo(max) > 0) return max;
        return value;
    }

    public static int factorial(int n) {
        int result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    // Prime Generator
    public static int nextPrime(int start) {
        while (!isPrime(start)) {
            start++;
        }
        return start;
    }

    // Decimal Utilities
    public static int getScale(BigDecimal value, boolean ignoreTrailingZeros) {
        if (ignoreTrailingZeros) value = value.stripTrailingZeros();
        return value.scale();
    }

    public static int getPrecision(BigDecimal value, boolean ignoreTrailingZeros) {
        if (ignoreTrailingZeros) value = value.stripTrailingZeros();
        return value.precision();
    }

    public static BigDecimal normalize(BigDecimal value) {
        return value.stripTrailingZeros();
    }

    // Double Utilities
    public static double normalize(double value) {
        return value / 1.0;
    }

    public static String formatNumber(BigDecimal value, String format, boolean ignoreTrailingZeros) {
        if (ignoreTrailingZeros) {
            value = value.stripTrailingZeros();
        }
        return String.format(format, value);
    }
}
