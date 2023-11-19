package raf.sk_schedule.util.sort;

import raf.sk_schedule.model.schedule_node.ScheduleSlot;

/**
 * A functional interface for defining custom sorting logic for ScheduleSlot instances.
 */
@FunctionalInterface
public interface ScheduleSlotComparator {

    /**
     * Compares two ScheduleSlot instances based on custom sorting logic.
     *
     * @param slot1 The first ScheduleSlot to compare.
     * @param slot2 The second ScheduleSlot to compare.
     * @return 1 if slot1 should be placed before slot2, 0 if they are equal, -1 if slot2 should be placed before slot1.
     */
    int compare(ScheduleSlot slot1, ScheduleSlot slot2);
}