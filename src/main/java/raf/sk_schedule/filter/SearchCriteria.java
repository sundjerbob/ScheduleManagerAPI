package raf.sk_schedule.filter;


import raf.sk_schedule.api.Constants.WeekDay;
import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.filter.default_filters.*;
import raf.sk_schedule.model.location.RoomProperties;
import raf.sk_schedule.model.schedule.ScheduleSlot;

import java.util.*;

import static raf.sk_schedule.filter.CriteriaFilter.*;
import static raf.sk_schedule.util.format.DateTimeFormatter.*;

/**
 * The `SearchCriteria` class provides a flexible way to filter a list of `ScheduleSlot` instances based on various criteria.
 * Criteria can include date, day of the week, start time, end time, duration, location, and dynamic attributes.
 */
public class SearchCriteria {


    // number of supported handles
    private static final int SUPPORTED_FILTERS = 9;

    private Map<Integer, Object> searchCriteria;

    private final CriteriaFilter[] supportedFilters;


    /**
     * Private constructor to create an instance of `SearchCriteria`.
     *
     * @param searchCriteria A map containing criteria for filtering.
     */
    private SearchCriteria(Map<Integer, Object> searchCriteria) {


        this.searchCriteria = searchCriteria;
        supportedFilters = new CriteriaFilter[SUPPORTED_FILTERS];
        supportedFilters[DATE_KEY] = new DateFilter();
        supportedFilters[WEEK_DAY_KEY] = new WeekDayFilter();
        supportedFilters[START_TIME_KEY] = new StartTimeFilter();
        supportedFilters[END_TIME_KEY] = new EndTimeFilter();
        supportedFilters[DURATION_KEY] = new DurationFilter();
        supportedFilters[LOCATION_KEY] = new LocationFilter();
        supportedFilters[DYNAMIC_ATTRIBUTES_KEY] = new DynamicAttributesFilter();
        supportedFilters[LOWER_BOUND_DATE_KEY] = new LowerBoundDateFilter();
        supportedFilters[UPPER_BOUND_DATE_KEY] = new UpperBoundDateFilter();
    }


    public List<ScheduleSlot> filter(List<ScheduleSlot> schedule) {
        Iterator<ScheduleSlot> iterator = schedule.iterator();
        while (iterator.hasNext()) {
            ScheduleSlot slot = iterator.next();
            for (int filterKey : searchCriteria.keySet()) {
                if (supportedFilters[filterKey].filter(slot, this)) {
                    iterator.remove();  //  iterators remove method so we dont get get
                    break;  //  remove the slot once for any matching filter so break
                }
            }
        }
        return schedule;
    }


    /**
     * Filters a list of `ScheduleSlot` instances based on the set criteria.
     *
     * @param schedule The list of `ScheduleSlot` instances to be filtered.
     * @return The filtered list of `ScheduleSlot` instances.
     */
    public List<ScheduleSlot> filter(List<ScheduleSlot> schedule, CriteriaFilter customFilter) {
        schedule.removeIf(scheduleSlot -> customFilter.filter(scheduleSlot, this));
        return schedule;
    }


    /**
     * Checks if a specific criteria is set.
     *
     * @param criteriaKey The key representing the criteria.
     * @return `true` if the criteria is set, otherwise `false`.
     */
    public boolean hasCriteria(int criteriaKey) {
        return searchCriteria.containsKey(criteriaKey);
    }

    /**
     * Gets the value of a specific criteria.
     *
     * @param criteriaKey The key representing the criteria.
     * @return The value of the criteria.
     */
    public Object getCriteria(int criteriaKey) {
        return searchCriteria.get(criteriaKey);
    }

    /**
     * Sets the value of a specific criteria.
     *
     * @param criteriaKey   The key representing the criteria.
     * @param criteriaValue The value to set for the criteria.
     */
    public void setCriteria(int criteriaKey, Object criteriaValue) {
        searchCriteria.put(criteriaKey, criteriaValue);
    }

    /**
     * Checks if a dynamic attribute with a specific name is set.
     *
     * @param attributeName The name of the dynamic attribute.
     * @return `true` if the dynamic attribute is set, otherwise `false`.
     */
    public boolean hasDynamicAttribute(String attributeName) {
        try {
            return searchCriteria.containsKey(DYNAMIC_ATTRIBUTES_KEY)
                    && ((Map<?, ?>) searchCriteria.get(DYNAMIC_ATTRIBUTES_KEY)).containsKey(attributeName);
        } catch (ClassCastException e) {
            throw new ScheduleException(
                    e.getMessage()
                            + "\nDynamic attributes search parameter value expected to be instance of java.util.Map!"
            );
        }
    }

    /**
     * Gets the value of a dynamic attribute with a specific name.
     *
     * @param attributeName The name of the dynamic attribute.
     * @return The value of the dynamic attribute.
     */
    public Object getDynamicAttribute(String attributeName) {
        try {
            if (searchCriteria.containsKey(DYNAMIC_ATTRIBUTES_KEY))
                return ((Map<?, ?>) searchCriteria.get(DYNAMIC_ATTRIBUTES_KEY)).get(attributeName);

            return null;
        } catch (ClassCastException e) {
            throw new ScheduleException(e.getMessage() + "\nDynamic attributes search parameter value expected to be instance of java.util.Map!");
        }
    }

    /**
     * Builder class for creating an instance of `SearchCriteria`.
     */
    public static class Builder {
        private final Map<Integer, Object> searchCriteria;

        /**
         * Constructor for the builder.
         */
        public Builder() {
            this.searchCriteria = new HashMap<>();
        }

        /**
         * Sets a criteria with a specific key and value.
         *
         * @param criteriaKey   The key representing the criteria.
         * @param criteriaValue The value to set for the criteria.
         * @return The builder instance.
         */
        public Builder setCriteria(int criteriaKey, Object criteriaValue) {
            searchCriteria.put(criteriaKey, criteriaValue);
            return this;
        }

        /**
         * Sets dynamic attributes with a map of attribute names and values.
         *
         * @param attributes The map of dynamic attributes.
         * @return The builder instance.
         */
        public Builder setDynamicAttributes(Map<String, Object> attributes) {
            searchCriteria.put(DYNAMIC_ATTRIBUTES_KEY, attributes);
            return this;
        }

        /**
         * Builds an instance of `SearchCriteria` with the set criteria.
         *
         * @return The created `SearchCriteria` instance.
         */
        public SearchCriteria build() {

            return new SearchCriteria(this.searchCriteria);
        }
    }
}