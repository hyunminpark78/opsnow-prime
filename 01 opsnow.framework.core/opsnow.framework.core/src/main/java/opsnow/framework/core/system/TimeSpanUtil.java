package opsnow.framework.core.system;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 *         /// String                          en-US
 *         /// -------------------------------------
 *         /// 6                          6.00:00:00
 *         /// 6:12                         06:12:00
 *         /// 6:12:14                      06:12:14
 *         /// 6:12:14:45                 6.12:14:45
 *         /// 6.12:14:45                 6.12:14:45
 *         /// 6:12:14:45.3448    6.12:14:45.3448000
 *         /// 6:12:14:45,3448       Unable to Parse
 *         /// 6:34:14:45            Unable to Parse
 *         ///
 *         /// Number                          en-US
 *         /// -------------------------------------
 *         /// 30                   00:00:00.0300000
 *         /// 90 * 1000                    00:01:30
 */
public class TimeSpanUtil {
    public enum TimeSince {
        Years, Year, Months, Month, Weeks, Week, Days, Yesterday, Hours, Hour, Minutes, Minute, Seconds
    }

    // Converts an object to Duration
    public static Duration toDuration(Object source, Duration defaultVal) {
        if (source instanceof Duration) {
            return (Duration) source;
        }

        if (source instanceof String) {
            String timeVal = (String) source;
            if (timeVal.isEmpty()) {
                return defaultVal;
            }
            try {
                return Duration.parse("PT" + timeVal.replace(":", "H") + "M");
            } catch (Exception e) {
                // Handle invalid format
            }
        }

        try {
            long milliseconds = Long.parseLong(source.toString());
            return Duration.ofMillis(milliseconds);
        } catch (Exception e) {
            // Handle invalid conversion
        }

        return defaultVal;
    }

    // Get the number of years in a Duration
    public static long getYears(Duration duration) {
        return duration.toDays() / 365;
    }

    // Get the number of months in a Duration
    public static long getMonths(Duration duration) {
        return (duration.toDays() % 365) / 30;
    }

    // Get the remaining days in a Duration
    public static long getDaysRemainder(Duration duration) {
        return duration.toDays() % 30;
    }

    // Convert Duration to a human-readable string
    public static String durationToString(Duration duration) {
        List<String> parts = new ArrayList<>();

        long years = getYears(duration);
        if (years > 0) {
            parts.add(years + " year" + (years > 1 ? "s" : ""));
        }

        long months = getMonths(duration);
        if (months > 0) {
            parts.add(months + " month" + (months > 1 ? "s" : ""));
        }

        long days = getDaysRemainder(duration);
        if (days > 0) {
            parts.add(days + " day" + (days > 1 ? "s" : ""));
        }

        long hours = duration.toHours() % 24;
        if (hours > 0) {
            parts.add(hours + " hour" + (hours > 1 ? "s" : ""));
        }

        long minutes = duration.toMinutes() % 60;
        if (minutes > 0) {
            parts.add(minutes + " minute" + (minutes > 1 ? "s" : ""));
        }

        long seconds = duration.getSeconds() % 60;
        if (seconds > 0) {
            parts.add(seconds + " second" + (seconds > 1 ? "s" : ""));
        }

        return String.join(", ", parts);
    }

    // Convert Duration to a plain English representation
    public static String timeSince(Duration duration, Function<TimeSince, String> translator) {
        if (translator == null) {
            throw new IllegalArgumentException("Translator function cannot be null");
        }

        if (getYears(duration) > 1) {
            return getYears(duration) + " " + translator.apply(TimeSince.Years);
        } else if (getYears(duration) == 1) {
            return "1 " + translator.apply(TimeSince.Year);
        } else if (getMonths(duration) > 1) {
            return getMonths(duration) + " " + translator.apply(TimeSince.Months);
        } else if (getMonths(duration) == 1) {
            return "1 " + translator.apply(TimeSince.Month);
        } else if (duration.toDays() / 7 > 1) {
            return (duration.toDays() / 7) + " " + translator.apply(TimeSince.Weeks);
        } else if (duration.toDays() / 7 == 1) {
            return "1 " + translator.apply(TimeSince.Week);
        } else if (duration.toDays() > 1) {
            return duration.toDays() + " " + translator.apply(TimeSince.Days);
        } else if (duration.toDays() == 1) {
            return "1 " + translator.apply(TimeSince.Yesterday);
        } else if (duration.toHours() > 1) {
            return duration.toHours() + " " + translator.apply(TimeSince.Hours);
        } else if (duration.toHours() == 1) {
            return "1 " + translator.apply(TimeSince.Hour);
        } else if (duration.toMinutes() > 1) {
            return duration.toMinutes() + " " + translator.apply(TimeSince.Minutes);
        } else if (duration.toMinutes() == 1) {
            return "1 " + translator.apply(TimeSince.Minute);
        } else {
            return duration.getSeconds() + " " + translator.apply(TimeSince.Seconds);
        }
    }
}
