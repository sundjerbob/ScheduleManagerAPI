package raf.sk_schedule.filter;


import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.model.schedule.ScheduleSlot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchCriteria {

    public static String WEEK_DAY_KEY = "day_of_week";

    public static String LOCATION_KEY = "location";

    public static String TIME_START_KEY = "time_start";

    public static String TIME_END_KEY = "time_end";

    public static String AFTER_DATE_KEY = "after_date";

    public static String BEFORE_DATE_KEY = "before_date";

    public static String DURATION_KEY = "duration";


    private Map<String, Object> searchCriteria;


    public List<ScheduleSlot> filter(List<ScheduleSlot> schedule) {

        for (ScheduleSlot curr : schedule) {
            if (searchCriteria.containsKey(WEEK_DAY_KEY)) {

            }

        }
        return null;
    }

    private SearchCriteria(Map<String, Object> searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public Object getCriteria(String criteriaName) {
        return searchCriteria.get(criteriaName);
    }

    public void setCriteria(String criteriaName, Object criteriaValue) {
        searchCriteria.put(criteriaName, criteriaValue);
    }

    public static class Builder {
        private Map<String, Object> searchCriteria;

        public Builder() {
            this.searchCriteria = new HashMap<>();
        }

        public Builder setCriteria(String criteriaName, Object criteriaValue) {
            searchCriteria.put(criteriaName, criteriaValue);
            return this;
        }

        public SearchCriteria build() {
            if (searchCriteria.isEmpty())
                throw new ScheduleException("Criteria descriptions is empty.");
            return new SearchCriteria(this.searchCriteria);
        }
    }
}