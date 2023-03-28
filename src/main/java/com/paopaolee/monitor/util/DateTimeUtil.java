package com.paopaolee.monitor.util;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author paopaolee
 */
public class DateTimeUtil {
    private static LocalTime parse(String time, String pattern) {
        final DateTimeFormatter dft = DateTimeFormatter.ofPattern(pattern);
        return LocalTime.parse(time, dft);
    }

    public static LocalTime getLocalTimeNormalPattern(String time) {
        return parse(time, "HH:mm:ss");
    }

    public static String formattedDateTime(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static Date ConvertLocalDateTime2Date(final LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        final ZoneId zoneId = ZoneId.systemDefault();
        final ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        return Date.from(zonedDateTime.toInstant());
    }

    public static Date[] getDateRange(String startTime, String endTime) {
        final LocalDate curDate = LocalDate.now();
        LocalTime localStartTime = getLocalTimeNormalPattern(startTime);
        LocalTime localEndTime = getLocalTimeNormalPattern(endTime);
        LocalDateTime start = LocalDateTime.of(curDate, localStartTime);
        LocalDateTime end = LocalDateTime.of(curDate, localEndTime);
        return new Date[] {
                ConvertLocalDateTime2Date(start),
                ConvertLocalDateTime2Date(end)
        };
    }
}
