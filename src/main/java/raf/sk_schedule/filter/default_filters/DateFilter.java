package raf.sk_schedule.filter.default_filters;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.filter.CriteriaFilter;
import raf.sk_schedule.filter.SearchCriteria;
import raf.sk_schedule.model.schedule.ScheduleSlot;

import java.util.Date;

import static raf.sk_schedule.util.format.DateTimeFormatter.parseDate;

public class DateFilter implements CriteriaFilter {
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
