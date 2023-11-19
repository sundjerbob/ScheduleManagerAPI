package raf.sk_schedule.filter.default_filters;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.filter.CriteriaFilter;
import raf.sk_schedule.filter.SearchCriteria;
import raf.sk_schedule.model.schedule.ScheduleSlot;

import java.util.Date;

import static raf.sk_schedule.util.format.DateTimeFormatter.parseDate;

public class UpperBoundDateFilter implements CriteriaFilter {

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
