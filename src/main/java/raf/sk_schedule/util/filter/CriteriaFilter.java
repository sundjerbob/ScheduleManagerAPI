package raf.sk_schedule.util.filter;

import raf.sk_schedule.model.schedule_node.ScheduleSlot;


/**
 * Functional interface for implementing custom filters.
 * <p>Supported keys:</p>
 * <ul>
 *     <li>{@link CriteriaFilter#DATE_KEY}</li>
 *     <li>{@link CriteriaFilter#WEEK_DAY_KEY}</li>
 *     <li>{@link CriteriaFilter#LOWER_BOUND_TIME_KEY}</li>
 *     <li>{@link CriteriaFilter#UPPER_BOUND_TIME_KEY}</li>
 *     <li>{@link CriteriaFilter#DURATION_KEY}</li>
 *     <li>{@link CriteriaFilter#LOCATION_KEY}</li>
 *     <li>{@link CriteriaFilter#DYNAMIC_ATTRIBUTES_KEY}</li>
 *     <li>{@link CriteriaFilter#UPPER_BOUND_DATE_KEY}</li>
 *     <li>{@link CriteriaFilter#LOWER_BOUND_DATE_KEY}</li>
 * </ul>
 */
@FunctionalInterface
public interface CriteriaFilter {

    /**
     * Constant representing the key for the date criteria.
     */
    int DATE_KEY = 0;

    /**
     * Constant representing the key for the week day criteria.
     */
    int WEEK_DAY_KEY = 1;

    /**
     * Constant representing the key for the earliest time bound to look for.
     */
    int LOWER_BOUND_TIME_KEY = 3;

    /**
     * Constant representing the key for the latest time bound to look for.
     */
    int UPPER_BOUND_TIME_KEY = 4;

    /**
     * Constant representing the key for the duration criteria.
     */
    int DURATION_KEY = 2;

    /**
     * Constant representing the key for the location criteria.
     */
    int LOCATION_KEY = 5;

    /**
     * Constant representing the key for the dynamic attributes criteria.
     */
    int DYNAMIC_ATTRIBUTES_KEY = 6;

    /**
     * Constant representing the key for the upper bound date criteria.
     */
    int UPPER_BOUND_DATE_KEY = 7;

    /**
     * Constant representing the key for the lower bound date criteria.
     */
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