package raf.sk_schedule.filter.default_filters;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.filter.CriteriaFilter;
import raf.sk_schedule.filter.SearchCriteria;
import raf.sk_schedule.model.schedule.ScheduleSlot;

import java.util.Map;

public class DynamicAttributesFilter implements CriteriaFilter {
    @Override
    public boolean filter(ScheduleSlot slot, SearchCriteria searchCriteria) {
        // Implementation for dynamic attributes filter
        try {
            if (!(searchCriteria.getCriteria(DYNAMIC_ATTRIBUTES_KEY) instanceof Map<?, ?> searchParam))
                throw new ScheduleException("Dynamic attributes search parameter can be applied only with Map instance!");

            for (String attributeName : slot.getAttributes().keySet()) {
                if (!searchParam.containsKey(attributeName))
                    return true;
            }
            for (Object filterAttribute : (searchParam.keySet())) {
                if (!slot.hasAttribute((String) filterAttribute)
                        || !slot.getAttribute((String) filterAttribute).equals(searchParam.get(filterAttribute)))
                    return true;
            }
            return false;
        } catch (ClassCastException e) {
            throw new ScheduleException(e.getMessage());
        }

    }
}
