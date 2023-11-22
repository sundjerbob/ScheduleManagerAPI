package raf.sk_schedule.api;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static raf.sk_schedule.api.Constants.*;
import static raf.sk_schedule.util.date_formater.DateTimeFormatter.parseDate;

public abstract class ScheduleManagerAdapter implements ScheduleManager {

    protected Date startingDate;
    protected Date endingDate;
    protected WeekDay[] excludedDays;
    protected SimpleDateFormat dateFormat;
    protected SimpleDateFormat dateTimeFormat;


    protected ScheduleManagerAdapter() {
        excludedDays = new WeekDay[WeekDay.values().length];
        dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);

    }


    protected boolean isAcceptableDay(Object day) {
        for (WeekDay excludedDay : WeekDay.values()) {
            if (day instanceof String && Enum.valueOf(WeekDay.class, ((String) day).toUpperCase()) == excludedDay)
                return false;
            else if (day instanceof WeekDay && day == excludedDay)
                return false;
        }
        return true;
    }

    @Override
    public void initialize(Object lowerDateBound, Object upperDateBound) {
        this.startingDate = /**/ lowerDateBound instanceof String ? /**/ parseDate(lowerDateBound.toString()) : /**/ lowerDateBound instanceof Date ? (Date) lowerDateBound : null; /**/
        this.endingDate = /**/ upperDateBound instanceof String ? /**/ parseDate(upperDateBound.toString()) : /**/ upperDateBound instanceof Date ? (Date) upperDateBound : null; /**/
    }

    @Override
    public void setExcludedWeekDays(WeekDay... excludedDays) {

        // clear array
        Arrays.fill(this.excludedDays, null);

        for (WeekDay excludedDay : excludedDays) {
            this.excludedDays[excludedDay.ordinal()] = excludedDay;
        }
    }

    @Override
    public void setExcludedWeekDays(String... excludedDays) {
        // clear array
        Arrays.fill(this.excludedDays, null);

        for (String str : excludedDays) {
            String excludedDay = str.toUpperCase();
            this.excludedDays[Enum.valueOf(WeekDay.class, excludedDay).ordinal()] = Enum.valueOf(WeekDay.class, excludedDay);
        }
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }


}
