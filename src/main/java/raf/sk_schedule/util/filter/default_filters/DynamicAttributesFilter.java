package raf.sk_schedule.util.filter.default_filters;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.util.filter.CriteriaFilter;
import raf.sk_schedule.util.filter.SearchCriteria;
import raf.sk_schedule.model.schedule_node.ScheduleSlot;

import java.util.Map;

/**
 * A default implementation of {@link CriteriaFilter} for filtering based on dynamic attributes criteria.
 */
public class DynamicAttributesFilter implements CriteriaFilter {

    /**
     * Filters a schedule slot based on the specified dynamic attributes criteria.
     *
     * @param slot           The schedule slot to be filtered.
     * @param searchCriteria The criteria used for filtering.
     * @return True if the slot's attributes do not match the specified dynamic attributes criteria, false otherwise.
     * @throws ScheduleException If the dynamic attributes search parameter is not a Map instance.
     */
    @Override
    public boolean filter(ScheduleSlot slot, SearchCriteria searchCriteria) {
        // Implementation for dynamic attributes filter
        try {
            if (!(searchCriteria.getCriteria(DYNAMIC_ATTRIBUTES_KEY) instanceof Map<?, ?> searchParam))
                throw new ScheduleException("Dynamic attributes search parameter can be applied only with Map instance!");
            for (Object attribute : searchParam.keySet()) {
                if (!slot.hasAttribute(attribute.toString()))
                    return true;
                else if (!slot.getAttribute(attribute.toString()).equals(searchParam.get(attribute)))
                    return true;
            }
            return false;
        } catch (ClassCastException e) {
            throw new ScheduleException(e.getMessage());
        }

    }
}
