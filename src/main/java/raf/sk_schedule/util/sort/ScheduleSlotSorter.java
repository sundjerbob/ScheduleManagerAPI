package raf.sk_schedule.util.sort;

import raf.sk_schedule.model.schedule_node.ScheduleSlot;

import java.util.List;

/**
 * A helper class for sorting ScheduleSlot instances.
 */
public class ScheduleSlotSorter {

    /**
     * Sorts a list of ScheduleSlot instances based on the provided comparator.
     *
     * @param schedule       The list of ScheduleSlot instances to be sorted.
     * @param slotComparator The custom comparator defining the schedule-slots sorting logic.
     */
    public static void sort(List<ScheduleSlot> schedule, ScheduleSlotComparator slotComparator) {
        schedule.sort(slotComparator::compare);
    }
}