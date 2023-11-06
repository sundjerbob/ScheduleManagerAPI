package raf.sk_schedule.model;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.util.exporter.ScheduleExporter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static raf.sk_schedule.api.ScheduleManager.dateTimeFormat;

/**
 * This class represents a universal time slot within the scheduling component.
 */
public class ScheduleSlot {


    private static final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(dateTimeFormat);

    /**
     * Date and time when the time slot starts.
     */
    private Date start;

    /**
     * Duration of the time slot in minutes.
     */
    private long duration;

    /**
     * Location (room) where the time slot is scheduled.
     */
    private RoomProperties location;

    /**
     * Additional attributes associated with the time slot.
     */
    private Map<String, Object> attributes;

    private ScheduleSlot(Date start, long duration, RoomProperties location, Map<String, Object> attributes) {
        this.start = start;
        this.duration = duration;
        this.location = location;
        this.attributes = attributes;
    }

    /**
     * Checks if this time slot is colliding with another time slot.
     *
     * @param otherSlot The other time slot to check for collisions.
     * @return True if there's a collision, false otherwise.
     * @throws ParseException If there is an issue parsing the time slots.
     */
    public boolean isCollidingWith(ScheduleSlot otherSlot) throws ParseException {

        //  collision cases for [1] {2}          //
        //  1.start >= 2.start >= 2.end >= 1.end // [ {-\\-} ]
        //  1.start >= 2.start >= 1.end >= 2.end // [ {-\\-] }
        //  2.start >= 1.start >= 1.end >= 2.end // { [-\\-] }
        //  2.start >= 1.start >= 2.end >= 1.end // { [-\\-} ]

        return
                (this.getStart().getTime() <= otherSlot.getStart().getTime() && otherSlot.getStart().getTime() < this.getEnd().getTime())
                        ||
                        (otherSlot.getStart().getTime() <= this.getStart().getTime() && this.getStart().getTime() < otherSlot.getEnd().getTime());

    }


    /**
     * Calculate the time difference between this time slot and another time slot.
     *
     * @param otherSlot The other time slot for the calculation.
     * @return Time difference in minutes.
     * @throws ParseException If there is an issue parsing the time slots.
     */
    public long calculateTimeDifference(ScheduleSlot otherSlot) throws ParseException {

        if (isCollidingWith(otherSlot))
            return 0; // Return 0 if there is collision


        if (this.getStart().getTime() < otherSlot.getStart().getTime())
            // returns positive number
            return otherSlot.getStart().getTime() - this.getEnd().getTime();

        else if (otherSlot.getStart().getTime() < this.getStart().getTime())
            // returns negative number
            return otherSlot.getEnd().getTime() - this.getStart().getTime();

        throw new ScheduleException("Calculate difference between schedule slots failed.");

    }

    public String getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        String[] daysOfWeek = new String[]{
                "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
        };

        return daysOfWeek[dayOfWeek - 1];
    }

    public Date getStart() {
        return start;
    }


    public Date getEnd() {
        return new Date(start.getTime() + duration * 60 * 1000);
    }

    public String getStartAsString() throws ParseException {
        return dateTimeFormatter.format(start) + ' ' + start.getDay();

    }

    public String getEndAsString() throws ParseException {
        return dateTimeFormatter.format(getEnd());
    }

    public long getDuration() {
        return duration;
    }

    public RoomProperties getLocation() {
        return location;
    }

    public Object getAttribute(String attributesName) {
        return attributes.get(attributesName);
    }

    public boolean hasAttribute(String attributeName) {
        return attributes.containsKey(attributeName);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }


    public void setStart(Date start) {
        this.start = start;
    }

    public void setStart(String start) throws ParseException {
        this.start = dateTimeFormatter.parse(start);
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setEnd(Date endingTime) {
        duration = (endingTime.getTime() - start.getTime()) / (1000 * 60);
    }

    public void setEnd(String end) throws ParseException {
        duration = (dateTimeFormatter.parse(end).getTime() - start.getTime()) / (1000 * 60);
    }


    public void setLocation(RoomProperties location) {
        this.location = location;
    }

    public ScheduleSlot setAttribute(String attributeName, Object attributeValue) {
        attributes.put(attributeName, attributeValue);
        return this;
    }

    @Override
    public String toString() {
        return "starts: " + start + " duration: " + duration + " location: " + location.getName() + ScheduleExporter.mapToString(attributes);
    }

    public static class Builder {
        private Date start;


        private long duration;
        private RoomProperties location;


        private final Map<String, Object> attributes;

        public Builder() {
            start = null;
            duration = 0;
            location = null;
            attributes = new HashMap<>();
        }

        public Builder setStart(Date start) {
            this.start = start;
            return this;
        }

        public Builder setStart(String start) throws ParseException {
            this.start = dateTimeFormatter.parse(start);
            return this;
        }

        public Builder setEnd(Date endingTime) {
            duration = (endingTime.getTime() - start.getTime()) / (1000 * 60);
            return this;
        }

        public Builder setEnd(String end) throws ParseException {
            duration = (dateTimeFormatter.parse(end).getTime() - start.getTime()) / (1000 * 60);
            return this;
        }


        public Builder setDuration(long duration) {
            this.duration = duration;
            return this;
        }


        public Builder setLocation(RoomProperties location) {
            this.location = location;
            return this;
        }

        public Builder setAttribute(String attributeName, Object attributeValue) {
            attributes.put(attributeName, attributeValue);
            return this;
        }

        public ScheduleSlot build() {

            if (start == null)
                throw new ScheduleException("ScheduleSlot builder: the start has to be defined.");

            if (duration <= 0)
                throw new ScheduleException("ScheduleSlot builder: the duration is:" + duration + "and it has to be a positive whole number of minutes;");
            if (location == null)
                throw new ScheduleException("ScheduleSlot builder: every slot has to be bound to specific room in or in this context location.");

            return new ScheduleSlot(this.start, this.duration, this.location, this.attributes);
        }
    }

}