package raf.sk_schedule.model.schedule;


import raf.sk_schedule.api.Constants.WeekDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RepetitiveScheduleMapper {
    private Date recurrenceIntervalStart;
    private Date recurrenceIntervalEnd;
    private int recurrencePeriod;

    private String startTime;
    private String endTime;
    private int duration;
    private WeekDay weekDay;


    List<ScheduleSlot> slotInstances;
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


    private List<Date> weekDayOccurrences(WeekDay day) {

        long startTime = recurrenceIntervalStart.getTime();
        long endTime = recurrenceIntervalEnd.getTime();
        Calendar calendar = Calendar.getInstance();
        List<Date> occurrences = new ArrayList<>();
        for (long time = startTime; time < endTime; time += 24 * 60 * 1000) {
            Date date = new Date(time);
            calendar.setTime(date);

            if (WeekDay.values()[calendar.get(Calendar.DAY_OF_WEEK) - 1] == day)
                occurrences.add(date);

        }
        return occurrences;
    }

    public List<ScheduleSlot> mapSchedule() {

        long deltaPeriod = ((long) recurrencePeriod) * 1000 * 60 * 24;

//        long start = weekDay == null ? recurrenceIntervalStart.getTime() : weekDayOccurrences(weekDay)[0];
        long end = recurrenceIntervalEnd.getTime();

        /*for (long time = start; time < end; time += deltaPeriod) {
            ScheduleSlot slot = new ScheduleSlot.Builder()
                    .setStart().build();

        }*/
        return null;
    }


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


    public static class Builder {
        private Date recurrenceIntervalStart;
        private Date recurrenceIntervalEnd;
        private int recurrencePeriod;
        private String startTime;
        private String endTime;
        private int duration;
        private WeekDay weekDay;

        public Builder() {
            recurrenceIntervalStart = null;
            recurrenceIntervalEnd = null;
            recurrencePeriod = 7; //default delta is weekly or on every 7 days it rep
            startTime = null;
            endTime = null;
            duration = 0;
            weekDay = null;
        }

        public Builder setGetRecurrenceIntervalEnd(Date getRecurrenceIntervalEnd) {
            this.recurrenceIntervalEnd = getRecurrenceIntervalEnd;
            return this;
        }

        public Builder setRecurrenceIntervalStart(Date recurrenceIntervalStart) {
            this.recurrenceIntervalStart = recurrenceIntervalStart;
            return this;
        }

        public void setWeekDay(WeekDay weekDay) {
            this.weekDay = weekDay;
        }

        public Builder setDay(int dayIndex) {
            weekDay = WeekDay.values()[++dayIndex];
            return this;
        }

        public Builder setDay(String dayName) {
            weekDay = WeekDay.valueOf(dayName);
            return this;
        }

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
