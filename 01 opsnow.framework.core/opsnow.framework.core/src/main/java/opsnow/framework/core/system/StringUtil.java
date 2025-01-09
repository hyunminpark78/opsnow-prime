package opsnow.framework.core.system;

import opsnow.framework.core.ONIConst;
import opsnow.framework.core.ONIUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
public class StringUtil {

    /// RuleValidation

    public static boolean isEmpty(String source) {
        return source == null || source.isEmpty();
    }

    public static boolean isNotEmpty(String source) {
        return !isEmpty(source);
    }

    public static boolean isEquals(String source, String target, boolean ignoreCase) {
        if (source == null || target == null) return false;
        return ignoreCase ? source.equalsIgnoreCase(target) : source.equals(target);
    }

    public static boolean isEquals(String source, List<String> values, boolean ignoreCase) {
        if (isEmpty(source) || values == null || values.isEmpty()) return false;
        for (String value : values) {
            if (isEquals(source, value, ignoreCase)) return true;
        }
        return false;
    }

    public static boolean isAscii(String value, boolean permitCrlf) {
        if (isEmpty(value)) return false;
        for (char ch : value.toCharArray()) {
            if (ch > 127 || (!permitCrlf && (ch == '\r' || ch == '\n'))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAnsi(String value, boolean permitCrlf) {
        if (isEmpty(value)) return false;
        for (char ch : value.toCharArray()) {
            if (ch > 255 || (!permitCrlf && (ch == '\r' || ch == '\n'))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPrintable(String value, boolean permitCrlf) {
        if (isEmpty(value)) return false;
        for (char ch : value.toCharArray()) {
            if (ch < 32 && ch != '\t') {
                if (permitCrlf && (ch == '\r' || ch == '\n')) continue;
                return false;
            } else if (ch > 255) {
                return false;
            }
        }
        return true;
    }

    public static boolean isLetter(String value) {
        return value != null && value.matches("^[a-zA-Z\\-_]+$");
    }

    public static boolean isLetterOrDigit(String value) {
        return value != null && value.matches("^[a-zA-Z0-9]+$");
    }

    public static boolean isNumeric(String value) {
        return value != null && value.replace(",", "").matches("^-?\\d+(\\.\\d+)?$");
    }

    public static boolean isDigit(String value) {
        return value != null && value.matches("^-?\\d+$");
    }

    public static boolean isHexDigit(String value) {
        return value != null && value.matches("^[\\dA-Fa-f]+$");
    }

    public static boolean isCurrency(String value) {
        return value != null && value.matches("^\\$?(\\d{1,3}(,\\d{3})*|\\d+)(\\.\\d{1,2})?$");
    }

    public static boolean isEmail(String value) {
        return value != null && value.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    public static boolean isUrl(String value) {
        return value != null && value.matches("^(https?|ftp)://[\\w-]+(\\.[\\w-]+)+[/#?]?.*$");
    }

    public static boolean isDomainName(String value) {
        return value != null && value.matches("^[\\w-]+(\\.[\\w-]+)+$");
    }

    public static boolean isJson(String source) {
        return isJsObject(source) || isJsArray(source);
    }

    public static boolean isJsObject(String source) {
        return !isEmpty(source) && source.startsWith("{") && source.endsWith("}");
    }

    public static boolean isJsArray(String source) {
        return !isEmpty(source) && source.startsWith("[") && source.endsWith("]");
    }

    public static boolean hasUnicode(String value) {
        if (isEmpty(value)) return false;
        return !value.equals(value.replaceAll("[^\\p{ASCII}]", ""));
    }

    public static boolean hasHtmlTag(String source) {
        return !isEmpty(source) && Pattern.compile("<(\"[^\"]*\"|'[^']*'|[^'\">])*?>").matcher(source).find();
    }

    /// Chop

    public static String[] chop(String source, int slice) {
        return chop(source, slice, null);
    }

    public static String[] chop(String source, int slice, Function<String, String> converter) {
        if (source == null || source.isEmpty()) {
            return new String[]{};
        }

        List<String> result = new ArrayList<>();
        String input = source;
        int length = Math.abs(slice);

        if (input.length() <= length || length == 0) {
            result.add(converter != null ? converter.apply(input) : input);
        } else {
            String temp;

            // Handle negative slice
            if (slice < 0) {
                int remainder = input.length() % length;
                if (remainder > 0) {
                    temp = input.substring(0, remainder);
                    result.add(converter != null ? converter.apply(temp) : temp);
                    input = input.substring(remainder);
                }
            }

            while (!input.isEmpty()) {
                // Adjust length for the last slice
                length = Math.min(input.length(), length);
                temp = input.substring(0, length);
                result.add(converter != null ? converter.apply(temp) : temp);
                input = input.substring(length);
            }
        }

        return result.toArray(new String[0]);
    }

    /// Replace

    // Replace single character
    public static String replace(String source, char pattern, char newValue) {
        if (source == null) {
            return "";
        }
        return source.replace(pattern, newValue);
    }

    // Replace with a regex pattern and replacement
    public static String replace(String source, String pattern, String newValue, boolean ignoreCase) {
        if (source == null || pattern == null || pattern.isEmpty()) {
            return source != null ? source : "";
        }

        Pattern regex = ignoreCase ? Pattern.compile(pattern, Pattern.CASE_INSENSITIVE) : Pattern.compile(pattern);
        Matcher matcher = regex.matcher(source);

        return matcher.replaceAll(newValue);
    }

    // Replace using a MatchEvaluator-like functionality
    public static String replace(String source, Pattern regex, ReplacementEvaluator evaluator) {
        if (source == null || regex == null || evaluator == null) {
            return "";
        }

        Matcher matcher = regex.matcher(source);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(sb, evaluator.evaluate(matcher));
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    // Replace with special replacement patterns
    public static String replace(String source, Pattern regex, String replaceText, int count) {
        if (source == null || regex == null) {
            return "";
        }

        Matcher matcher = regex.matcher(source);
        StringBuffer sb = new StringBuffer();
        int replacements = 0;

        while (matcher.find() && replacements < count) {
            String replacement = processSpecialPatterns(matcher, source, replaceText);
            matcher.appendReplacement(sb, replacement);
            replacements++;
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    private static String processSpecialPatterns(Matcher match, String source, String replaceText) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < replaceText.length(); i++) {
            char c = replaceText.charAt(i);
            if (c == '$' && i < replaceText.length() - 1) {
                c = replaceText.charAt(++i);

                switch (c) {
                    case '$':
                        sb.append('$');
                        break;
                    case '&':
                        sb.append(match.group(0));
                        break;
                    case '`':
                        sb.append(source.substring(0, match.start()));
                        break;
                    case '\'':
                        sb.append(source.substring(match.end()));
                        break;
                    default:
                        if (Character.isDigit(c)) {
                            int groupIndex = Character.getNumericValue(c);
                            if (groupIndex < match.groupCount()) {
                                sb.append(match.group(groupIndex));
                            }
                        } else {
                            sb.append('$').append(c);
                        }
                        break;
                }
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    // Replace first occurrence
    public static String replaceFirst(String source, String search, String replacement, boolean ignoreCase) {
        if (source == null || search == null || replacement == null) {
            return source != null ? source : "";
        }

        Pattern pattern = ignoreCase ? Pattern.compile(search, Pattern.CASE_INSENSITIVE) : Pattern.compile(search);
        Matcher matcher = pattern.matcher(source);

        if (matcher.find()) {
            return matcher.replaceFirst(replacement);
        }

        return source;
    }

    // Replace last occurrence
    public static String replaceLast(String source, String search, String replacement, boolean ignoreCase) {
        if (source == null || search == null || replacement == null) {
            return source != null ? source : "";
        }

        Pattern pattern = ignoreCase ? Pattern.compile(search, Pattern.CASE_INSENSITIVE) : Pattern.compile(search);
        Matcher matcher = pattern.matcher(source);

        int lastMatchStart = -1;
        int lastMatchEnd = -1;

        while (matcher.find()) {
            lastMatchStart = matcher.start();
            lastMatchEnd = matcher.end();
        }

        if (lastMatchStart != -1) {
            return source.substring(0, lastMatchStart) + replacement + source.substring(lastMatchEnd);
        }

        return source;
    }

    // Replace CRLF with <br>
    public static String replaceCR2BR(String source) {
        if (source == null || source.isEmpty()) {
            return "";
        }

        // Check if the source contains HTML tags
        Matcher htmlMatcher = ONIConst.HTML_TAG_REGEX.matcher(source);
        if (!htmlMatcher.find()) {
            // Replace CRLF with <br>
            Matcher crlfMatcher = ONIConst.CRLF_REGEX.matcher(source);
            return crlfMatcher.replaceAll("<br>");
        }

        return source;
    }

    // Replace CRLF with a specific value
    public static String replaceCR2Value(String source, String replaceValue) {
        if (source == null || source.isEmpty()) {
            return "";
        }

        Matcher crlfMatcher = ONIConst.CRLF_REGEX.matcher(source);
        return crlfMatcher.replaceAll(replaceValue);
    }

    @FunctionalInterface
    public interface ReplacementEvaluator {
        String evaluate(Matcher match);
    }

    /// Grep

    /**
     * Finds all occurrences of a given pattern in the source string and returns a list of matched strings.
     * The pattern can be a string or a regular expression.
     *
     * @param source  The source string.
     * @param pattern The pattern to search for.
     * @return A list of strings matching the given pattern.
     */
    public static List<String> grep(String source, String pattern) {
        return grep(source, pattern, Matcher::group);
    }

    /**
     * Finds all occurrences of a given pattern in the source string and returns a list of transformed matched strings.
     * The pattern can be a string or a regular expression, and the matches can be transformed using a valueSelector.
     *
     * @param source        The source string.
     * @param pattern       The pattern to search for.
     * @param valueSelector A function to transform the matched strings.
     * @return A list of transformed strings matching the given pattern.
     */
    public static List<String> grep(String source, String pattern, Function<Matcher, String> valueSelector) {
        if (source == null || source.isEmpty() || pattern == null || pattern.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(source);

        while (matcher.find()) {
            result.add(valueSelector.apply(matcher));
        }

        return result;
    }

    /// Regex

    /**
     * Checks whether the source string matches the given pattern starting from the specified position.
     *
     * @param source The source string.
     * @param pattern The pattern to match.
     * @param startAt The starting position for the match (default is 0).
     * @return True if a match is found, otherwise false.
     */
    public static boolean isMatch(String source, String pattern, int startAt) {
        if (source == null || pattern == null) {
            return false;
        }
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(source);
        return matcher.find(startAt);
    }

    /**
     * Returns the first match of the given pattern in the source string starting from the specified position.
     *
     * @param source The source string.
     * @param pattern The pattern to match.
     * @param startAt The starting position for the match (default is 0).
     * @return The first match as a Matcher object, or null if no match is found.
     */
    public static Matcher findMatch(String source, String pattern, int startAt) {
        if (source == null || pattern == null) {
            return null;
        }
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(source);
        return matcher.find(startAt) ? matcher : null;
    }

    /**
     * Returns all matches of the given pattern in the source string starting from the specified position.
     *
     * @param source The source string.
     * @param pattern The pattern to match.
     * @param startAt The starting position for the match (default is 0).
     * @return A list of Matcher objects containing all matches.
     */
    public static List<Matcher> findAllMatches(String source, String pattern, int startAt) {
        if (source == null || pattern == null) {
            return new ArrayList<>();
        }
        List<Matcher> matches = new ArrayList<>();
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(source);

        while (matcher.find(startAt)) {
            matches.add(matcher);
            startAt = matcher.end(); // Update startAt to avoid overlapping matches.
        }

        return matches;
    }

    /// Length

    /**
     * Gets the size of the characters in the string.
     * For ASCII characters, it adds 1 to the length.
     * For non-ASCII characters (2-byte), it adds 2 to the length.
     *
     * @param source The source string.
     * @return The calculated size of the characters.
     */
    public static int getCharSize(String source) {
        if (source == null || source.isEmpty()) {
            return 0;
        }

        int length = 0;
        char[] buffer = source.toCharArray();

        for (char ch : buffer) {
            if (ch < 0 || ch >= 128) { // If it is a non-ASCII character
                length += 2;
            } else { // ASCII character
                length += 1;
            }
        }
        return length;
    }

    ///  Filtering

    /**
     * Keeps only alphanumeric characters.
     *
     * @param source Input string
     * @return The string containing only alphanumeric characters
     */
    public static String toAlphaNumericOnly(String source) {
        return ONIUtil.buildString(grep(source, ONIConst.ALPHA_DIGIT_ONLY_REGEX), String::valueOf);
    }

    /**
     * Keeps only alpha characters.
     *
     * @param source Input string
     * @return The string containing only alpha characters
     */
    public static String toAlphaCharactersOnly(String source) {
        return ONIUtil.buildString(grep(source, ONIConst.ALPHA_CHAR_REGEX), String::valueOf);
    }

    /**
     * Keeps only numeric characters.
     *
     * @param source Input string
     * @return The string containing only numeric characters
     */
    public static String toNumericOnly(String source) {
        return ONIUtil.buildString(grep(source, ONIConst.NUMERIC_REGEX), String::valueOf);
    }

    /**
     * Filters matches from a string based on the provided pattern.
     *
     * @param source  The input string
     * @param pattern The regex pattern to match
     * @return A list of matching substrings
     */
    public static List<String> grep(String source, Pattern pattern) {
        List<String> results = new ArrayList<>();
        if (source == null || pattern == null) {
            return results;
        }

        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            results.add(matcher.group());
        }

        return results;
    }

    /// Extract

    /**
     * Extracts a substring between two specified characters.
     *
     * @param source  The source string.
     * @param lpattern The left delimiter.
     * @param rpattern The right delimiter.
     * @return The extracted substring, or null if the patterns are not found.
     */
    public static String extract(String source, char lpattern, char rpattern) {
        if (source == null || source.isEmpty()) {
            return null;
        }

        // Find the left pattern
        int leftIndex = source.indexOf(lpattern);
        if (leftIndex == -1) {
            return null;
        }

        // Extract the substring after the left pattern
        String str2 = source.substring(leftIndex + 1);

        // Find the right pattern
        int rightIndex = str2.indexOf(rpattern);
        if (rightIndex == -1) {
            return null;
        }

        // Return the substring between the patterns
        return str2.substring(0, rightIndex);
    }

    /**
     * Extracts a substring between two patterns using a more generic approach.
     *
     * @param source  The source string.
     * @param lpattern The left pattern (can be a string or regex).
     * @param rpattern The right pattern (can be a string or regex).
     * @return The extracted substring, or null if the patterns are not found.
     */
    public static String extract(String source, String lpattern, String rpattern) {
        if (source == null || source.isEmpty()) {
            return null;
        }

        // Find the left pattern
        int leftIndex = source.indexOf(lpattern);
        if (leftIndex == -1) {
            return null;
        }

        // Extract the substring after the left pattern
        String str2 = source.substring(leftIndex + lpattern.length());

        // Find the right pattern
        int rightIndex = str2.indexOf(rpattern);
        if (rightIndex == -1) {
            return null;
        }

        // Return the substring between the patterns
        return str2.substring(0, rightIndex);
    }

    /// IndexOf

    public static int indexOf(String source, char item) {
        if (source == null) return -1;
        return source.indexOf(item);
    }

    public static int indexOf(String source, char... items) {
        if (source == null) return -1;
        for (char item : items) {
            int index = source.indexOf(item);
            if (index != -1) return index;
        }
        return -1;
    }

    public static int indexOf(String source, Object pattern, boolean ignoreCase) {
        return indexOf(source, pattern, 0, ignoreCase);
    }

    public static int indexOf(String source, Object pattern, int startIndex, boolean ignoreCase) {
        if (source == null || source.isEmpty()) return -1;

        if (startIndex < 0) {
            startIndex = Math.max(source.length() + startIndex, 0);
        } else if (startIndex >= source.length()) {
            startIndex = source.length() - 1;
        }

        if (pattern instanceof String) {
            String strPattern = (String) pattern;
            if (ignoreCase) {
                source = source.toLowerCase();
                strPattern = strPattern.toLowerCase();
            }
            return source.indexOf(strPattern, startIndex);
        } else if (pattern instanceof Character) {
            return source.indexOf((Character) pattern, startIndex);
        } else if (pattern instanceof Pattern) {
            Pattern regex = (Pattern) pattern;
            Matcher matcher = regex.matcher(source.substring(startIndex));
            return matcher.find() ? matcher.start() + startIndex : -1;
        }
        return -1;
    }

    public static int indexOf(String source, Object... items) {
        return indexOf(source, List.of(items), 0, true);
    }

    public static int indexOf(String source, List<Object> items, boolean ignoreCase) {
        return indexOf(source, items, 0, ignoreCase);
    }

    public static int indexOf(String source, List<Object> items, int startIndex, boolean ignoreCase) {
        for (Object pattern : items) {
            int index = indexOf(source, pattern, startIndex, ignoreCase);
            if (index != -1) {
                return index;
            }
        }
        return -1;
    }

    public static int lastIndexOf(String source, char item) {
        if (source == null) return -1;
        return source.lastIndexOf(item);
    }

    public static int lastIndexOf(String source, char... items) {
        if (source == null) return -1;
        for (char item : items) {
            int index = source.lastIndexOf(item);
            if (index != -1) return index;
        }
        return -1;
    }

    public static int lastIndexOf(String source, Object pattern, boolean ignoreCase) {
        return lastIndexOf(source, pattern, -1, ignoreCase);
    }

    public static int lastIndexOf(String source, Object pattern, int startIndex, boolean ignoreCase) {
        if (source == null || source.isEmpty()) return -1;

        if (startIndex < 0) {
            startIndex = Math.max(source.length() + startIndex, 0);
        } else if (startIndex >= source.length()) {
            startIndex = source.length() - 1;
        }

        if (pattern instanceof String) {
            String strPattern = (String) pattern;
            if (ignoreCase) {
                source = source.toLowerCase();
                strPattern = strPattern.toLowerCase();
            }
            return source.lastIndexOf(strPattern, startIndex);
        } else if (pattern instanceof Character) {
            return source.lastIndexOf((Character) pattern, startIndex);
        } else if (pattern instanceof Pattern) {
            Pattern regex = (Pattern) pattern;
            Matcher matcher = regex.matcher(source.substring(0, startIndex + 1));
            int lastIndex = -1;
            while (matcher.find()) {
                lastIndex = matcher.start();
            }
            return lastIndex;
        }
        return -1;
    }

    public static int lastIndexOf(String source, Object... items) {
        return lastIndexOf(source, List.of(items), -1, true);
    }

    public static int lastIndexOf(String source, List<Object> items, boolean ignoreCase) {
        return lastIndexOf(source, items, -1, ignoreCase);
    }

    public static int lastIndexOf(String source, List<Object> items, int startIndex, boolean ignoreCase) {
        for (Object pattern : items) {
            int index = lastIndexOf(source, pattern, startIndex, ignoreCase);
            if (index != -1) {
                return index;
            }
        }
        return -1;
    }

    /// Contains

    public static char getAt(String source, int index) {
        if (source == null || source.isEmpty() || index >= source.length()) {
            return '\0'; // Default char
        }
        if (index < 0) {
            index = Math.max(source.length() + index, 0);
        }
        return source.charAt(index);
    }

    public static boolean contains(String source, Object pattern, boolean ignoreCase) {
        return contains(source, pattern, 0, ignoreCase);
    }

    public static boolean contains(String source, Object pattern, int startIndex, boolean ignoreCase) {
        if (source == null || source.isEmpty()) return false;
        return indexOf(source, pattern, startIndex, ignoreCase) != -1;
    }

    public static boolean contains(String source, Object... items) {
        return contains(source, List.of(items), 0, true);
    }

    public static boolean contains(String source, List<Object> items, boolean ignoreCase) {
        return contains(source, items, 0, ignoreCase);
    }

    public static boolean contains(String source, List<Object> items, int startIndex, boolean ignoreCase) {
        if (source == null || source.isEmpty()) return false;

        for (Object item : items) {
            if (indexOf(source, item, startIndex, ignoreCase) != -1) {
                return true;
            }
        }
        return false;
    }

    public static <T extends Enum<T>> boolean contains(String source, T flags) {
        if (source == null || source.isEmpty()) return false;

        String flagString = flags.toString();
        if (flagString.contains(",")) {
            String[] flagArray = flagString.split(",");
            for (String flag : flagArray) {
                if (source.toLowerCase().contains(flag.trim().toLowerCase())) {
                    return true;
                }
            }
            return false;
        }

        return source.toLowerCase().contains(flagString.toLowerCase());
    }

    /// EnsureWith

    /**
     * If the string does not start with the specified character, prepend it to the string.
     *
     * @param source the source string
     * @param append the character to ensure at the start
     * @return the modified string
     */
    public static String ensureStart(String source, char append) {
        if (!startsWith(source, append)) {
            source = append + safeString(source);
        }
        return source;
    }

    /**
     * If the string does not start with the specified string, prepend it to the string.
     *
     * @param source      the source string
     * @param append      the string to ensure at the start
     * @param ignoreCase  whether to ignore case when checking
     * @return the modified string
     */
    public static String ensureStart(String source, String append, boolean ignoreCase) {
        if (!startsWith(source, append, ignoreCase)) {
            source = safeString(append) + safeString(source);
        }
        return source;
    }

    /**
     * If the string does not end with the specified character, append it to the string.
     *
     * @param source the source string
     * @param append the character to ensure at the end
     * @return the modified string
     */
    public static String ensureEnd(String source, char append) {
        if (!endsWith(source, append)) {
            source = safeString(source) + append;
        }
        return source;
    }

    /**
     * If the string does not end with the specified string, append it to the string.
     *
     * @param source      the source string
     * @param append      the string to ensure at the end
     * @param ignoreCase  whether to ignore case when checking
     * @return the modified string
     */
    public static String ensureEnd(String source, String append, boolean ignoreCase) {
        if (!endsWith(source, append, ignoreCase)) {
            source = safeString(source) + safeString(append);
        }
        return source;
    }

    /// StartsWith, EndsWith

    public static boolean startsWith(String source, char item) {
        if (source == null || source.isEmpty()) {
            return false;
        }
        return source.charAt(0) == item;
    }

    public static boolean startsWith(String source, String item, boolean ignoreCase) {
        if (source == null || source.isEmpty() || item == null) {
            return false;
        }
        if (ignoreCase) {
            return source.toLowerCase().startsWith(item.toLowerCase());
        }
        return source.startsWith(item);
    }

    public static boolean startsWith(String source, List<Object> items, boolean ignoreCase) {
        if (source == null || source.isEmpty()) {
            return false;
        }
        for (Object item : items) {
            if (startsWith(source, safeString(item), ignoreCase)) {
                return true;
            }
        }
        return false;
    }

    public static boolean startsWith(String source, Object... items) {
        return startsWith(source, List.of(items), true);
    }

    public static boolean endsWith(String source, char item) {
        if (source == null || source.isEmpty()) {
            return false;
        }
        return source.charAt(source.length() - 1) == item;
    }

    public static boolean endsWith(String source, String item, boolean ignoreCase) {
        if (source == null || source.isEmpty() || item == null) {
            return false;
        }
        if (ignoreCase) {
            return source.toLowerCase().endsWith(item.toLowerCase());
        }
        return source.endsWith(item);
    }

    public static boolean endsWith(String source, List<Object> items, boolean ignoreCase) {
        if (source == null || source.isEmpty()) {
            return false;
        }
        for (Object item : items) {
            if (endsWith(source, safeString(item), ignoreCase)) {
                return true;
            }
        }
        return false;
    }

    public static boolean endsWith(String source, Object... items) {
        return endsWith(source, List.of(items), true);
    }

    private static String safeString(Object source) {
        return source == null ? "" : source.toString();
    }

    /// Left, Mid, Right

    public static String left(String source, int count) {
        if (source == null) return "";
        if (count < 0) {
            count = Math.max(source.length() + count, 0);
        }
        if (source.length() < count) {
            return source;
        }
        return source.substring(0, count);
    }

    public static String left(String source, char separator, boolean includeSeparator, boolean ensureNotEmpty) {
        int index = source.indexOf(separator);
        if (index == -1) {
            return ensureNotEmpty ? safeString(source) : "";
        }
        return source.substring(0, index + (includeSeparator ? 1 : 0));
    }

    public static String left(String source, String separator, boolean includeSeparator, boolean ensureNotEmpty) {
        int index = source.indexOf(separator);
        if (index == -1) {
            return ensureNotEmpty ? safeString(source) : "";
        }
        return source.substring(0, index + (includeSeparator ? separator.length() : 0));
    }

    public static String lastLeft(String source, char separator, boolean includeSeparator, boolean ensureNotEmpty) {
        int index = source.lastIndexOf(separator);
        if (index == -1) {
            return ensureNotEmpty ? safeString(source) : "";
        }
        return source.substring(0, index + (includeSeparator ? 1 : 0));
    }

    public static String lastLeft(String source, String separator, boolean includeSeparator, boolean ensureNotEmpty) {
        int index = source.lastIndexOf(separator);
        if (index == -1) {
            return ensureNotEmpty ? safeString(source) : "";
        }
        return source.substring(0, index + (includeSeparator ? separator.length() : 0));
    }

    public static String mid(String source, int length) {
        if (source == null) return "";
        if (length < 0) {
            length = Math.max(source.length() + length, 0);
        }
        if (source.length() > length) {
            source = source.substring(length);
        }
        return source;
    }

    public static String right(String source, int count) {
        if (source == null) return "";
        if (count < 0) {
            count = Math.max(source.length() + count, 0);
        }
        if (source.length() < count) {
            return source;
        }
        return source.substring(source.length() - count);
    }

    public static String right(String source, char separator, boolean includeSeparator, boolean ensureNotEmpty) {
        int index = source.indexOf(separator);
        if (index == -1) {
            return ensureNotEmpty ? safeString(source) : "";
        }
        return source.substring(index + (includeSeparator ? 0 : 1));
    }

    public static String right(String source, String separator, boolean includeSeparator, boolean ensureNotEmpty) {
        int index = source.indexOf(separator);
        if (index == -1) {
            return ensureNotEmpty ? safeString(source) : "";
        }
        return source.substring(index + (includeSeparator ? 0 : separator.length()));
    }

    public static String lastRight(String source, char separator, boolean includeSeparator, boolean ensureNotEmpty) {
        int index = source.lastIndexOf(separator);
        if (index == -1) {
            return ensureNotEmpty ? safeString(source) : "";
        }
        return source.substring(index + (includeSeparator ? 0 : 1));
    }

    public static String lastRight(String source, String separator, boolean includeSeparator, boolean ensureNotEmpty) {
        int index = source.lastIndexOf(separator);
        if (index == -1) {
            return ensureNotEmpty ? safeString(source) : "";
        }
        return source.substring(index + (includeSeparator ? 0 : separator.length()));
    }

    public static String padLeft(String source, int totalWidth, char paddingChar) {
        return padLeft(source, totalWidth, String.valueOf(paddingChar));
    }

    public static String padLeft(String source, int totalWidth, String padding) {
        if (source == null) {
            source = "";
        }
        char filler = (padding == null || padding.isEmpty()) ? ' ' : padding.charAt(0); // Default to SPACE
        source = trimStart(source);
        StringBuilder result = new StringBuilder();

        while (result.length() + source.length() < totalWidth) {
            result.append(filler);
        }
        result.append(source);
        return result.toString();
    }

    public static String padRight(String source, int totalWidth, char paddingChar) {
        return padRight(source, totalWidth, String.valueOf(paddingChar));
    }

    public static String padRight(String source, int totalWidth, String padding) {
        if (source == null) {
            source = "";
        }
        char filler = (padding == null || padding.isEmpty()) ? ' ' : padding.charAt(0); // Default to SPACE
        source = trimEnd(source);
        StringBuilder result = new StringBuilder(source);

        while (result.length() < totalWidth) {
            result.append(filler);
        }
        return result.toString();
    }

    /// Substring, Slice

    public static String substring(String source, int startIndex) {
        if (source == null || source.isEmpty()) {
            return "";
        }
        if (startIndex >= source.length()) {
            return "";
        }
        return source.substring(startIndex);
    }

    public static String substring(String source, int startIndex, int length) {
        if (source == null || source.isEmpty()) {
            return "";
        }
        if (startIndex >= source.length()) {
            return "";
        }
        return source.substring(startIndex, Math.min(startIndex + length, source.length()));
    }

    /**
     * Slices the specified string from start to end.
     *
     * @param source The source string.
     * @param start  The start index (can be negative).
     * @param end    The end index (can be negative).
     * @return The sliced string.
     */
    public static String slice(String source, int start, int end) {
        if (source == null || source.isEmpty()) {
            return "";
        }

        int len = source.length();
        int from = (start < 0) ? Math.max(len + start, 0) : Math.min(start, len);
        int to = (end < 0) ? Math.max(len + end, 0) : Math.min(end, len);
        int span = Math.max(to - from, 0);

        // Check common case: span is 1
        if (span == 1) {
            return String.valueOf(source.charAt(from));
        }

        return source.substring(from, from + span);
    }

    /// Trim

    public static String trim(String source) {
        if (source == null || source.isEmpty()) return "";
        if (Character.isWhitespace(source.charAt(0)) || Character.isWhitespace(source.charAt(source.length() - 1))) {
            return source.trim();
        }
        return source;
    }

    public static String trimStart(String source) {
        if (source == null || source.isEmpty()) return "";
        int startIndex = 0;
        while (startIndex < source.length() && Character.isWhitespace(source.charAt(startIndex))) {
            startIndex++;
        }
        return source.substring(startIndex);
    }

    public static String trimEnd(String source) {
        if (source == null || source.isEmpty()) return "";
        int endIndex = source.length() - 1;
        while (endIndex >= 0 && Character.isWhitespace(source.charAt(endIndex))) {
            endIndex--;
        }
        return source.substring(0, endIndex + 1);
    }

    public static String trimSpace(String source) {
        String result = trim(source);
        if (!result.isEmpty()) {
            result = result.replaceAll("\\s{2,}|\\s", " ");
        }
        return result;
    }

    public static String trimQuote(String source) {
        String result = trim(source);
        if (!result.isEmpty()) {
            result = result.replaceAll("['\"]", "");
        }
        return result;
    }

    public static String trimHtml(String source) {
        if (source == null || source.isEmpty()) return "";

        Pattern htmlPattern = Pattern.compile(
                "(<(head|script|style|select|frameset)[^>]*>(.*?)</\\2[^>]*>|</?[!a-z][a-z0-9:]*[^<>]*>|&nbsp;|&amp;)",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = htmlPattern.matcher(source);

        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            String match = matcher.group();
            if ("&nbsp;".equals(match)) {
                matcher.appendReplacement(result, " ");
            } else if ("&amp;".equals(match)) {
                matcher.appendReplacement(result, "&");
            } else if (match.toLowerCase().startsWith("<br")) {
                matcher.appendReplacement(result, "\r\n");
            } else {
                matcher.appendReplacement(result, "");
            }
        }
        matcher.appendTail(result);
        return result.toString();
    }

    /// remove

    public static String remove(String source, char pattern) {
        if (source == null || source.isEmpty()) {
            return "";
        }
        if (source.indexOf(pattern) == -1) {
            return source;
        }
        return source.replace(String.valueOf(pattern), "");
    }

    public static String remove(String source, String pattern, boolean ignoreCase) {
        if (source == null || pattern == null || source.isEmpty()) {
            return "";
        }
        Pattern regex = ignoreCase ? Pattern.compile(pattern, Pattern.CASE_INSENSITIVE) : Pattern.compile(pattern);
        return regex.matcher(source).replaceAll("");
    }

    public static String removePostfix(String source, String postfix, boolean ignoreCase) {
        if (source == null || source.isEmpty()) {
            return "";
        }
        if (endsWith(source, postfix, ignoreCase)) {
            return source.substring(0, source.length() - postfix.length());
        }
        return source;
    }

    public static String removePostfix(String source, String... postfixes) {
        return removePostfix(source, Arrays.asList(postfixes), true);
    }

    public static String removePostfix(String source, List<String> postfixes, boolean ignoreCase) {
        if (source == null || source.isEmpty()) {
            return "";
        }
        for (String postfix : postfixes) {
            if (endsWith(source, postfix, ignoreCase)) {
                return source.substring(0, source.length() - postfix.length());
            }
        }
        return source;
    }

    public static String removePrefix(String source, String prefix, boolean ignoreCase) {
        if (source == null || source.isEmpty()) {
            return "";
        }
        if (startsWith(source, prefix, ignoreCase)) {
            return source.substring(prefix.length());
        }
        return source;
    }

    public static String removePrefix(String source, String... prefixes) {
        return removePrefix(source, Arrays.asList(prefixes), true);
    }

    public static String removePrefix(String source, List<String> prefixes, boolean ignoreCase) {
        if (source == null || source.isEmpty()) {
            return "";
        }
        for (String prefix : prefixes) {
            if (startsWith(source, prefix, ignoreCase)) {
                return source.substring(prefix.length());
            }
        }
        return source;
    }

}
