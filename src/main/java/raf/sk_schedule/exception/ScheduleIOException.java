package raf.sk_schedule.exception;

public class ScheduleIOException extends RuntimeException{
    public ScheduleIOException(String message)
    {
        super(message);
    }

    public ScheduleIOException(Exception e) {
        super(e);
    }


}
