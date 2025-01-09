package opsnow.framework.core.system;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 */
public class DateTimeUtil {
    private static final LocalDateTime BASE_UTC_DATE_TIME = LocalDateTime.of(1970, 1, 1, 0, 0);

    private static final ConcurrentHashMap<String, ZoneId> TIMEZONE_MAP = new ConcurrentHashMap<>();

    // RuleValidation Methods

    public static boolean isDate(Object source) {
        return tryParseDateTime(source) != null;
    }

    public static boolean isValid(LocalDateTime source) {
        return source != null;
    }

    public static boolean isLeapYear(LocalDateTime source) {
        return Year.of(source.getYear()).isLeap();
    }

    public static boolean isWeekDay(LocalDateTime source) {
        DayOfWeek day = source.getDayOfWeek();
        return day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
    }

    public static boolean isWeekend(LocalDateTime source) {
        DayOfWeek day = source.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    public static boolean isToday(LocalDateTime source) {
        return source.toLocalDate().equals(LocalDate.now());
    }

    public static boolean isFutureDay(LocalDateTime source) {
        return source.isAfter(LocalDateTime.now());
    }

    public static boolean isPastDay(LocalDateTime source) {
        return source.isBefore(LocalDateTime.now());
    }

    // Conversion Methods

    public static LocalDateTime tryParseDateTime(Object source) {
        if (source instanceof LocalDateTime) {
            return (LocalDateTime) source;
        } else if (source instanceof String) {
            return toDateTime((String) source);
        } else if (source instanceof Double) {
            return toDateTime((Double) source);
        } else if (source instanceof Long) {
            return toDateTime((long) source);
        }
        return null;
    }

    public static LocalDateTime toDateTime(String source) {
        try {
            return LocalDateTime.parse(source, DateTimeFormatter.ISO_DATE_TIME);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static LocalDateTime toDateTime(Double millis) {
        if (millis == null || millis.isNaN()) {
            return null;
        }
        return Instant.ofEpochMilli(millis.longValue())
                .atZone(ZoneId.of("UTC"))
                .toLocalDateTime();
    }

    public static LocalDateTime toDateTime(long timestamp) {
        return Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.of("UTC"))
                .toLocalDateTime();
    }

    public static double toJsTimeValue(LocalDateTime source) {
        if (source == null) {
            return Double.NaN;
        }
        Instant instant = source.atZone(ZoneId.of("UTC")).toInstant();
        return instant.toEpochMilli();
    }

    public static long toUnixTimestamp(LocalDateTime source) {
        if (source == null) {
            return 0L;
        }
        Instant instant = source.atZone(ZoneId.of("UTC")).toInstant();
        return instant.getEpochSecond();
    }

    public static LocalDateTime fromUnixTimestamp(long timestamp) {
        return Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    // DateTime Utility Methods

    public static LocalDateTime startOfDay(LocalDateTime source) {
        return source.toLocalDate().atStartOfDay();
    }

    public static LocalDateTime endOfDay(LocalDateTime source) {
        return source.toLocalDate().atTime(23, 59, 59);
    }

    public static LocalDateTime addWeeks(LocalDateTime source, int weeks) {
        return source.plusWeeks(weeks);
    }

    public static LocalDateTime firstDayOfMonth(LocalDateTime source) {
        return source.withDayOfMonth(1);
    }

    public static LocalDateTime lastDayOfMonth(LocalDateTime source) {
        return source.withDayOfMonth(source.toLocalDate().lengthOfMonth());
    }

    public static int daysInMonth(LocalDateTime source) {
        return source.toLocalDate().lengthOfMonth();
    }

    public static int daysLeftInMonth(LocalDateTime source) {
        return daysInMonth(source) - source.getDayOfMonth();
    }

    public static String toString(LocalDateTime source, String format) {
        return source.format(DateTimeFormatter.ofPattern(format));
    }

    public static String toShortString(LocalDateTime source, String format, Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, locale);
        return source.format(formatter);
    }

    public static ZoneId toTimeZone(String source) {
        return TIMEZONE_MAP.computeIfAbsent(source, tz -> {
            try {
                return ZoneId.of(tz);
            } catch (Exception e) {
                return ZoneId.systemDefault();
            }
        });
    }

    public static LocalDateTime convertToTimeZone(LocalDateTime source, String targetZone) {
        ZoneId target = toTimeZone(targetZone);
        return source.atZone(ZoneId.systemDefault()).withZoneSameInstant(target).toLocalDateTime();
    }
}
