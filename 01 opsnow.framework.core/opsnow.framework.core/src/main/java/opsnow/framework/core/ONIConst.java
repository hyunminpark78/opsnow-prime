package opsnow.framework.core;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
public class ONIConst {
    // 기본 상수
    public static final long GIGA_BYTE = 1024 * 1024 * 1024;
    public static final long MEGA_BYTE = 1024 * 1024;
    public static final long KIRO_BYTE = 1024;

    // 문자열 상수
    public static final String YES = "Y";
    public static final String NO = "N";
    public static final String TRUE = "T";
    public static final String FALSE = "F";

    // 인코딩
    public static final java.nio.charset.Charset ANSIEncoding = StandardCharsets.ISO_8859_1;
    public static final java.nio.charset.Charset UTF8NoBomEncoding = java.nio.charset.StandardCharsets.UTF_8;

    // 구분자
    public static final char Delimiter = '∬';
    public static final char[] DelimiterPathChars = { '\\', '/' };
    public static final char[] DelimiterChars = { ',', ';', '&' };
    public static final char[] QuoteChars = { '\'', '"' };
    public static final char[] TrimChars = { ' ', '\t', '\r', '\n' };

    // Constant Types
    public static final Class<?> TypeType = Class.class;
    public static final Class<?> ObjectType = Object.class;
    public static final Class<?> StringType = String.class;
    public static final Class<?> CharType = Character.class;
    public static final Class<?> BoolType = Boolean.class;
    public static final Class<?> IntType = Integer.class;
    public static final Class<?> ShortType = Short.class;
    public static final Class<?> LongType = Long.class;
    public static final Class<?> UIntType = Long.class; // Java does not have unsigned types
    public static final Class<?> UShortType = Integer.class; // Java does not have unsigned types
    public static final Class<?> ULongType = Long.class; // Java uses BigInteger for unsigned long
    public static final Class<?> DoubleType = Double.class;
    public static final Class<?> FloatType = Float.class;
    public static final Class<?> DecimalType = BigDecimal.class;
    public static final Class<?> DateType = Date.class;
    public static final Class<?> TimeSpanType = Duration.class;
    public static final Class<?> GuidType = UUID.class;
    public static final Class<?> DBNullType = Void.class;
    public static final Class<?> NullableType = Optional.class;
    public static final Class<?> DictionaryType = Map.class;
    public static final Class<?> GenericDictionaryType = Map.class;
    public static final Class<?> NameValueCollType = Properties.class;
    public static final Class<?> ExceptionType = Exception.class;
    //public static final Class<?> ConvertibleType = IConvertible.class; // This would need a custom interface
    public static final Class<?> EnumerableType = Iterable.class;
    public static final Class<?> EnumeratorType = Iterator.class;
    public static final Class<?> CollectionType = Collection.class;
    public static final Class<?> ArrayType = Object[].class;
    public static final Class<?> VoidType = void.class;
    public static final Class<?> ByteArrayType = byte[].class;

    // Empty Arrays
    //public static final Member[] EmptyMemberInfos = new Member[0];
    //public static final Parameter[] EmptyParameterInfos = new Parameter[0];
    public static final Object[] EmptyObjectArray = new Object[0];
    public static final String[] EmptyStringArray = new String[0];
    public static final byte[] EmptyByteArray = new byte[0];
    public static final Class<?>[] EmptyTypeArray = new Class<?>[0];

    // Empty Object
    public static final Object EmptyObject = new Object();

    // 미디어 타입
    public static final String MultiPartByteRangeMediaType = "multipart/byteranges";
    public static final String MultiPartFormDataMediaType = "multipart/form-data";
    public static final String ApplicationFormUrlEncodedMediaType = "application/x-www-form-urlencoded";
    public static final String ApplicationOctetStreamMediaType = "application/octet-stream";
    public static final String ApplicationXmlMediaType = "application/xml";
    public static final String ApplicationJsonMediaType = "application/json";
    public static final String ApplicationJavascriptMediaType = "application/x-javascript";
    public static final String ApplicationExcelMediaType = "application/vnd.ms-excel";
    public static final String TextXmlMediaType = "text/xml";
    public static final String TextJsonMediaType = "text/json";
    public static final String TextHtmlMediaType = "text/html";
    public static final String TextPlainMediaType = "text/plain";

    // 프록시 헤더
    public static final String X_TRANSFER_TO = "X-TRANSFER-TO";
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";
    public static final String X_HOST = "Host";

    // 문자열 상수
    public static final String TENANT_SID = "TENANT_SID";
    public static final String USER_SID = "USER_SID";
    public static final String COND_TYPE = "COND_TYPE";

    // 정규 표현식 패턴

    /**
     * Avoid null reference exception for Regex.
     */
    public static final Pattern NULL_REGEX = Pattern.compile("^$");

    /**
     * Checks the string contains any resolve tag (e.g., ${userid}).
     */
    public static final Pattern RESOLVE_REGEX = Pattern.compile("\\$\\{(?<name>[^=}\\s]+)(=(?<default>[^}]+))?}");

    /**
     * Checks the string contains unicode.
     */
    public static final Pattern UNICODE_REGEX = Pattern.compile("[^\\u0000-\\u007F]");

    /**
     * The encode url64.
     */
    public static final Pattern ENCODE_URL64_REGEX = Pattern.compile("[\\/\\=\\+]");

    /**
     * The decode url64.
     */
    public static final Pattern DECODE_URL64_REGEX = Pattern.compile("[\\-\\s_]");

    /**
     * Checks the string contains only alphabets. [a-zA-Z\-_]
     */
    public static final Pattern ALPHA_ONLY_REGEX = Pattern.compile("^[a-zA-Z\\-_]+$");

    /**
     * Checks the string contains alphabets. [a-zA-Z]
     */
    public static final Pattern ALPHA_CHAR_REGEX = Pattern.compile("[a-zA-Z]");

    /**
     * Checks the string contains only alphabets and number start with alphabet. [a-zA-Z][\w\-\.]
     */
    public static final Pattern ALPHA_NUMERIC_ONLY_REGEX = Pattern.compile("^[a-zA-Z][\\w\\-\\.]+$");

    /**
     * Checks the string contains only alphabets and number start with alphabet. [a-zA-Z][\w\-\.]
     */
    public static final Pattern ALPHA_NUMERIC_REGEX = Pattern.compile("[a-zA-Z][\\w\\-\\.]+");

    /**
     * Checks the string contains alphabets and number. [a-zA-Z0-9]
     */
    public static final Pattern ALPHA_DIGIT_ONLY_REGEX = Pattern.compile("^[a-zA-Z0-9]+$");

    /**
     * Checks the string contains alphabets and number. [a-zA-Z0-9]
     */
    public static final Pattern ALPHA_DIGIT_REGEX = Pattern.compile("[a-zA-Z0-9]+");

    /**
     * Checks the string contains only numeric. -?\d+(\.\d+)?
     */
    public static final Pattern NUMERIC_ONLY_REGEX = Pattern.compile("^(?<digit>-?\\d+)(\\.(?<scale>\\d*))?$");

    /**
     * Checks the string contains numeric. -?\d+(\.\d+)?
     */
    public static final Pattern NUMERIC_REGEX = Pattern.compile("(?<digit>-?\\d+)(\\.(?<scale>\\d*))?");

    /**
     * Checks the string contains only digit. -?[\d]+
     */
    public static final Pattern DIGIT_ONLY_REGEX = Pattern.compile("^-?\\d+$");

    /**
     * Checks the string contains digit. -?[\d]+
     */
    public static final Pattern DIGIT_REGEX = Pattern.compile("-?\\d+");

    /**
     * Checks the string contains only digit list separated by commas (,). -?[\d]+
     */
    public static final Pattern DIGIT_LIST_REGEX = Pattern.compile("^-?\\d+((,\\s*-?\\d+)+)?$");

    /**
     * Checks the string contains only hex digit. [\dA-F]+
     */
    public static final Pattern HEX_DIGIT_ONLY_REGEX = Pattern.compile("^([\\dA-F]{2,4}|[\\dA-F]{6})$", Pattern.CASE_INSENSITIVE);

    /**
     * Checks the string contains hex digit. [\dA-F]+
     */
    public static final Pattern HEX_DIGIT_REGEX = Pattern.compile("([\\dA-F]{2,4}|[\\dA-F]{6})", Pattern.CASE_INSENSITIVE);

    /**
     * Checks the string contains only white-space. [\s\t\r\n]
     */
    public static final Pattern WHITESPACE_REGEX = Pattern.compile("^[\\s\\t\\r\\n]+$");

    /**
     * Checks the string contains only word (letters, digits, underscore). \w+
     */
    public static final Pattern WORD_ONLY_REGEX = Pattern.compile("^\\w+$");

    /**
     * Checks the string contains word (letters, digits, underscore). \w+
     */
    public static final Pattern WORD_REGEX = Pattern.compile("\\w+");

    /**
     * Checks the string contains carriage return. [\r?\n]
     */
    public static final Pattern CRLF_REGEX = Pattern.compile("(\\r?\\n|\\r)");

    /**
     * Checks the string contains carriage return. [\r?\n]
     */
    public static final Pattern CRLF_TAB_REGEX = Pattern.compile("[\\r\\n\\t]");

    /**
     * Checks the string contains any URL encoded string.
     */
    public static final Pattern URL_HEX_REGEX = Pattern.compile("\\%(\\d[a-fA-F]|u[a-fA-F0-9]{4})");

    /**
     * Checks the string contains only boolean. (true|false|yes|no|y|n|0|1)
     */
    public static final Pattern BOOLEAN_REGEX = Pattern.compile("^(true|false|yes|no|y|n|0|1)$", Pattern.CASE_INSENSITIVE);

    /**
     * Checks the string contains any path.
     */
    public static final Pattern PATH_REGEX = Pattern.compile("(?<path>([a-zA-Z]\\:\\\\|\\\\\\\\[\\w\\.\\-]+\\\\)(?<dir>[\\w\\.\\-]+\\\\)*)(?<file>[\\w\\.\\- ]+\\.[\\w]+)?");

    /**
     * Checks the string contains any invalid character for path.
     */
    public static final Pattern INVALID_PATH_CHARS_REGEX = Pattern.compile("[\\\\\\/\\?:\\*\"<>|]");

    /**
     * Checks the string contains any invalid character for name.
     */
    public static final Pattern INVALID_NAME_CHARS_REGEX = Pattern.compile("[_\\\\\\/\\.\\-\\s]");

    /**
     * Checks the string contains valid email address.
     */
    public static final Pattern EMAIL_REGEX = Pattern.compile("^((?<account>[\\w\\-.]+)@(?<domain>([\\w\\-]+\\.)+[a-zA-Z]{2,})|[\\\"'](?<display>[^\\\"']+)[\\\"']\\s*<(?<account2>[\\w\\-.]+)@(?<domain2>([\\w\\-]+\\.)+[a-zA-Z]{2,})>)$");

    /**
     * Checks the string contains valid URL address.
     */
    public static final Pattern URL_REGEX = Pattern.compile("^(?<protocol>http|https|ftp)\\://(?<host>([\\w\\-]+\\.)+[a-zA-Z]{2,})(\\:(?<port>\\d+))?(/(?<path>.*))?$");

    /**
     * Checks the string contains valid IP address.
     */
    public static final Pattern IP_ADDRESS_REGEX = Pattern.compile("^[1-9]\\d{1,2}\\.\\d{1,3}\\.\\d{1,3}\\.[1-9]\\d{0,2}$");

    /**
     * Checks the string contains valid domain name.
     */
    public static final Pattern DOMAIN_REGEX = Pattern.compile("^([\\w\\-]+\\.)+[a-zA-Z]{2,}$");

    /**
     * Checks the string contains valid currency.
     */
    public static final Pattern CURRENCY_REGEX = Pattern.compile("^\\p{Sc}?(?<dollor>(\\d{1,3},)+\\d{3}|\\d+)(\\.(?<cent>\\d{1,2}))?$");

    /**
     * Checks the string contains any HTML tag.
     */
    public static final Pattern HTML_TAG_REGEX = Pattern.compile("<(?<tag>/?[a-zA-Z]+)[^>]*>", Pattern.DOTALL);

    /**
     * Checks the string contains any SQL injection string.
     */
    public static final Pattern SQL_INJECT_REGEX = Pattern.compile("(^\\d+\\s+(or|and)\\s+\\d+|\\w+';?\\s*--|@@[a-zA-Z_]+|(exec|execute)(\\s+@\\w+|\\s*\\(|\\s+[\\w\\.]+\\.?(xp|sp|sys|dt|r)_)|(set|declare)\\s+@\\w+|(openquery|opendatasource|openrowset)\\s*\\(|union\\s+(all\\s+)?select|select\\s+([a-zA-Z_]+\\s*\\(|.+?from)|dbo\\.[\\w\\.]+)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

    /**
     * Checks the string contains quotation marks.
     */
    public static final Pattern QUOTATION_REGEX = Pattern.compile("('|\")");

    /**
     * Checks the string contains any resolve tag.
     */
    public static final Pattern ECOUNT_TAG_REGEX = Pattern.compile("\\[(?<name>ecount\\d+)\\]", Pattern.CASE_INSENSITIVE);

    /**
     * Checks the string contains any resource format.
     */
    public static final Pattern RESOURCE_REGEX = Pattern.compile("resx\\d{6}", Pattern.CASE_INSENSITIVE);

    /**
     * Checks the string contains consecutive lower and upper case characters like camel case.
     */
    public static final Pattern CAMEL_CASE_REGEX = Pattern.compile("(\\p{Ll})(\\p{Lu})");
}
