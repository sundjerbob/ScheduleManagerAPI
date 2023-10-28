package raf.sk_schedule.model;

import org.jetbrains.annotations.NotNull;
import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.util.ScheduleMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ScheduleSlot {
    public static String dateFormat = "yyyy-MM-dd";

    public static String dateTimeFormat = "yyyy-MM-dd HH:mm";

    public static String timeFormat = "HH:mm";
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat(dateTimeFormat);
    private Date start;

    private long duration;

    private RoomProperties location;

    private Map<String, Object> attributes;


    private ScheduleSlot(Date start, long duration, RoomProperties location, Map<String, Object> attributes) {
        this.start = start;
        this.duration = duration;
        this.location = location;
        this.attributes = attributes;
    }


    public boolean isCollidingWith(@NotNull ScheduleSlot otherSlot) throws ParseException {

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


    public Date getStart() {
        return start;
    }


    public Date getEnd() {
        return new Date(start.getTime() + duration * 60 * 1000);
    }

    public String getStartAsString() throws ParseException {
        return dateFormatter.format(start);

    }

    public String getEndAsString() throws ParseException {
        return dateFormatter.format(getEnd());
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


    public void setStart(Date start) {
        this.start = start;
    }

    public void setStart(String start) throws ParseException {
        this.start = dateFormatter.parse(start);
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setEnd(Date endingTime) {
        duration = (endingTime.getTime() - start.getTime()) / (1000 * 60);
    }

    public void setEnd(String end) throws ParseException {
        duration = (dateFormatter.parse(end).getTime() - start.getTime()) / (1000 * 60);
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
        return "starts: " + start + " duration: " + duration + " location: " + location.getName() + ScheduleMapper.mapToString(attributes);
    }

    public static class Builder {
        private Date start;


        private long duration;
        private RoomProperties location;


        private Map<String, Object> attributes;

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
            this.start = dateFormatter.parse(start);
            return this;
        }

        public Builder setEnd(Date endingTime) {
            duration = (endingTime.getTime() - start.getTime()) / (1000 * 60);
            return this;
        }

        public Builder setEnd(String end) throws ParseException {
            duration = (dateFormatter.parse(end).getTime() - start.getTime()) / (1000 * 60);
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