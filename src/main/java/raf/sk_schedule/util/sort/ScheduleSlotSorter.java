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

    /**
     * Sorts a list of ScheduleSlot instances based on the provided comparator and order.
     *
     * @param schedule       The list of ScheduleSlot instances to be sorted.
     * @param slotComparator The custom comparator defining the schedule-slots sorting logic.
     * @param ascendingOrder An integer indicating the sorting order:
     *                       Use {@link ScheduleSlotComparator#ASCENDING_ORDER} for ascending order.
     *                       Use {@link ScheduleSlotComparator#DESCENDING_ORDER} for descending order.
     */
    public static void sort(List<ScheduleSlot> schedule, ScheduleSlotComparator slotComparator, int ascendingOrder) {
        schedule.sort(slotComparator::compare);
    }


}