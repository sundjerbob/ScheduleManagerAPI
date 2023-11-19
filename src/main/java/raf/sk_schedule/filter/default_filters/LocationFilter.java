package raf.sk_schedule.filter.default_filters;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.filter.CriteriaFilter;
import raf.sk_schedule.filter.SearchCriteria;
import raf.sk_schedule.model.location.RoomProperties;
import raf.sk_schedule.model.schedule.ScheduleSlot;

public class LocationFilter implements CriteriaFilter {
    @Override
    public boolean filter(ScheduleSlot slot, SearchCriteria searchCriteria) {
        // Implementation for location filter
        Object searchParam = searchCriteria.getCriteria(LOCATION_KEY);
        if (searchParam instanceof RoomProperties || searchParam instanceof String)
            return !slot.getLocation().equals(searchParam);

        throw new ScheduleException("Location search parameter can be set by stating the name of the room or a RoomProperties class object!");
    }
}
