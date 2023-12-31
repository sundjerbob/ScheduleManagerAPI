package raf.sk_schedule.util.date_formater;

import raf.sk_schedule.exception.ScheduleException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static raf.sk_schedule.api.Constants.*;

/**
 * Utility class for formatting and parsing date and time within the scheduling component.
 */
public class DateTimeFormatter {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);

    /**
     * Formats a Date object to a string using the specified date format.
     *
     * @param date The Date object to be formatted.
     * @return A string representing the formatted date.
     */
    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    /**
     * Parses a string into a Date object using the specified date format.
     *
     * @param date The string representing the date to be parsed.
     * @return The parsed Date object.
     * @throws ScheduleException If the date string is not in the expected format.
     */
    public static Date parseDate(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            throw new ScheduleException(" The value: " + date + " couldn't be parsed. The universal format for date within schedule component is: " + DATE_FORMAT + " !");
        }
    }

    /**
     * Formats a Date object to a string using the specified time format.
     *
     * @param date The Date object to be formatted.
     * @return A string representing the formatted time.
     */
    public static String formatTime(Date date) {
        return timeFormat.format(date);
    }

    /**
     * Parses a string into a {@code Date} object representing the time using the specified time format.
     * <p>
     * Note: The returned {@code Date} object will be set to the date of 1st January 1970 if the parsed string contains only
     * time information without an actual date. This behavior is due to the default parsing of {@code java.util.Date} when
     * only the time of the day is provided without an explicit date. Therefore, it is recommended to use this method
     * when dealing with scenarios where only the time of the day is relevant, and the comparison of times is needed as if
     * they all belong to the same date (1st January 1970).
     * </p>
     *
     * @param time The string representing the time to be parsed.
     * @return The parsed {@code Date} object.
     * @throws ScheduleException If the time string is not in the expected format.
     */
    public static Date parseTime(String time) {
        try {
            return timeFormat.parse(time);
        } catch (ParseException e) {
            throw new ScheduleException(" The value: " + time + " couldn't be parsed. The universal format for time within schedule component is: " + TIME_FORMAT + " !");
        }
    }

    /**
     * Formats a Date object to a string using the specified date and time format.
     *
     * @param date The Date object to be formatted.
     * @return A string representing the formatted date and time.
     */
    public static String formatDateTime(Date date) {
        return dateTimeFormat.format(date);
    }

    /**
     * Parses a string into a Date object using the specified date and time format.
     *
     * @param dateAndTime The string representing the date and time to be parsed.
     * @return The parsed Date object.
     * @throws ScheduleException If the date and time string is not in the expected format.
     */
    public static Date parseDateTime(String dateAndTime) {
        try {
            return dateTimeFormat.parse(dateAndTime);
        } catch (ParseException e) {
            throw new ScheduleException(" The value: " + dateAndTime + " couldn't be parsed. The universal format for date & time within schedule component is: " + DATE_TIME_FORMAT + " !");

        }
    }
}
