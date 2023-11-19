package raf.sk_schedule.filter;


import raf.sk_schedule.api.Constants.WeekDay;
import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.model.location.RoomProperties;
import raf.sk_schedule.model.schedule.ScheduleSlot;

import java.util.*;

import static raf.sk_schedule.util.format.DateTimeFormatter.*;

/**
 * The `SearchCriteria` class provides a flexible way to filter a list of `ScheduleSlot` instances based on various criteria.
 * Criteria can include date, day of the week, start time, end time, duration, location, and dynamic attributes.
 */
public class SearchCriteria {

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
        supportedFilters[DATE_KEY] = dateFilterHandle;
        supportedFilters[WEEK_DAY_KEY] = weekDayFilterHandle;
        supportedFilters[START_TIME_KEY] = startTimeFilterHandle;
        supportedFilters[END_TIME_KEY] = endTimeFilterHandle;
        supportedFilters[DURATION_KEY] = durationFilterHandle;
        supportedFilters[LOCATION_KEY] = locationFilterHandle;
        supportedFilters[UPPER_BOUND_DATE_KEY] = supportedFilters[DYNAMIC_ATTRIBUTES_KEY] = dynamicAttributesFilterHandle;
    }


    /**
     * Functional interface for implementing custom filters.
     */
    public interface CriteriaFilter {
        boolean filter(ScheduleSlot slot);
    }

    // Criteria filter implementations for default filters


    // Implementation for date filter
    private final CriteriaFilter dateFilterHandle = slot -> {
        Object searchParam = getCriteria(DATE_KEY);
        Date usableDate = null;
        if (searchParam instanceof String)
            usableDate = parseDate(searchParam.toString());
        else if (searchParam instanceof Date)
            usableDate = (Date) searchParam;
        else
            throw new ScheduleException("Search criteria property value query is not supported!");
        return usableDate.getTime() != slot.getDate().getTime();

    };

    // Implementation for week day filter
    private final CriteriaFilter weekDayFilterHandle = slot -> {
        Object searchParam = getCriteria(WEEK_DAY_KEY);
        WeekDay dayEnum = null;
        if (searchParam instanceof String)
            dayEnum = Enum.valueOf(WeekDay.class, searchParam.toString().toUpperCase());
        else if (searchParam instanceof WeekDay)
            dayEnum = (WeekDay) searchParam;

        return slot.getDayOfWeek() != dayEnum;
    };

    // Implementation for start time filter
    private final CriteriaFilter startTimeFilterHandle = slot -> {
        Object searchParam = searchCriteria.get(START_TIME_KEY);
        if (!(searchParam instanceof String))
            throw new ScheduleException("Start time search parameter can be set only as a String!");
        return parseTime(slot.getStartTime()).getTime() < parseTime((String) searchParam).getTime();
    };

    private final CriteriaFilter endTimeFilterHandle = slot -> {
        // Implementation for end time filter
        Object searchParam = searchCriteria.get(END_TIME_KEY);
        if (!(searchParam instanceof String))
            throw new ScheduleException("Start time search parameter can be set only as a String!");
        return parseTime(slot.getEndTime()).getTime() > parseTime((String) searchParam).getTime();
    };
    private final CriteriaFilter durationFilterHandle = slot -> {
        // Implementation for duration filter
        Object searchParam = getCriteria(DURATION_KEY);
        if (!(searchParam instanceof Number))
            throw new ScheduleException("Duration search parameter can be represented only by a numeric value (number)!");
        return slot.getDuration() != (int) searchParam;
    };
    private final CriteriaFilter locationFilterHandle = slot -> {
        // Implementation for location filter
        Object searchParam = getCriteria(LOCATION_KEY);
        if (searchParam instanceof RoomProperties || searchParam instanceof String)
            return !slot.getLocation().equals(searchParam);

        throw new ScheduleException("Location search parameter can be set by stating the name of the room or a RoomProperties class object!");

    };
    private final CriteriaFilter dynamicAttributesFilterHandle = slot -> {
        // Implementation for dynamic attributes filter
        try {
            if (!(getCriteria(DYNAMIC_ATTRIBUTES_KEY) instanceof Map<?, ?> searchParam))
                throw new ScheduleException("Dynamic attributes search parameter can be applied only with Map instance!");

            for (String attributeName : slot.getAttributes().keySet()) {
                if (!searchParam.containsKey(attributeName))
                    return true;
            }
            for (Object filterAttribute : (searchParam.keySet())) {
                if (!slot.hasAttribute((String) filterAttribute)
                        || !slot.getAttribute((String) filterAttribute).equals(searchParam.get(filterAttribute)))
                    return true;
            }
            return false;
        } catch (ClassCastException e) {
            throw new ScheduleException(e.getMessage());
        }
    };

    private final CriteriaFilter lowerBoundFilterHandle = slot -> {
        // Implementation for lower bound filter
        Object searchParam = searchCriteria.get(LOWER_BOUND_DATE_KEY);
        Date lowerBound;
        if (searchParam instanceof String)
            lowerBound = parseDate((String) searchParam);
        else if (searchParam instanceof Date)
            lowerBound = (Date) searchParam;
        else
            throw new ScheduleException("Lower bound search parameter can be set only as String or java.util Date instance!");

        return slot.getDate().getTime() < lowerBound.getTime();
    };

    private final CriteriaFilter upperBoundFilterHandle = slot -> {
        //
        Object searchParam = searchCriteria.get(UPPER_BOUND_DATE_KEY);
        Date upperBound;
        if (searchParam instanceof String)
            upperBound = parseDate((String) searchParam);
        else if (searchParam instanceof Date)
            upperBound = (Date) searchParam;
        else
            throw new ScheduleException("Upper bound search parameter can be set only as String or java.util Date instance!");

        return slot.getDate().getTime() > upperBound.getTime();
    };


    public List<ScheduleSlot> filter(List<ScheduleSlot> schedule) {
        Iterator<ScheduleSlot> iterator = schedule.iterator();
        while (iterator.hasNext()) {
            ScheduleSlot slot = iterator.next();
            for (int filterKey : searchCriteria.keySet()) {
                if (supportedFilters[filterKey].filter(slot)) {
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
        schedule.removeIf(customFilter::filter);
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