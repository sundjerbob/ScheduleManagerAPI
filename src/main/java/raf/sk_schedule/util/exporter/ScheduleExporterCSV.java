package raf.sk_schedule.util.exporter;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.model.schedule.ScheduleSlot;

import java.text.ParseException;
import java.util.List;


public class ScheduleExporterCSV {

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
