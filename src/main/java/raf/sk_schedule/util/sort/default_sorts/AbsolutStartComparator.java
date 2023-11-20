package raf.sk_schedule.util.sort.default_sorts;

import raf.sk_schedule.model.schedule_node.ScheduleSlot;
import raf.sk_schedule.util.sort.ScheduleSlotComparator;

public class AbsolutStartComparator implements ScheduleSlotComparator {
    private int ascendingOrder;

    public AbsolutStartComparator() {
        //default ascending order is ascending and the value for that is 1 as defined in interface ScheduleSlotComparator
        ascendingOrder = 1;
    }

    public AbsolutStartComparator(int ascendingOrder) {
        // flatten the value to be sure that it is 1, -1 or 0
        this.ascendingOrder = (int) Math.signum(ascendingOrder);
    }


    @Override
    public int compare(ScheduleSlot slot1, ScheduleSlot slot2) {

        return ascendingOrder * Long.compare(slot2.getAbsoluteStartTimeMillis(), slot1.getAbsoluteStartTimeMillis());
    }


    public void setAscendingOrder(int ascendingOrder) {
        this.ascendingOrder = ascendingOrder;
    }

    public int getAscendingOrder() {
        return ascendingOrder;
    }
}
