package raf.sk_schedule.util.filter.default_filters;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.util.filter.CriteriaFilter;
import raf.sk_schedule.util.filter.SearchCriteria;
import raf.sk_schedule.model.schedule_node.ScheduleSlot;

/**
 * A default implementation of {@link CriteriaFilter} for filtering based on duration criteria.
 */
public class DurationFilter implements CriteriaFilter {
    /**
     * Filters a schedule slot based on the specified duration criteria.
     *
     * @param slot           The schedule slot to be filtered.
     * @param searchCriteria The criteria used for filtering.
     * @return True if the slot's duration does not match the specified duration criteria, false otherwise.
     * @throws ScheduleException If the duration search parameter is not represented by a numeric value.
     */
    @Override
    public boolean filter(ScheduleSlot slot, SearchCriteria searchCriteria) {
        // Implementation for duration filter
        Object searchParam = searchCriteria.getCriteria(DURATION_KEY);
        if (!(searchParam instanceof Number))
            throw new ScheduleException("Duration search parameter can be represented only by a numeric value (number)!");
        return slot.getDuration() != (int) searchParam;
    }
}
