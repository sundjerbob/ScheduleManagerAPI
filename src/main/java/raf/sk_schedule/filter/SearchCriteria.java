package raf.sk_schedule.filter;


import raf.sk_schedule.exception.ScheduleException;

import java.util.HashMap;
import java.util.Map;

public class SearchCriteria {
    private Map<String, Object> searchCriteria;

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
            if(searchCriteria.isEmpty())
                throw new ScheduleException("Criteria descriptions is empty.");
            return new SearchCriteria(this.searchCriteria);
        }
    }
}