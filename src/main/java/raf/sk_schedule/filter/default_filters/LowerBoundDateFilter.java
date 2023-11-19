package raf.sk_schedule.filter.default_filters;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.filter.CriteriaFilter;
import raf.sk_schedule.filter.SearchCriteria;
import raf.sk_schedule.model.schedule.ScheduleSlot;

import java.util.Date;

import static raf.sk_schedule.util.format.DateTimeFormatter.parseDate;

public class LowerBoundDateFilter implements CriteriaFilter {
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
