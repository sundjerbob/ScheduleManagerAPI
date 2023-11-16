package raf.sk_schedule.model.schedule;


import raf.sk_schedule.api.Constants.WeekDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RepetitiveScheduleMapper {

    //default difference between linked slots is 7 days, meaning weekly occurrence
    private static int DEFAULT_RECCURRENCE_PERIOD = 7;
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


    private List<Date> mapSlotReoccurrences() {

        Calendar calendar = Calendar.getInstance();
        List<Date> slotOccurrences = new ArrayList<>();
        long oneDayMills = /*|mills in sec|*/1000 * /*|sec in min|*/60 * /*|min in hr|*/60 * /*|hr in day|*/24;

        if (weekDay != null) {
            //find the first occurrence of the weekDay from the beginning of the reoccurrences interval
            for (long time = recurrenceIntervalStart.getTime(); time < recurrenceIntervalEnd.getTime(); time += oneDayMills) {
                Date date = new Date(time);
                calendar.setTime(date);

                if (WeekDay.values()[calendar.get(Calendar.DAY_OF_WEEK) - 1] == this.weekDay) {
                    slotOccurrences.add(date);
                    break;
                }
            }
        } else
            slotOccurrences.add(recurrenceIntervalStart);

        //slotOccurrences should have one element by this time...
        if (slotOccurrences.isEmpty())
            throw new RuntimeException(" 0,o' ?*#$");

        // time difference between each linked slot is number of days (recurrencePeriod) * how many mills there is in one day
        long deltaTimeMills = recurrencePeriod > 0 ? (long) recurrencePeriod * oneDayMills : DEFAULT_RECCURRENCE_PERIOD;

        // finding all the slot occurrences starting from the first occurrence since we already had found that
        for (long time = slotOccurrences.get(0).getTime(); time < this.recurrenceIntervalEnd.getTime(); time += deltaTimeMills) {
            slotOccurrences.add(new Date(time));
        }

        return slotOccurrences;
    }


    public List<ScheduleSlot> mapSchedule() {

        long deltaPeriod = ((long) recurrencePeriod) * 1000 * 60 * 24;

        // long start = weekDay == null ? recurrenceIntervalStart.getTime() : weekDayOccurrences(weekDay)[0];
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
