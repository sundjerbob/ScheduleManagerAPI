package raf.sk_schedule.util.filter.default_filters;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.util.filter.CriteriaFilter;
import raf.sk_schedule.util.filter.SearchCriteria;
import raf.sk_schedule.model.schedule_node.ScheduleSlot;

import static raf.sk_schedule.util.date_formater.DateTimeFormatter.parseTime;

/**
 * A default implementation of {@link CriteriaFilter} for filtering based on end time criteria.
 */
public class EndTimeFilter implements CriteriaFilter {

    /**
     * Filters a schedule slot based on the specified end time criteria.
     *
     * @param slot           The schedule slot to be filtered.
     * @param searchCriteria The criteria used for filtering.
     * @return True if the slot's end time is later than the specified end time criteria, false otherwise.
     * @throws ScheduleException If the end time search parameter is not a String.
     */
    @Override
    public boolean filter(ScheduleSlot slot, SearchCriteria searchCriteria) {
        // Implementation for end time filter
        Object searchParam = searchCriteria.getCriteria(UPPER_BOUND_TIME_KEY);
        if (!(searchParam instanceof String))
            throw new ScheduleException("Start time search parameter can be set only as a String!");
        return parseTime(slot.getEndTime()).getTime() > parseTime((String) searchParam).getTime();
    }
}