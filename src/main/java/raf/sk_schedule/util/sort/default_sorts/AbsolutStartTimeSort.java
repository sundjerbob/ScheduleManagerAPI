package raf.sk_schedule.util.sort.default_sorts;

import raf.sk_schedule.model.schedule_node.ScheduleSlot;
import raf.sk_schedule.util.sort.ScheduleSlotComparator;

import java.util.Date;

import static raf.sk_schedule.util.formater.DateTimeFormatter.formatDate;

public class AbsolutStartTimeSort implements ScheduleSlotComparator {
    @Override
    public int compare(ScheduleSlot slot1, ScheduleSlot slot2) {

        return Long.compare(slot2.getAbsoluteStartTimeMillis(), slot1.getAbsoluteStartTimeMillis());
    }
}
