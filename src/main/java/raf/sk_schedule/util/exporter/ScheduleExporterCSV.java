package raf.sk_schedule.util.exporter;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.model.schedule_node.ScheduleSlot;

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
                .append(formatDate(slot.getDate())).append(", ")
                .append(slot.getStartTime()).append(", ")
                .append(slot.getEndTime()).append(", ")
                .append(slot.getLocation().getName()).append(", ");

        // Append additional attributes
        for (int i = 0; i < includedAttributes.length; i++) {
            csvSlot.append(
                    slot.hasAttribute(includedAttributes[i]) ? slot.getAttribute(includedAttributes[i]) : "not_defined")
                    .append(i == includedAttributes.length - 1 ? "\n" : ", ");
        }

        // Remove the trailing comma if no additional attributes are present
        if (includedAttributes.length == 0) {
            csvSlot.setLength(csvSlot.length() - 1);
        }

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
    public static String listToCSV(List<?> list, String... includedAttributes) throws ScheduleException{
        StringBuilder csv = new StringBuilder("date, start, end, location");

        // Append additional attributes header
        if (includedAttributes.length > 0) {
            csv.append(", ").append(String.join(", ", includedAttributes)).append("\n");
        } else {
            csv.append("\n");
        }

        // Validate and convert the list of ScheduleSlot instances
        for (Object o : list) {
            if (!(o instanceof ScheduleSlot)) {
                throw new ScheduleException("ScheduleExporterCSV:listToCSV() -> Invalid list content. Check if the list contains only ScheduleSlot instances.");
            }
            csv.append(slotToCSV((ScheduleSlot) o));
        }

        return csv.toString();
    }
}
