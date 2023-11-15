package raf.sk_schedule.api;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.model.schedule.ScheduleSlot;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static raf.sk_schedule.api.Constants.*;

public abstract class ScheduleManagerAdapter implements ScheduleManager {

    protected Date startingDate;
    protected Date endingDate;

    protected SimpleDateFormat dateFormat;

    protected SimpleDateFormat dateTimeFormat;


    protected ScheduleManagerAdapter() {
        dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
    }

    @Override
    public void initialize(String startDate, String endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    }

    public Date getStartingDate() {
        return startingDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    protected String formatDate(Date date) {
        return dateFormat.format(date);
    }

    protected Date parseDate(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            throw new ScheduleException("The universal format for date within schedule component is: " + DATE_FORMAT + " !");
        }
    }

    protected String formatDateTime(Date date) {
        return dateTimeFormat.format(date);
    }

    protected Date parseDateTime(String date) {
        try {
            return dateTimeFormat.parse(date);
        } catch (ParseException e) {
            throw new ScheduleException("The universal format for date & time within schedule component is: " + DATE_TIME_FORMAT + " !");

        }
    }
}
