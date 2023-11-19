package raf.sk_schedule.filter.default_filters;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.filter.CriteriaFilter;
import raf.sk_schedule.filter.SearchCriteria;
import raf.sk_schedule.model.schedule.ScheduleSlot;

public class DurationFilter implements CriteriaFilter {
    @Override
    public boolean filter(ScheduleSlot slot, SearchCriteria searchCriteria) {
        // Implementation for duration filter
        Object searchParam = searchCriteria.getCriteria(DURATION_KEY);
        if (!(searchParam instanceof Number))
            throw new ScheduleException("Duration search parameter can be represented only by a numeric value (number)!");
        return slot.getDuration() != (int) searchParam;
    }
}
