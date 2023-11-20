package raf.sk_schedule.util.exporter;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.model.schedule_node.ScheduleSlot;

import java.text.ParseException;
import java.util.List;

import static raf.sk_schedule.util.date_formater.DateTimeFormatter.formatDate;

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
     */
    public static String slotToCSV(ScheduleSlot slot, String... includedAttributes) {

        StringBuilder csvSlot = new StringBuilder()
                .append(formatDate(slot.getDate()))
                .append(slot.getStartTime()).append(", ")
                .append(slot.getEndTime()).append(", ")
                .append(slot.getLocation().getName()).append(", ");

        for (int i = 0; i < includedAttributes.length; i++) {
            csvSlot.append(includedAttributes[i]).append(i == includedAttributes.length - 1 ? "\n" : ", ");
        }
        if (includedAttributes.length == 0)
            csvSlot.setLength(csvSlot.length() - 1);
        return csvSlot.toString();

    }

    /**
     * Converts a list of `ScheduleSlot` instances to a CSV-formatted string.
     *
     * @param list               The list of `ScheduleSlot` instances to be converted.
     * @param includedAttributes Optional additional attributes to include in the CSV string.
     * @return A CSV-formatted string representing the provided list of `ScheduleSlot` instances.
     * @throws ScheduleException If the list is empty or contains elements that are not `ScheduleSlot` instances.
     */
    public static String listToCSV(List<?> list, String... includedAttributes) {

        StringBuilder csv = new StringBuilder("date, start, end, location");

        if (includedAttributes != null) {
            //if we don't have any additional columns we write '\n' if we have some we write ', '
            csv.append(includedAttributes.length == 0 ? "\n" : ", ");

            for (int i = 0; i < includedAttributes.length; ++i) {
                //if we made it to the end of column names we write '\n' to start new line , else we have more we write ', '
                csv.append(includedAttributes[i]).append(i == includedAttributes.length - 1 ? "\n" : ", ");
            }
        }

        // this method supports lists of ScheduleSlot class instances
        if (!list.isEmpty() && !(list.get(0) instanceof ScheduleSlot))
            throw new ScheduleException("ScheduleExporterCSV:listToCSV() -> check if the list you passed as argument is empty or elements are not ScheduleSlot instances.");

        for (Object o : list) {
            csv.append(slotToCSV((ScheduleSlot) o));
        }

        return csv.toString();
    }

}
