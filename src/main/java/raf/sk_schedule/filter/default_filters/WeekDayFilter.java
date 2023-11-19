package raf.sk_schedule.filter.default_filters;

import raf.sk_schedule.api.Constants;
import raf.sk_schedule.filter.CriteriaFilter;
import raf.sk_schedule.filter.SearchCriteria;
import raf.sk_schedule.model.schedule.ScheduleSlot;

public class WeekDayFilter implements CriteriaFilter {
    @Override
    public boolean filter(ScheduleSlot slot, SearchCriteria searchCriteria) {
        Object searchParam = searchCriteria.getCriteria(WEEK_DAY_KEY);
        Constants.WeekDay dayEnum = null;
        if (searchParam instanceof String)
            dayEnum = Enum.valueOf(Constants.WeekDay.class, searchParam.toString().toUpperCase());
        else if (searchParam instanceof Constants.WeekDay)
            dayEnum = (Constants.WeekDay) searchParam;

        return slot.getDayOfWeek() != dayEnum;
    }
}
