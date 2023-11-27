package raf.sk_schedule.util.filter.default_filters;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.util.filter.CriteriaFilter;
import raf.sk_schedule.util.filter.SearchCriteria;
import raf.sk_schedule.model.location_node.RoomProperties;
import raf.sk_schedule.model.schedule_node.ScheduleSlot;


/**
 * A default implementation of {@link CriteriaFilter} for filtering based on location criteria.
 */
public class LocationFilter implements CriteriaFilter {

    /**
     * Filters a schedule slot based on the specified location criteria.
     *
     * @param slot           The schedule slot to be filtered.
     * @param searchCriteria The criteria used for filtering.
     * @return True if the slot's location does not match the specified location criteria, false otherwise.
     * @throws ScheduleException If the location search parameter is not a RoomProperties or String.
     */
    @Override
    public boolean filter(ScheduleSlot slot, SearchCriteria searchCriteria) {
        // Implementation for location filter
        Object searchParam = searchCriteria.getCriteria(LOCATION_KEY);
        if (searchParam instanceof RoomProperties)
            return !slot.getLocation().equals(searchParam);
        if ( searchParam instanceof String)
            return !slot.getLocation().getName().equals((String) searchParam);

        throw new ScheduleException("Location search parameter can be set by stating the name of the room or a RoomProperties class object!");
    }
}
