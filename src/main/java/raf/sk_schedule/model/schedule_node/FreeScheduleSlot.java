package raf.sk_schedule.model.schedule_node;

import raf.sk_schedule.model.location_node.RoomProperties;
import raf.sk_schedule.model.schedule_mapper.RepetitiveScheduleMapper;

import java.util.Date;

import static raf.sk_schedule.util.exporter.ScheduleExporterJSON.serializeObject;

public class FreeScheduleSlot {

    private Date date;
    private String startTime;
    private int duration;
    private String endTime;

    private RoomProperties location;

    private FreeScheduleSlot(Date date, String startTime, int duration, String endTime, RoomProperties location) {
        this.date = date;
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = endTime;
        this.location = location;
    }

    @Override
    public String toString() {
        return "<on day:" + date
                + "> <starts at: " + startTime
                + "> <ends_at: " + endTime
                + "> <location: " + location.getName();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public RoomProperties getLocation() {
        return location;
    }

    public void setLocation(RoomProperties location) {
        this.location = location;
    }

    public static class Builder {
        private Date date;
        private String startTime;
        private int duration;
        private String endTime;

        private RoomProperties location;

        public Builder setDate(Date date) {
            this.date = date;
            return this;
        }

        public Builder setStartTime(String startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder setEndTime(String endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder setLocation(RoomProperties location) {
            this.location = location;
            return this;
        }

        public FreeScheduleSlot build() {
            return new FreeScheduleSlot(date, startTime, duration, endTime, location);
        }
    }
}
