package ae.accumed.lookuprequestsmanager.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.ZoneId;
import java.util.Date;

import static java.time.Month.FEBRUARY;

/**
 * Utility library for working with {@link Date}s.
 */
public final class DateUtils {

    /**
     * February 29th.
     */
    static final MonthDay LEAP_DAY = MonthDay.of(FEBRUARY, 29);

    private DateUtils() {
    }

    /**
     * Returns the {@link Date} of midnight at the start of the given {@link Date}.
     *
     * <p>This returns a {@link Date} formed from the given {@link Date} at the time of midnight,
     * 00:00, at the start of this {@link Date}.
     *
     * @return the {@link Date} of midnight at the start of the given {@link Date}
     */
    public static Date atStartOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return localDateTimeToDate(startOfDay);
    }

    /**
     * Returns the {@link Date} at the end of day of the given {@link Date}.
     *
     * <p>This returns a {@link Date} formed from the given {@link Date} at the time of 1 millisecond
     * prior to midnight the next day.
     *
     * @return the {@link Date} at the end of day of the given {@link Date}j
     */
    public static Date atEndOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return localDateTimeToDate(endOfDay);
    }

    private static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date minusDays(Date date, int days){
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTimeToDate(localDateTime.minusDays(days));
    }

    public static Date plusDays(Date date, int days){
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTimeToDate(localDateTime.plusDays(days));
    }
}