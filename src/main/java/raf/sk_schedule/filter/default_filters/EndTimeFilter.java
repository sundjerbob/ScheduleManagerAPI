package raf.sk_schedule.filter.default_filters;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.filter.CriteriaFilter;
import raf.sk_schedule.filter.SearchCriteria;
import raf.sk_schedule.model.schedule.ScheduleSlot;

import static raf.sk_schedule.util.format.DateTimeFormatter.parseTime;

public class EndTimeFilter implements CriteriaFilter {
    @Override
    public boolean filter(ScheduleSlot slot, SearchCriteria searchCriteria) {
        // Implementation for end time filter
        Object searchParam = searchCriteria.getCriteria(END_TIME_KEY);
        if (!(searchParam instanceof String))
            throw new ScheduleException("Start time search parameter can be set only as a String!");
        return parseTime(slot.getEndTime()).getTime() > parseTime((String) searchParam).getTime();
    }
}
