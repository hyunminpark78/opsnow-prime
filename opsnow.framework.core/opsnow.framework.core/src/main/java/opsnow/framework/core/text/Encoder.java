package opsnow.framework.core.text;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import opsnow.framework.core.ONIConst;
import opsnow.framework.core.pooling.StringBuilderPool;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
public class Encoder {
    /**
     * Encodes a string into Base64.
     * @param source The source string to encode.
     * @param encoding The character encoding to use for the source string.
     * @return The Base64 encoded string, or an empty string if the source is empty or null.
     * /// var s = "this is tested";
     * /// var enc = Encoder.EncodeBase64(s);
     * /// var dec = Encoder.DecodeBase64(enc);
     */
    public static String encodeBase64(String source, String encoding) {
        if (source == null || source.isEmpty()) {
            return "";
        }

        byte[] bytes = source.getBytes(Charset.forName(encoding));
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Encodes a byte array into a Base64 string.
     * @param source The byte array to encode.
     * @return The Base64 encoded string, or an empty string if the source is null.
     */
    public static String encodeBase64(byte[] source) {
        if (source == null) {
            return "";
        }

        return Base64.getEncoder().encodeToString(source);
    }

    /**
     * Encodes a part of a byte array into a Base64 string.
     * @param source The byte array to encode.
     * @param offset The start position in the byte array from which to begin using data.
     * @param length The number of bytes to use from the byte array.
     * @return The Base64 encoded string, or an empty string if the source is null.
     */
    public static String encodeBase64(byte[] source, int offset, int length) {
        if (source == null) {
            return "";
        }

        byte[] part = Arrays.copyOfRange(source, offset, offset + length);
        return Base64.getEncoder().encodeToString(part);
    }

    /**
     * Encodes the data from an input stream to a Base64 string.
     * @param input The input stream to read the data from.
     * @param leaveOpen If false, closes the input stream after encoding.
     * @return A string containing the Base64 encoded data.
     * @throws IOException If an I/O error occurs.
     */
    public static String encodeBase64(InputStream input, boolean leaveOpen) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        encodeBase64To(input, output, leaveOpen);
        return output.toString("UTF-8");
    }

    /**
     * Encodes data from an input stream to Base64 and writes it to an output stream.
     * @param input The input stream to read the data from.
     * @param output The output stream to write the Base64 encoded data to.
     * @param leaveOpen If false, closes the input stream after encoding.
     * @throws IOException If an I/O error occurs.
     */
    public static void encodeBase64To(InputStream input, ByteArrayOutputStream output, boolean leaveOpen) throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] buffer = new byte[8192];  // Adjust buffer size if necessary.
        int bytesRead;

        while ((bytesRead = input.read(buffer)) != -1) {
            byte[] part = Arrays.copyOfRange(buffer, 0, bytesRead);  // Copy only the read bytes.
            byte[] encodedBytes = encoder.encode(part);
            output.write(encodedBytes);
        }

        if (!leaveOpen) {
            input.close();
        }
    }

    /**
     * Decodes a base64 encoded string to a byte array.
     *
     * @param source The base64 encoded string.
     * @return The decoded byte array, or an empty array if the source is null or empty.
     * /// var s = "this is test";
     * /// var enc = Encoder.EncodeBase64(s);
     * /// var dec = Encoder.DecodeBase64(enc, Encoding.UTF8);
     */
    public static byte[] decodeBase64(String source) {
        if (source == null || source.isEmpty()) {
            return ONIConst.EmptyByteArray;
        }
        return Base64.getDecoder().decode(source);
    }

    /**
     * Decodes a base64 encoded string using the specified encoding.
     *
     * @param source The base64 encoded string.
     * @param encodingUsing The character encoding to use. If null, UTF-8 will be used.
     * @return The decoded string, or an empty string if the source is null or empty.
     */
    public static String decodeBase64(String source, Charset encodingUsing) {
        if (source == null || source.isEmpty()) {
            return "";
        }
        if (encodingUsing == null) {
            encodingUsing = StandardCharsets.UTF_8;
        }
        byte[] decodedBytes = Base64.getDecoder().decode(source);
        return new String(decodedBytes, encodingUsing);
    }

    /**
     * Decodes a Base64 encoded byte array.
     *
     * @param source The source byte array.
     * @return The decoded byte array or an empty byte array if source is null or empty.
     */
    public static byte[] decodeBase64(byte[] source) {
        if (source == null || source.length == 0) {
            return ONIConst.EmptyByteArray;
        }
        return decodeBase64(source, 0, source.length);
    }

    /**
     * Decodes a segment of a byte array from the specified offset and length.
     *
     * @param source The source byte array.
     * @param offset The position in the byte array from where to start decoding.
     * @param length The number of bytes to decode.
     * @return The decoded byte array or an empty byte array if source is null or if length is 0.
     */
    public static byte[] decodeBase64(byte[] source, int offset, int length) {
        if (source == null || length == 0 || offset + length > source.length) {
            return ONIConst.EmptyByteArray;
        }

        // Extract the segment of the array to decode
        byte[] segment = Arrays.copyOfRange(source, offset, offset + length);

        // Decode using Java's Base64 decoder
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(segment);
    }

    /**
     * Decodes a Base64 encoded byte array and converts it to a string using the specified encoding.
     *
     * @param source The source byte array.
     * @param encodingUsing The Charset to use for the decoded byte array to string conversion.
     * @return The decoded string or an empty string if source is null or empty.
     */
    public static String decodeBase64(byte[] source, Charset encodingUsing) {
        if (source == null || source.length == 0) {
            return ""; // Returning empty string
        }
        return decodeBase64(source, 0, source.length, encodingUsing);
    }

    /**
     * Decodes a segment of a byte array from the specified offset and length and converts it to a string using the specified encoding.
     *
     * @param source The source byte array.
     * @param offset The position in the byte array from where to start decoding.
     * @param length The number of bytes to decode.
     * @param encodingUsing The Charset to use for the decoded byte array to string conversion.
     * @return The decoded string or an empty string if the source segment is invalid.
     */
    public static String decodeBase64(byte[] source, int offset, int length, Charset encodingUsing) {
        if (source == null || length == 0 || offset + length > source.length) {
            return ""; // Returning empty string
        }

        // Extract the segment of the array to decode
        byte[] segment = Arrays.copyOfRange(source, offset, offset + length);

        // Decode using Java's Base64 decoder
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decoded = decoder.decode(segment);

        // Convert the decoded bytes to a string using the specified Charset
        return new String(decoded, encodingUsing);
    }

    /**
     * Converts a Base64 encoded input stream into a byte array.
     *
     * @param input     The input stream containing Base64 encoded data.
     * @param leaveOpen Whether to leave the input stream open after reading.
     * @return The decoded byte array.
     * @throws Exception If an I/O error occurs.
     */
    public static byte[] decodeBase64(InputStream input, boolean leaveOpen) throws Exception {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            decodeBase64To(input, output, leaveOpen);
            return output.toByteArray();
        }
    }

    /**
     * Converts a Base64 encoded input stream into a string using the specified encoding.
     *
     * @param input         The input stream containing Base64 encoded data.
     * @param encodingUsing The character encoding to use for the output string.
     * @param leaveOpen     Whether to leave the input stream open after reading.
     * @return The decoded string.
     * @throws Exception If an I/O error occurs.
     */
    public static String decodeBase64(InputStream input, Charset encodingUsing, boolean leaveOpen) throws Exception {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            decodeBase64To(input, output, leaveOpen);
            return output.toString(encodingUsing.name());
        }
    }

    /**
     * Decodes Base64 from an input stream to an output stream.
     *
     * @param input     The input stream containing Base64 encoded data.
     * @param output    The output stream where the decoded data will be written.
     * @param leaveOpen Whether to leave the input stream open after reading.
     * @throws Exception If an I/O error occurs.
     */
    public static void decodeBase64To(InputStream input, OutputStream output, boolean leaveOpen) throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        try (InputStream base64Stream = Base64.getMimeDecoder().wrap(input)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = base64Stream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } finally {
            if (!leaveOpen) {
                input.close();
            }
        }
    }

    /**
     * Encodes a string to URL-safe Base64.
     *
     * @param source The source string to encode.
     * @return The URL-safe Base64 encoded string.
     * /// var s = "this is test";
     * /// var enc = Encoder.EncodeBaseUrl64(s);
     * /// var dec = Encoder.DecodeBaseUrl64(enc);
     */
    public static String encodeBaseUrl64(String source) {
        if (source == null || source.isEmpty()) {
            return "";
        }
        String base64 = Base64.getEncoder().encodeToString(source.getBytes());
        return encodeUrl64Helper(base64);
    }

    /**
     * Encodes a byte array to URL-safe Base64.
     *
     * @param source The byte array to encode.
     * @return The URL-safe Base64 encoded string.
     */
    public static String encodeBaseUrl64(byte[] source) {
        if (source == null || source.length == 0) {
            return "";
        }
        String base64 = Base64.getEncoder().encodeToString(source);
        return encodeUrl64Helper(base64);
    }

    /**
     * Converts a Base64 string into a URL-safe Base64 string by replacing '+' with '-',
     * '/' with '_', and removing padding characters ('=').
     *
     * @param base64 The Base64 encoded string.
     * @return The URL-safe Base64 encoded string.
     */
    private static String encodeUrl64Helper(String base64) {
        if (base64 == null || base64.isEmpty()) {
            return base64;
        }

        Matcher matcher = ONIConst.ENCODE_URL64_REGEX.matcher(base64);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String replacement;
            switch (matcher.group()) {
                case "/":
                    replacement = "_";
                    break;
                case "+":
                    replacement = "-";
                    break;
                default:
                    replacement = ""; // Remove padding '='
                    break;
            }
            matcher.appendReplacement(result, replacement);
        }
        matcher.appendTail(result);

        return result.toString();
    }

    /**
     * Decodes a URL-safe Base64 string into a byte array.
     *
     * @param source The URL-safe Base64 encoded string.
     * @return The decoded byte array.
     * /// var s = "this is test";
     * /// var enc = Encoder.EncodeBaseUrl64(s);
     * /// var dec = Encoder.DecodeBaseUrl64(enc, Encoding.UTF8);
     */
    public static byte[] decodeBaseUrl64(String source) {
        String base64 = decodeUrl64Helper(source);
        return Base64.getDecoder().decode(base64);
    }

    /**
     * Decodes a URL-safe Base64 string into a string using the specified encoding.
     *
     * @param source         The URL-safe Base64 encoded string.
     * @param encodingUsing  The charset to use for decoding.
     * @return The decoded string.
     */
    public static String decodeBaseUrl64(String source, Charset encodingUsing) {
        if (encodingUsing == null) {
            encodingUsing = StandardCharsets.UTF_8;
        }
        String base64 = decodeUrl64Helper(source);
        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        return new String(decodedBytes, encodingUsing);
    }

    /**
     * Converts a URL-safe Base64 string back to standard Base64 by replacing '-' with '+'
     * and '_' with '/', and adding padding characters ('=') if needed.
     *
     * @param source The URL-safe Base64 encoded string.
     * @return The standard Base64 encoded string.
     */
    private static String decodeUrl64Helper(String source) {
        if (source == null || source.isEmpty()) {
            return "";
        }

        // Fixing up '-' -> '+' and '_' -> '/'
        Matcher matcher = ONIConst.DECODE_URL64_REGEX.matcher(source);
        StringBuffer base64 = new StringBuffer();

        while (matcher.find()) {
            String replacement = matcher.group().equals("-") ? "+" : "/";
            matcher.appendReplacement(base64, replacement);
        }
        matcher.appendTail(base64);

        // Add padding characters '=' to make the length a multiple of 4
        int padding = 3 - ((base64.length() + 3) % 4);
        if (padding > 0) {
            for (int i = 0; i < padding; i++) {
                base64.append('=');
            }
        }

        return base64.toString();
    }

    // URL 관련 메서드

    /**
     * URL을 인코딩합니다.
     * @param source 인코딩할 문자열
     * @return 인코딩된 문자열
     * /// var s = "this is test";
     * /// var enc = s.vEncodeURL();
     * /// var dec = enc.vDecodeURL();
     */
    public static String encodeURL(String source) {
        if (source == null || source.trim().isEmpty()) {
            return "";
        }
        return URLEncoder.encode(source.trim(), StandardCharsets.UTF_8);
    }

    /**
     * URL을 디코딩합니다.
     * @param source 디코딩할 문자열
     * @return 디코딩된 문자열
     * /// var s = "this is test";
     * /// var enc = s.vEncodeURL();
     * /// var dec = enc.vDecodeURL();
     */
    public static String decodeURL(String source) {
        if (source == null || source.trim().isEmpty()) {
            return "";
        }
        return URLDecoder.decode(source.trim(), StandardCharsets.UTF_8);
    }

    // HTML 관련 메서드

    /**
     * HTML 문자열을 인코딩합니다.
     * @param source 인코딩할 HTML 문자열
     * @return 인코딩된 HTML 문자열
     * /// var s = "this is test";
     * /// var enc = s.vEncodeHTML();
     * /// var dec = enc.vDecodeHTML();
     */
    public static String encodeHTML(String source) {
        if (source == null || source.isEmpty()) {
            return "";
        }
        return source.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    /**
     * HTML 문자열을 디코딩합니다.
     * @param source 디코딩할 HTML 문자열
     * @return 디코딩된 HTML 문자열
     * /// var s = "this is test";
     * /// var enc = s.vEncodeHTML();
     * /// var dec = enc.vDecodeHTML();
     */
    public static String decodeHTML(String source) {
        if (source == null || source.isEmpty()) {
            return "";
        }
        return source.replace("&amp;", "&")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&quot;", "\"")
                .replace("&#39;", "'");
    }

    // QueryString 관련 메서드

    /**
     * 객체를 URL 쿼리 문자열로 변환합니다.
     * @param source 변환할 객체 (Map 형식)
     * @param separator 쿼리 문자열의 구분자 (& 기본값)
     * @return 쿼리 문자열
     * /// var arr = new { first = 1, last = 3 };
     * /// AppRuntime.Serializer.ToQueryString(arr) //=> "first=1&last=3"
     */
    public static String toQueryString(Map<String, Object> source, String separator) {
        if (source == null || source.isEmpty()) {
            return "";
        }

        if (separator == null || separator.isEmpty()) {
            separator = "&";
        }

        StringJoiner queryString = new StringJoiner(separator);

        source.forEach((key, value) -> {
            queryString.add(encodeURL(key) + "=" + encodeURL(value.toString()));
        });

        return queryString.toString();
    }

    /**
     * Tries to convert an object to a URL query string.
     *
     * @param source    The source object to be converted.
     * @param separator The separator to use between key-value pairs.
     * @param result    A StringBuilder to store the resulting query string.
     * @return True if the conversion was successful; otherwise, false.
     */
    public static boolean tryToQueryString(Object source, String separator, StringBuilder result) {
        if (source == null) {
            result.setLength(0); // Clear the result
            return true;
        }

        // If the source is a String
        if (source instanceof String) {
            result.setLength(0);
            result.append(source);
            return true;
        }

        if (separator == null || separator.isEmpty()) {
            separator = "&";
        }

        // If the source is a Map (equivalent to IDictionary in C#)
        if (source instanceof Map<?, ?>) {
            Map<?, ?> dict = (Map<?, ?>) source;
            StringBuilder sb = StringBuilderPool.take();
            for (Map.Entry<?, ?> entry : dict.entrySet()) {
                if (sb.length() > 0) {
                    sb.append(separator);
                }
                sb.append(encodeURL(String.valueOf(entry.getKey())))
                        .append("=")
                        .append(encodeURL(String.valueOf(entry.getValue())));
            }
            result.setLength(0);
            result.append(sb);
            return true;
        }

        // If the source is a Properties object (similar to NameValueCollection in C#)
        if (source instanceof Properties) {
            Properties props = (Properties) source;
            StringBuilder sb = StringBuilderPool.take();
            for (String key : props.stringPropertyNames()) {
                if (sb.length() > 0) {
                    sb.append(separator);
                }
                sb.append(encodeURL(key))
                        .append("=")
                        .append(encodeURL(props.getProperty(key)));
            }
            result.setLength(0);
            result.append(sb);
            return true;
        }

        // If the source is not supported
        result.setLength(0);
        return false;
    }

    /**
     * URL 쿼리 문자열을 Map으로 변환합니다.
     * @param queryString 파싱할 쿼리 문자열
     * @return 변환된 Map
     * /// var s = "var1=1&var2=2+2%2f3&var1=3";
     * /// var coll = AppRuntime.Serializer.FromQueryString(s);  //=> return a new NameValueCollection
     */
    public static Map<String, String> fromQueryString(String queryString) {
        Map<String, String> map = new HashMap<>();
        if (queryString == null || queryString.trim().isEmpty()) {
            return map;
        }
        String[] pairs = queryString.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                map.put(decodeURL(keyValue[0]), decodeURL(keyValue[1]));
            } else if (keyValue.length == 1) {
                map.put(decodeURL(keyValue[0]), "");
            }
        }
        return map;
    }


    ///  Hex

    private static final Pattern HEX_PREFIX_REGEX = Pattern.compile("(\\\\x|0x|%)", Pattern.CASE_INSENSITIVE);

    // Converts a byte array into a hex string
    public static String encodeHex(String source, Charset encodingUsing) {
        if (source == null || source.isEmpty()) return "";
        byte[] bytes = source.getBytes(encodingUsing != null ? encodingUsing : StandardCharsets.UTF_8);
        return encodeHex(bytes, 0, bytes.length);
    }

    public static String encodeHex(byte[] source) {
        if (source == null) return "";
        return encodeHex(source, 0, source.length);
    }

    public static String encodeHex(byte[] source, int offset, int count) {
        if (source == null) return "";
        StringBuilder sb = StringBuilderPool.take(count * 2);
        encodeHex(source, offset, count, sb);
        return StringBuilderPool.toString(sb);
    }

    public static void encodeHex(byte[] source, StringBuilder sb) {
        if (source == null) return;
        encodeHex(source, 0, source.length, sb);
    }

    public static void encodeHex(byte[] source, int offset, int count, StringBuilder sb) {
        if (source == null) return;
        for (int i = offset; i < offset + count; i++) {
            sb.append(String.format("%02x", source[i]));
        }
    }

    public static String encodeHex(byte[] source, HexConverter converter) {
        if (source == null) return "";
        return encodeHex(source, 0, source.length, converter);
    }

    public static String encodeHex(byte[] source, int offset, int count, HexConverter converter) {
        if (source == null) return "";
        StringBuilder sb = StringBuilderPool.take(count * 2);
        for (int i = offset; i < offset + count; i++) {
            sb.append(converter.convert(String.format("%02x", source[i])));
        }
        return StringBuilderPool.toString(sb);
    }

    public static void encodeHex(byte[] source, Writer writer) {
        if (source == null) return;
        encodeHex(source, 0, source.length, writer);
    }

    public static void encodeHex(byte[] source, int offset, int count, Writer writer) {
        if (source == null) return;
        try {
            for (int i = offset; i < offset + count; i++) {
                writer.write(String.format("%02x", source[i]));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error writing hex data", e);
        }
    }

    // Converts hex string to a byte array
    public static byte[] decodeHex(String source) {
        if (source == null || source.isEmpty()) return ONIConst.EmptyByteArray;
        String str = HEX_PREFIX_REGEX.matcher(source).replaceAll("");
        if (str.length() % 2 != 0) return ONIConst.EmptyByteArray;
        int length = str.length() / 2;
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = (byte) Integer.parseInt(str.substring(2 * i, 2 * i + 2), 16);
        }
        return result;
    }

    public static String decodeHex(String source, Charset encodingUsing) {
        return new String(decodeHex(source), encodingUsing != null ? encodingUsing : StandardCharsets.UTF_8);
    }

    public static byte[] decodeHex(byte[] source) {
        return decodeHex(new String(source, StandardCharsets.UTF_8));
    }

    public static String decodeHex(byte[] source, Charset encodingUsing) {
        return new String(decodeHex(new String(source, encodingUsing)), encodingUsing);
    }

    // HexConverter interface for custom conversions
    @FunctionalInterface
    public interface HexConverter {
        String convert(String hex);
    }
}
