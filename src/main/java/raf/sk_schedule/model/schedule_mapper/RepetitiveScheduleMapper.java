package raf.sk_schedule.model.schedule_mapper;

import raf.sk_schedule.api.Constants.WeekDay;
import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.model.location_node.RoomProperties;
import raf.sk_schedule.model.schedule_node.ScheduleSlot;

import java.util.*;

import static raf.sk_schedule.util.date_formater.DateTimeFormatter.parseDate;

/**
 * The `RepetitiveScheduleMapper` class is responsible for creating a set of linked `ScheduleSlot` instances
 * with shared attributes but different time occurrences based on recurrence settings.
 * Each instance of this class represents a set of linked slots with the same attributes and differing in time.
 */
public class RepetitiveScheduleMapper {

    // Default difference between linked slots is 7 days, meaning weekly occurrence
    public static final int DEFAULT_RECURRENCE_PERIOD = WeekDay.values().length;

    // Fields defining the recurrence settings
    private Date recurrenceIntervalStart;
    private Date recurrenceIntervalEnd;
    private int recurrencePeriod;
    private WeekDay weekDay;

    // Shared attributes for all linked slots
    private String startTime;
    private String endTime;
    private int duration;
    private RoomProperties location;
    private Map<String, Object> attributes;

    // List to hold all linked slots with shared attributes
    private List<ScheduleSlot> linkedSlotInstances;

    // Private constructor to enforce the use of the Builder pattern
    private RepetitiveScheduleMapper(
            Date recurrenceIntervalStart,
            Date recurrenceIntervalEnd,
            int recurrencePeriod,
            String startTime,
            String endTime,
            int duration,
            WeekDay weekDay
    ) {
        this.recurrenceIntervalStart = recurrenceIntervalStart;
        this.recurrenceIntervalEnd = recurrenceIntervalEnd;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.recurrencePeriod = recurrencePeriod;
        this.weekDay = weekDay;
    }

    /**
     * Maps the time occurrences of linked slots based on recurrence settings.
     * The mapped time instances are structured as a list of java util Date objects,
     * from witch the time related data can be easily extracted.
     *
     * @return A list of dates representing the time occurrences of linked slots.
     */
    private List<Date> mapSlotReoccurrences() {

        Calendar calendar = Calendar.getInstance();
        List<Date> slotOccurrences = new ArrayList<>();
        long oneDayMills = 1000 * 60 * 60 * 24; // Milliseconds in a day

        if (weekDay != null) {
            // Find the first occurrence of the weekDay from the beginning of the reoccurrences interval
            for (long time = recurrenceIntervalStart.getTime(); time < recurrenceIntervalEnd.getTime(); time += oneDayMills) {
                Date date = new Date(time);
                calendar.setTime(date);

                if (WeekDay.values()[calendar.get(Calendar.DAY_OF_WEEK) - 1] == this.weekDay) {
                    slotOccurrences.add(date);
                    break;
                }
            }
        } else {
            // If there is no recurrence day specified, use the recurrenceIntervalStart as the first occurrence
            slotOccurrences.add(recurrenceIntervalStart);
        }

        // SlotOccurrences should have one element by this time...
        if (slotOccurrences.isEmpty()) {
            throw new ScheduleException("No slot occurrences found.");
        }

        // Time difference between each linked slot is the number of days (recurrencePeriod) multiplied by the milliseconds in one day
        long deltaTimeMills = recurrencePeriod > 0 ? (long) recurrencePeriod * oneDayMills : DEFAULT_RECURRENCE_PERIOD;

        // Finding all the slot occurrences starting from the first occurrence since we already had found that
        for (long time = slotOccurrences.get(0).getTime(); time < this.recurrenceIntervalEnd.getTime(); time += deltaTimeMills) {
            slotOccurrences.add(new Date(time));
        }

        return slotOccurrences;
    }


    /**
     * Maps the repetitive occurrences of a time slot based on the configured parameters
     * and returns a list of ScheduleSlot instances representing the mapped occurrences.
     *
     * @return A list of ScheduleSlot instances representing the mapped occurrences.
     */
    public List<ScheduleSlot> mapSchedule() {
        List<ScheduleSlot> mappedSchedule = new ArrayList<>();

        for (Date date : mapSlotReoccurrences()) {
            mappedSchedule.add(new ScheduleSlot.Builder()
                    .setDate(date)
                    .setStartTime(startTime)
                    .setEndTime(endTime)
                    .setDuration(duration)
                    .setLocation(location)
                    .setAttributes(attributes)
                    .build());
        }

        // Set the value of the linkedSlotInstances field as the list of mapped slots
        linkedSlotInstances = mappedSchedule;

        return linkedSlotInstances;
    }

    private void syncSharedState() {
        for (ScheduleSlot linkedSlot : linkedSlotInstances) {
            linkedSlot.handleSharedStatePropagation();
        }
    }

    public void addLinkedSlot(ScheduleSlot linkedSlot) {
        linkedSlotInstances.add(linkedSlot);
    }


    // Getters for recurrence settings
    public Date getRecurrenceIntervalEnd() {
        return recurrenceIntervalEnd;
    }

    public Date getRecurrenceIntervalStart() {
        return recurrenceIntervalStart;
    }

    public int getRecurrencePeriod() {
        return recurrencePeriod;
    }

    public WeekDay getWeekDay() {
        return weekDay;
    }


    // Setters for recurrence settings
    public void setRecurrenceIntervalStart(Date intervalEnd) {
        recurrenceIntervalStart = intervalEnd;
    }

    public void setRecurrenceIntervalEnd(Date intervalEnd) {
        recurrenceIntervalEnd = intervalEnd;
    }

    public void setRecurrenceIntervalStart(String intervalStart) {
        recurrenceIntervalStart = parseDate(intervalStart);
    }

    public void setRecurrenceIntervalEnd(String intervalEnd) {
        recurrenceIntervalStart = parseDate(intervalEnd);
    }

    public void setRecurrencePeriod(int recurrencePeriod) {
        this.recurrencePeriod = recurrencePeriod;
    }

    public void setWeekDay(WeekDay weekDay) {
        this.weekDay = weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = Enum.valueOf(WeekDay.class, weekDay);
    }


    // Getters for fields that are shared fields values among the all mapped slots
    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getDuration() {
        return duration;
    }

    public RoomProperties getLocation() {
        return location;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public Object getAttribute(String attributeName) {
        return attributes.get(attributeName);
    }

    public boolean hasAttribute(String attributeName) {
        return attributes.containsKey(attributeName);
    }

    // Setters for fields that are shared fields values among the all mapped slots
    public void setStartTime(String startTime) {
        this.startTime = startTime;
        syncSharedState();
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
        syncSharedState();
    }

    public void setLocation(RoomProperties location) {
        this.location = location;
        syncSharedState();
    }

    public void setDuration(int duration) {
        this.duration = duration;
        syncSharedState();
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
        syncSharedState();
    }

    public void setAttribute(String attributeName, String attributeValue) {
        attributes.put(attributeName, attributeValue);
        syncSharedState();
    }

    public void removeAttribute(String attributeName) {
        attributes.remove(attributeName);
    }


    /**
     * The `Builder` class is responsible for constructing instances of the `RepetitiveScheduleMapper` class
     * with the desired settings.
     */
    public static class Builder {
        private Date recurrenceIntervalStart;
        private Date recurrenceIntervalEnd;
        private int recurrencePeriod;
        private String startTime;
        private String endTime;
        private int duration;
        private WeekDay weekDay;

        /**
         * Default constructor initializes default mappers fields values.
         */
        public Builder() {
            recurrenceIntervalStart = null;
            recurrenceIntervalEnd = null;
            recurrencePeriod = DEFAULT_RECURRENCE_PERIOD; // Default delta is weekly or every 7 days
            startTime = null;
            endTime = null;
            duration = 0;
            weekDay = null;
        }

        /**
         * Sets the end of the recurrence interval.
         *
         * @param recurrenceIntervalEnd The end of the recurrence interval.
         * @return The builder instance for method chaining.
         */
        public Builder setRecurrenceIntervalEnd(Date recurrenceIntervalEnd) {
            this.recurrenceIntervalEnd = recurrenceIntervalEnd;
            return this;
        }

        /**
         * Sets the start of the recurrence interval.
         *
         * @param recurrenceIntervalStart The start of the recurrence interval.
         * @return The builder instance for method chaining.
         */
        public Builder setRecurrenceIntervalStart(Date recurrenceIntervalStart) {
            this.recurrenceIntervalStart = recurrenceIntervalStart;
            return this;
        }

        /**
         * Sets the recurrence period.
         *
         * @param recurrencePeriod The recurrence period.
         * @return The builder instance for method chaining.
         */
        public Builder setRecurrencePeriod(int recurrencePeriod) {
            this.recurrencePeriod = recurrencePeriod;
            return this;
        }

        /**
         * Sets the week day for recurrence.
         *
         * @param weekDay The week day for recurrence.
         * @return The builder instance for method chaining.
         */
        public Builder setWeekDay(WeekDay weekDay) {
            this.weekDay = weekDay;
            return this;
        }

        /**
         * Sets the day for recurrence using the day index.
         *
         * @param dayIndex The day index for recurrence.
         * @return The builder instance for method chaining.
         */
        public Builder setDay(int dayIndex) {
            weekDay = WeekDay.values()[++dayIndex];
            return this;
        }

        /**
         * Sets the day for recurrence using the day name.
         *
         * @param dayName The day name for recurrence.
         * @return The builder instance for method chaining.
         */
        public Builder setDay(String dayName) {
            weekDay = WeekDay.valueOf(dayName);
            return this;
        }

        /**
         * Sets the time of the day when the time frame starts.
         *
         * @param startTime The day name for recurrence.
         * @return The builder instance for method chaining.
         */
        public Builder setStartTime(String startTime) {
            this.startTime = startTime;
            return this;
        }

        /**
         * Sets the time of the day when the time frame starts.
         *
         * @param endTime The day name for recurrence.
         * @return The builder instance for method chaining.
         */
        public Builder setEndTime(String endTime) {
            this.endTime = endTime;
            return this;
        }

        /**
         * Sets the duration of the time frame in minutes.
         *
         * @param duration The duration of the time frame in minutes.
         * @return The builder instance for method chaining.
         */
        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        /**
         * Builds an instance of the `RepetitiveScheduleMapper` class with the specified settings.
         *
         * @return An instance of `RepetitiveScheduleMapper`.
         */
        public RepetitiveScheduleMapper build() {
            return new RepetitiveScheduleMapper(
                    recurrenceIntervalStart,
                    recurrenceIntervalEnd,
                    recurrencePeriod,
                    startTime,
                    endTime,
                    duration,
                    weekDay);
        }
    }
}
