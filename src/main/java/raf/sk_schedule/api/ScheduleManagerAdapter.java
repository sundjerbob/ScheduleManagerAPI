package raf.sk_schedule.api;



import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static raf.sk_schedule.api.Constants.*;
import static raf.sk_schedule.util.format.DateTimeFormatter.parseDate;

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

    @Override
    public void initialize(String startDate, String endDate) {
        this.startingDate = parseDate(startDate);
        this.endingDate = parseDate(endDate);

    }

    @Override
    public void initialize(Date startDate, Date endDate) {
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
