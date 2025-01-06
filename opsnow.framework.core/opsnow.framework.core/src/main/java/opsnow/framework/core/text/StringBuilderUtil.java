package opsnow.framework.core.text;

import ch.qos.logback.core.encoder.ByteArrayUtil;

import java.util.Objects;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 *     /// var sb = new StringBuilder();
 *     ///
 *     /// sb.PrependLine("D");
 *     /// sb.Prepend("C");
 *     /// sb.Prepend("{0}{1}", "A", "B");
 *     ///
 *     /// var result = sb.ToString();
 *     /// ==> ABCD\r\n
 */
public class StringBuilderUtil {
    /**
     * Prepend a string to the StringBuilder.
     * @param builder The StringBuilder instance.
     * @param value The string to prepend.
     * @return The updated StringBuilder instance.
     */
    public static StringBuilder prepend(StringBuilder builder, String value) {
        Objects.requireNonNull(builder, "StringBuilder cannot be null");
        return builder.insert(0, value);
    }

    /**
     * Prepend a formatted string to the StringBuilder.
     * @param builder The StringBuilder instance.
     * @param format The format string.
     * @param args The arguments for formatting.
     * @return The updated StringBuilder instance.
     */
    public static StringBuilder prepend(StringBuilder builder, String format, Object... args) {
        Objects.requireNonNull(builder, "StringBuilder cannot be null");
        String formatted = String.format(format, args);
        return prepend(builder, formatted);
    }

    /**
     * Prepend a string followed by a new line to the StringBuilder.
     * @param builder The StringBuilder instance.
     * @param value The string to prepend.
     * @return The updated StringBuilder instance.
     * /// var sb = new StringBuilder();
     * ///
     * /// sb.AppendLine("A");
     * /// sb.AppendLine("{0}", "B");
     * /// var result = sb.ToString();
     * /// ==> A\r\nB\r\n
     */
    public static StringBuilder prependLine(StringBuilder builder, String value) {
        Objects.requireNonNull(builder, "StringBuilder cannot be null");
        return prepend(builder, value + System.lineSeparator());
    }

    /**
     * Prepend a formatted string followed by a new line to the StringBuilder.
     * @param builder The StringBuilder instance.
     * @param format The format string.
     * @param args The arguments for formatting.
     * @return The updated StringBuilder instance.
     */
    public static StringBuilder prependLine(StringBuilder builder, String format, Object... args) {
        Objects.requireNonNull(builder, "StringBuilder cannot be null");
        String formatted = String.format(format, args);
        return prependLine(builder, formatted);
    }

    /**
     * Append a formatted string and then a new line to the StringBuilder.
     * @param builder The StringBuilder instance.
     * @param format The format string.
     * @param args The arguments for formatting.
     * @return The updated StringBuilder instance.
     */
    public static StringBuilder appendLine(StringBuilder builder, String format, Object... args) {
        Objects.requireNonNull(builder, "StringBuilder cannot be null");
        builder.append(String.format(format, args));
        return builder.append(System.lineSeparator());
    }
}
