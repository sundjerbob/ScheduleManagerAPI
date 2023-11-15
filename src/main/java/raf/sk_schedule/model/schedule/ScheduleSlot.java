package raf.sk_schedule.model.schedule;

import raf.sk_schedule.api.Constants.WeekDay;
import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.model.location.RoomProperties;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static raf.sk_schedule.api.Constants.DATE_FORMAT;
import static raf.sk_schedule.api.Constants.DATE_TIME_FORMAT;
import static raf.sk_schedule.util.exporter.ScheduleExporterJSON.serializeObject;

/**
 * This class represents a universal time slot within the scheduling component.
 */
public class ScheduleSlot {

    /**
     * Date of the time slot starts.
     */
    private Date date;

    /**
     * Time of the day when the time slot starts.
     */
    protected String startTime;

    /**
     * Time of the day when the time slot ends.
     */
    protected String endTime;

    /**
     * Duration of the time slot in minutes.
     */
    protected int duration;

    /**
     * Location (room) where the time slot is scheduled.
     */
    protected RoomProperties location;

    /**
     * Additional attributes associated with the time slot.
     */
    protected Map<String, Object> attributes;


    private ScheduleSlot(Date date, String startTime, String endTime, int duration, RoomProperties location, Map<String, Object> attributes) {
        this.date = date;

        if (startTime == null)
            throw new ScheduleException("Starting time of ScheduleSlot not defined!");

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        if (duration > 0) {
            if (endTime == null) {
                try {
                    this.endTime =
                            dateTimeFormat.format(
                                    new Date(
                                            dateTimeFormat.parse(
                                                    dateFormat.format(date) + " " + startTime).getTime()
                                                    + (long) duration * 1000 * 60
                                    )
                            );
                } catch (ParseException e) {
                    throw new ScheduleException("Date parsing error, check date and time string formats!");

                }
            } else {

            }
        } else if (endTime == null)
            throw new ScheduleException("End time and duration are both not defined thus the be occupied time couldn't be calculated!");

        this.location = location;
        this.attributes = attributes;
    }

    private long getStartTimeInMillis() {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            return dateTimeFormat.parse(dateFormat.format(this.date) + " " + startTime).getTime();
        } catch (ParseException e) {
            throw new ScheduleException(e);
        }
    }

    private long getEndTimeInMillis() {
        return /*|start_in_ms|*/getStartTimeInMillis()
                + /*|millSec|*/1000
                * /*|minutes|*/60
                * /*|dur_in_minutes|*/(long) duration;
    }

    /**
     * Checks if this time slot is colliding with another time slot.
     *
     * @param otherSlot The other time slot to check for collisions.
     * @return True if there's a collision, false otherwise.
     * @throws ParseException If there is an issue parsing the time slots.
     */
    public boolean isCollidingWith(ScheduleSlot otherSlot) throws ParseException {
        long start_1 = getStartTimeInMillis();
        long end_1 = getEndTimeInMillis();
        long start_2 = otherSlot.getStartTimeInMillis();
        long end_2 = otherSlot.getEndTimeInMillis();

        //  collision cases for [1] {2}
        //  1.start >= 2.start >= 2.end >= 1.end // [ {\\\\} ]
        //  1.start >= 2.start >= 1.end >= 2.end // [ {\\\\] }
        //  2.start >= 1.start >= 1.end >= 2.end // { [\\\\] }
        //  2.start >= 1.start >= 2.end >= 1.end // { [\\\\} ]

        return (start_1 <= start_2 && start_2 < end_1) || (start_2 <= start_1 && start_1 < end_2);

    }


    public void setDate(Date date) {
        this.date = date;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setLocation(RoomProperties location) {
        this.location = location;
    }

    public ScheduleSlot setAttribute(String attributeName, String attributeValue) {
        attributes.put(attributeName, attributeValue);
        return this;
    }


    public Date getDate() {
        return date;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public int getDuration() {
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
        return new HashMap<>(attributes);
    }

    public WeekDay getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeekIndex = calendar.get(Calendar.DAY_OF_WEEK);
        return WeekDay.values()[dayOfWeekIndex];
    }


    @Override
    public String toString() {
        return "<on day:" + date
                + "> <starts at: " + startTime
                + "> <ends_at: " + endTime
                + "> <location: " + location.getName()
                + "> <properties: " + serializeObject(attributes) + ">";
    }

    public static class Builder {
        private Date date;
        String startTime;
        String endTime;
        private int duration;
        private RoomProperties location;
        private final Map<String, Object> attributes;

        public Builder() {
            date = null;
            startTime = null;
            endTime = null;
            duration = 0;
            location = null;
            attributes = new HashMap<>();
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Builder setStartTime(String startTime) {
            this.startTime = startTime;
            return this;
        }


        public Builder setEndTime(String endTime) {
            this.endTime = endTime;
            return this;
        }


        public Builder setDuration(int duration) {
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
            return new ScheduleSlot(this.date, this.startTime, this.endTime, this.duration, this.location, this.attributes);
        }
    }

}