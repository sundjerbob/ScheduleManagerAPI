package raf.sk_schedule.filter;

import raf.sk_schedule.api.Constants;
import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.model.location.RoomProperties;
import raf.sk_schedule.model.schedule.ScheduleSlot;

import java.util.Date;
import java.util.Map;

import static raf.sk_schedule.util.format.DateTimeFormatter.parseDate;
import static raf.sk_schedule.util.format.DateTimeFormatter.parseTime;

/**
 * Functional interface for implementing custom filters.
 */
public interface CriteriaFilter {

    // Constants for criteria keys
    public static final int DATE_KEY = 0;
    public static final int WEEK_DAY_KEY = 1;
    public static final int START_TIME_KEY = 3;
    public static final int END_TIME_KEY = 4;
    public static final int DURATION_KEY = 2;
    public static final int LOCATION_KEY = 5;
    public static final int DYNAMIC_ATTRIBUTES_KEY = 6;
    public static final int UPPER_BOUND_DATE_KEY = 7;
    public static final int LOWER_BOUND_DATE_KEY = 8;

    boolean filter(ScheduleSlot slot, SearchCriteria searchCriteria);




}