package raf.sk_schedule.util.filter.default_filters;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.util.filter.CriteriaFilter;
import raf.sk_schedule.util.filter.SearchCriteria;
import raf.sk_schedule.model.schedule_node.ScheduleSlot;

import static raf.sk_schedule.util.formater.DateTimeFormatter.parseTime;


/**
 * A default implementation of {@link CriteriaFilter} for filtering based on start time criteria.
 */
public class StartTimeFilter implements CriteriaFilter {


    /**
     * Filters a schedule slot based on the specified start time criteria.
     *
     * @param slot           The schedule slot to be filtered.
     * @param searchCriteria The criteria used for filtering.
     * @return True if the slot's start time is earlier than the specified start time, false otherwise.
     * @throws ScheduleException If the start time search parameter is not a String.
     */
    @Override
    public boolean filter(ScheduleSlot slot, SearchCriteria searchCriteria) {

        Object searchParam = searchCriteria.getCriteria(START_TIME_KEY);
        if (!(searchParam instanceof String))
            throw new ScheduleException("Start time search parameter can be set only as a String!");
        return parseTime(slot.getStartTime()).getTime() < parseTime((String) searchParam).getTime();
    }
}
