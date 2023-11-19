package raf.sk_schedule.util.filter.default_filters;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.util.filter.CriteriaFilter;
import raf.sk_schedule.util.filter.SearchCriteria;
import raf.sk_schedule.model.schedule_node.ScheduleSlot;

import java.util.Date;

import static raf.sk_schedule.util.date_formater.DateTimeFormatter.parseDate;

/**
 * A default implementation of {@link CriteriaFilter} for filtering based on date criteria.
 */
public class DateFilter implements CriteriaFilter {

    /**
     * Filters a schedule slot based on the specified date criteria.
     *
     * @param slot           The schedule slot to be filtered.
     * @param searchCriteria The criteria used for filtering.
     * @return True if the slot's date does not match the specified date criteria, false otherwise.
     * @throws ScheduleException If the search criteria property value query is not supported.
     */
    @Override
    public boolean filter(ScheduleSlot slot, SearchCriteria searchCriteria) {
        Object searchParam = searchCriteria.getCriteria(DATE_KEY);
        Date usableDate = null;
        if (searchParam instanceof String)
            usableDate = parseDate(searchParam.toString());
        else if (searchParam instanceof Date)
            usableDate = (Date) searchParam;
        else
            throw new ScheduleException("Search criteria property value query is not supported!");
        return usableDate.getTime() != slot.getDate().getTime();
    }
}
