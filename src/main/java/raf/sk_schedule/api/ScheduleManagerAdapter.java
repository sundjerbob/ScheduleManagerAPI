package raf.sk_schedule.api;


import raf.sk_schedule.exception.ScheduleException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static raf.sk_schedule.api.Constants.*;
import static raf.sk_schedule.util.date_formater.DateTimeFormatter.parseDate;

public abstract class ScheduleManagerAdapter implements ScheduleManager {

    protected Date startingDate;
    protected Date endingDate;
    protected List<WeekDay> excludedDays;
    protected SimpleDateFormat dateFormat;
    protected SimpleDateFormat dateTimeFormat;


    protected ScheduleManagerAdapter() {
        excludedDays = new ArrayList<>();
        dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);

    }


    protected boolean isExcludedDay(Object day) {

        WeekDay weekDay;
        if (day instanceof String)
            weekDay = Enum.valueOf(WeekDay.class, day.toString().toUpperCase());
        if (day instanceof Number)
            weekDay = WeekDay.values()[(int) day];
        else if (day instanceof WeekDay)
            weekDay = (WeekDay) day;
        else
            throw new ScheduleException("The argument of this method: \"isExcludedDay()\" can be either string, number or WeekDay enumeration instance!");

        return excludedDays.contains(weekDay);
    }

    @Override
    public void initialize(Object lowerDateBound, Object upperDateBound) {
        this.startingDate = /**/ lowerDateBound instanceof String ? /**/ parseDate(lowerDateBound.toString()) : /**/ lowerDateBound instanceof Date ? (Date) lowerDateBound : null; /**/
        this.endingDate = /**/ upperDateBound instanceof String ? /**/ parseDate(upperDateBound.toString()) : /**/ upperDateBound instanceof Date ? (Date) upperDateBound : null; /**/
    }

    @Override
    public void setExcludedWeekDays(Object... excludedDays) {
        //reset the values
        this.excludedDays = new ArrayList<>();
        //fill in the new values
        for (Object excludedDay : excludedDays) {

            if (excludedDay instanceof String)
                this.excludedDays.add(Enum.valueOf(WeekDay.class, excludedDay.toString().toUpperCase()));

            else if (excludedDay instanceof WeekDay)
                this.excludedDays.add((WeekDay) excludedDay);

            else if (excludedDay instanceof Number)
                this.excludedDays.add(WeekDay.values()[(int) excludedDay]);
        }
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }


}
