package raf.sk_schedule.util.filter.default_filters;

import raf.sk_schedule.api.Constants;
import raf.sk_schedule.util.filter.CriteriaFilter;
import raf.sk_schedule.util.filter.SearchCriteria;
import raf.sk_schedule.model.schedule_node.ScheduleSlot;

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
        Constants.WeekDay dayEnum = null;
        if (searchParam instanceof String)
            dayEnum = Enum.valueOf(Constants.WeekDay.class, searchParam.toString().toUpperCase());
        else if (searchParam instanceof Constants.WeekDay)
            dayEnum = (Constants.WeekDay) searchParam;

        return slot.getDayOfWeek() != dayEnum;
    }
}
