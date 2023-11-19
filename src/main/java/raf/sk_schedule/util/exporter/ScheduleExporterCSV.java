package raf.sk_schedule.util.exporter;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.model.schedule_node.ScheduleSlot;

import java.text.ParseException;
import java.util.List;

/**
 * The `ScheduleExporterCSV` class provides utility methods for exporting schedule data to CSV format.
 * It allows you to convert `ScheduleSlot` instances and lists of such instances to CSV format.
 */
public class ScheduleExporterCSV {


    /**
     * Converts a single `ScheduleSlot` instance to a CSV-formatted string.
     *
     * @param slot               The `ScheduleSlot` instance to be converted.
     * @param includedAttributes Optional additional attributes to include in the CSV string.
     * @return A CSV-formatted string representing the provided `ScheduleSlot`.
     * @throws ParseException If there is an issue parsing date and time information.
     */
    public static String slotToCSV(ScheduleSlot slot, String... includedAttributes) throws ParseException {

        StringBuilder csvSlot = new StringBuilder();
        csvSlot.append(slot.getStartTime()).append(", ");
        csvSlot.append(slot.getEndTime()).append(", ");
        csvSlot.append(slot.getLocation().getName());

        for (int i = 0; i < includedAttributes.length; i++) {
            csvSlot.append(includedAttributes[i]).append(i == includedAttributes.length - 1 ? "\n" : ", ");
        }

        return csvSlot.toString();

    }

    /**
     * Converts a list of `ScheduleSlot` instances to a CSV-formatted string.
     *
     * @param list               The list of `ScheduleSlot` instances to be converted.
     * @param includedAttributes Optional additional attributes to include in the CSV string.
     * @return A CSV-formatted string representing the provided list of `ScheduleSlot` instances.
     * @throws ParseException If there is an issue parsing date and time information.
     * @throws ScheduleException If the list is empty or contains elements that are not `ScheduleSlot` instances.
     */
    public static String listToCSV(List<?> list, String... includedAttributes) throws ParseException {

        StringBuilder csv = new StringBuilder("start, end, location, ");
        //adding the names of required attributes/column-names

        for (int i = 0; i < includedAttributes.length; i++) {
            csv.append(includedAttributes[i]).append(i == includedAttributes.length - 1 ? "\n" : ", ");
        }

        if (list.isEmpty() || !(list.get(0) instanceof ScheduleSlot))
            throw new ScheduleException("ScheduleExporterCSV:listToCSV() -> check if the list you passed as argument is empty or elements are not ScheduleSlot instances.");

        for (Object o : list) {
            csv.append(slotToCSV((ScheduleSlot) o));
        }

        return csv.toString();
    }

}
