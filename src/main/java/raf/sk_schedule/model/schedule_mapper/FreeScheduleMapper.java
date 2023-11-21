package raf.sk_schedule.model.schedule_mapper;

import raf.sk_schedule.model.schedule_node.FreeScheduleSlot;
import raf.sk_schedule.model.schedule_node.ScheduleSlot;

import java.util.List;

public class FreeScheduleMapper {

    List<ScheduleSlot> bookedSchedule;

    public FreeScheduleMapper() {
    }

    public FreeScheduleMapper(List<ScheduleSlot> schedule) {
        bookedSchedule = schedule;
    }

    // dumbest way to do it to iterate through every minute of absolute time and what
    // public List<FreeScheduleSlot> mapFreeSchedule() {

    //}

}
