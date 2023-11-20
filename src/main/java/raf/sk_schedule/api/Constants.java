package raf.sk_schedule.api;

/**
 * This class should not be instanced nor extended.
 * Its purpose is to hold all top-level constants that are used by schedule api.
 * All the constants are made public and can be accessed (are visible) outside the component.
 * Since there is no "sealed" keyword like in c#, we made constructor private.
 */
public abstract class Constants {

    private Constants() {
    }

    /**
     * The universal date format of this time scheduling api is yyyy-mm-dd for example: "2023-10-14"
     */
    public static String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * The universal time format of this time scheduling api is HH:MM for example: "04:20"
     */
    public static String TIME_FORMAT = "HH:mm";

    /**
     * The universal date format of this time scheduling api is yyyy-mm-dd for example: "2023-10-14"
     */
    public static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * Scheduling component uses this enumeration as the correctly ordered list of all days in a week.
     * WeekDay can be used by name or the index.
     */
    public enum WeekDay {
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY

    }
}
