package raf.sk_schedule.util.sort;

import raf.sk_schedule.model.schedule_node.ScheduleSlot;

import java.util.ArrayList;
import java.util.List;

/**
 * A helper class for sorting ScheduleSlot instances.
 */
public class ScheduleSlotSorter {

    /**
     * Sorts a list of ScheduleSlot instances based on the provided comparator.
     * Default order of sort is ascending. If you want to explicitly set the sort order use {@link ScheduleSlotSorter#sort(List, ScheduleSlotComparator, int)}.
     *
     * @param schedule       The list of ScheduleSlot instances to be sorted.
     * @param slotComparator The custom comparator defining the schedule-slots sorting logic.
     * @deprecated
     */
    public List<?> sort(List<ScheduleSlot> schedule, ScheduleSlotComparator slotComparator) {
        List<ScheduleSlot> bufferList = new ArrayList<>(schedule);
        bufferList.sort(slotComparator::compare);
        return new ArrayList<>(bufferList);
    }

    /**
     * Sorts a list of ScheduleSlot instances based on the provided comparator and order.
     *
     * @param schedule       The list of ScheduleSlot instances to be sorted.
     * @param slotComparator The custom comparator defining the schedule-slots sorting logic.
     * @param ascendingOrder An integer indicating the sorting order:
     *                       Use {@link ScheduleSlotComparator#ASCENDING_ORDER} for ascending order.
     *                       Use {@link ScheduleSlotComparator#DESCENDING_ORDER} for descending order.
     */
    public List<ScheduleSlot> sort(List<ScheduleSlot> schedule, ScheduleSlotComparator slotComparator, int ascendingOrder) {
        List<ScheduleSlot> bufferList = new ArrayList<>(schedule);
        if (ascendingOrder > 0)
            bufferList.sort(slotComparator::compare);
        else
            bufferList.sort((scheduleSlot1, scheduleSlot2) -> -1 * slotComparator.compare(scheduleSlot1, scheduleSlot2));
        return new ArrayList<>(bufferList);
    }


}