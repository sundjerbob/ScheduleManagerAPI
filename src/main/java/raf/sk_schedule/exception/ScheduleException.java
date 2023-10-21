package raf.sk_schedule.exception;

public class ScheduleException extends RuntimeException {
    public ScheduleException(String message){
        super(message);
    }
    public ScheduleException(Exception e) {
        super(e);
    }

}
