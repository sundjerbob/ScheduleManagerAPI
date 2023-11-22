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
    protected List<WeekDay> acceptableDays;
    protected SimpleDateFormat dateFormat;
    protected SimpleDateFormat dateTimeFormat;


    protected ScheduleManagerAdapter() {
        acceptableDays = new ArrayList<>();
        acceptableDays.addAll(Arrays.asList(WeekDay.values()));
        dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);


    }


    @Override
    public void initialize(Object lowerDateBound, Object upperDateBound) {
        this.startingDate = /**/ lowerDateBound instanceof String ? /**/ parseDate(lowerDateBound.toString()) : /**/ lowerDateBound instanceof Date ? (Date) lowerDateBound : null; /**/
        this.endingDate = /**/ upperDateBound instanceof String ? /**/ parseDate(upperDateBound.toString()) : /**/ upperDateBound instanceof Date ? (Date) upperDateBound : null; /**/
    }

    public void setExcludedWeekDays(WeekDay... excludedDays) {
        for (WeekDay excluded : excludedDays) {
            acceptableDays.remove(excluded);
        }

    }

    public Date getStartingDate() {
        return startingDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }


}
