package raf.sk_schedule.util.sort;

import raf.sk_schedule.model.schedule_node.ScheduleSlot;
import raf.sk_schedule.util.sort.default_sorts.AbsolutStartComparator;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.signum;

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
     */
    public List<ScheduleSlot> sort(List<ScheduleSlot> schedule, ScheduleSlotComparator slotComparator) {

        // make the buffer list to perform filtering computation on, with a value of passed schedule list
        List<ScheduleSlot> bufferList = new ArrayList<>(schedule);

        // perform sort algorithm
        bufferList.sort(slotComparator::compare);

        //change the value that the passed schedule list reference is pointing on to newly created list that is not the buffer list but has the same value
        schedule = new ArrayList<>(bufferList);

        // return the reference to the operation result list that is the same as the passed argument so the changes will be directly applied on the argument list
        return schedule;
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
        /* flattening ascendingOrder to -1, +1 or 0. */
        bufferList.sort((scheduleSlot1, scheduleSlot2) -> (int) signum(ascendingOrder) * slotComparator.compare(scheduleSlot1, scheduleSlot2));

        return new ArrayList<>(bufferList);
    }

    public List<ScheduleSlot> sortByAbsoluteStartTime(List<ScheduleSlot> schedule, int ascendingOrder) {
        // init buffer
        List<ScheduleSlot> bufferList = new ArrayList<>(schedule);

        // perform sort with default StartTimeComparator support class
        bufferList.sort(new AbsolutStartComparator(ascendingOrder)::compare);


        return new ArrayList<>(bufferList);

    }


}