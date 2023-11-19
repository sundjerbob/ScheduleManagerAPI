package raf.sk_schedule.util.filter.default_filters;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.util.filter.CriteriaFilter;
import raf.sk_schedule.util.filter.SearchCriteria;
import raf.sk_schedule.model.schedule_node.ScheduleSlot;

import java.util.Date;

import static raf.sk_schedule.util.date_formater.DateTimeFormatter.parseDate;

/**
 * A default implementation of {@link CriteriaFilter} for filtering based on lower bound date criteria.
 */
public class LowerBoundDateFilter implements CriteriaFilter {
    /**
     * Filters a schedule slot based on the specified lower bound date criteria.
     *
     * @param slot           The schedule slot to be filtered.
     * @param searchCriteria The criteria used for filtering.
     * @return True if the slot's date is earlier than the specified lower bound date, false otherwise.
     * @throws ScheduleException If the lower bound search parameter is not a String or java.util Date instance.
     */
    @Override
    public boolean filter(ScheduleSlot slot, SearchCriteria searchCriteria) {
        // Implementation for lower bound filter
        Object searchParam = searchCriteria.getCriteria(LOWER_BOUND_DATE_KEY);
        Date lowerBound;
        if (searchParam instanceof String)
            lowerBound = parseDate((String) searchParam);
        else if (searchParam instanceof Date)
            lowerBound = (Date) searchParam;
        else
            throw new ScheduleException("Lower bound search parameter can be set only as String or java.util Date instance!");

        return slot.getDate().getTime() < lowerBound.getTime();
    }
}
