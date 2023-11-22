package raf.sk_schedule.util.filter.default_filters;

import raf.sk_schedule.api.Constants;
import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.util.filter.CriteriaFilter;
import raf.sk_schedule.util.filter.SearchCriteria;
import raf.sk_schedule.model.schedule_node.ScheduleSlot;

import java.util.List;

/**
 * A default implementation of {@link CriteriaFilter} for filtering based on week day criteria.
 */
public class WeekDayFilter implements CriteriaFilter {

    /**
     * Filters a schedule slot based on the specified week day criteria.
     *
     * @param slot           The schedule slot to be filtered.
     * @param searchCriteria The criteria used for filtering.
     * @return True if the slot's day of the week does not match the specified week day, false otherwise.
     */
    @Override
    public boolean filter(ScheduleSlot slot, SearchCriteria searchCriteria) {
        Object searchParam = searchCriteria.getCriteria(WEEK_DAY_KEY);

        if (!(searchParam instanceof List<?> days))
            throw new ScheduleException("WeekDay search parameter must be set as a list of acceptable days or the filtering can not be performed!");

        boolean filterOut = true;

        for (Object day : days) {
            if (!(day instanceof Constants.WeekDay))
                throw new ScheduleException("WeekDay search parameter must be set as a list of acceptable days or the filtering can not be performed!");
            if (slot.getDayOfWeek() == (Constants.WeekDay) day) {
                filterOut = false;
                break;
            }
        }
        return filterOut;
    }

}
