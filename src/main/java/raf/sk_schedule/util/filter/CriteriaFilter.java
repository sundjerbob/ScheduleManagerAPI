package raf.sk_schedule.util.filter;

import raf.sk_schedule.model.schedule_node.ScheduleSlot;


/**
 * Functional interface for implementing custom filters.
 */
@FunctionalInterface
public interface CriteriaFilter {

    /**
     * Constants representing keys for default supported criteria.
     */
    int DATE_KEY = 0;
    int WEEK_DAY_KEY = 1;
    int START_TIME_KEY = 3;
    int END_TIME_KEY = 4;
    int DURATION_KEY = 2;
    int LOCATION_KEY = 5;
    int DYNAMIC_ATTRIBUTES_KEY = 6;
    int UPPER_BOUND_DATE_KEY = 7;
    int LOWER_BOUND_DATE_KEY = 8;

    /**
     * Filters a schedule slot based on the specified criteria.
     *
     * @param slot           The schedule slot to be filtered.
     * @param searchCriteria The criteria used for filtering.
     * @return True if the slot satisfies the criteria, false otherwise.
     */
    boolean filter(ScheduleSlot slot, SearchCriteria searchCriteria);
}