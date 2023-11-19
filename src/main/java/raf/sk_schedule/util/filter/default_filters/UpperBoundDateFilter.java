package raf.sk_schedule.util.filter.default_filters;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.util.filter.CriteriaFilter;
import raf.sk_schedule.util.filter.SearchCriteria;
import raf.sk_schedule.model.schedule_node.ScheduleSlot;

import java.util.Date;

import static raf.sk_schedule.util.date_formater.DateTimeFormatter.parseDate;

/**
 * A default implementation of {@link CriteriaFilter} for filtering based on upper bound date criteria.
 */
public class UpperBoundDateFilter implements CriteriaFilter {

    /**
     * Filters a schedule slot based on the specified upper bound date criteria.
     *
     * @param slot           The schedule slot to be filtered.
     * @param searchCriteria The criteria used for filtering.
     * @return True if the slot's date is later than the specified upper bound date, false otherwise.
     * @throws ScheduleException If the upper bound date search parameter is not a String or java.util Date instance.
     */
    @Override
    public boolean filter(ScheduleSlot slot, SearchCriteria searchCriteria) {
        Object searchParam = searchCriteria.getCriteria(UPPER_BOUND_DATE_KEY);
        Date upperBound;
        if (searchParam instanceof String)
            upperBound = parseDate((String) searchParam);
        else if (searchParam instanceof Date)
            upperBound = (Date) searchParam;
        else
            throw new ScheduleException("Upper bound search parameter can be set only as String or java.util Date instance!");

        return slot.getDate().getTime() > upperBound.getTime();
    }
}
