package raf.sk_schedule.util;

import org.jetbrains.annotations.NotNull;
import raf.sk_schedule.exception.ScheduleIOException;
import raf.sk_schedule.model.ScheduleSlot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleSlotImporterCSV {


    public static @NotNull List<ScheduleSlot> importRoomsCSV(String csvPath) throws IOException {

        List<ScheduleSlot> scheduleSlotsList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(csvPath));
        String line;

        // Read the header row to get column names
        String header = reader.readLine();
        String[] columnNames = header.split(",");

        // Find the indices of the mandatory columns
        int nameIndex = -1;
        int startIndex = -1;
        int locationIndex = -1;
        int durationIndex = -1;
        int endIndex = -1;

        for (int i = 0; i < columnNames.length; i++) {
            String columnName = columnNames[i].trim().toLowerCase();
            if (columnName.equals("name")) {
                nameIndex = i;
            } else if (columnName.equals("start")) {
                startIndex = i;
            } else if (columnName.equals("location")) {
                locationIndex = i;
            } else if (columnName.equals("duration")) {
                durationIndex = i;
            } else if (columnName.equals("end")) {
                endIndex = i;
            }
        }

        if (nameIndex == -1 || startIndex == -1 || locationIndex == -1 || (durationIndex == -1 && endIndex == -1)) {
            throw new ScheduleIOException("Missing required columns in the CSV file.");
        }

        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            if (values.length < 3) {
                // Handle the case where there are not enough mandatory columns
                continue;
            }

            String name = values[nameIndex].trim();
            String start = values[startIndex].trim();
            String location = values[locationIndex].trim();
            long duration = durationIndex != -1 ? Long.parseLong(values[durationIndex].trim()) : -1;
            long end = endIndex != -1 ? Long.parseLong(values[endIndex].trim()) : -1;

            // Create a ScheduleSlot instance and add attributes for mandatory columns
            ScheduleSlot.Builder slotBuilder = new ScheduleSlot.Builder()
                    .setAttribute("name", name)
                    .setAttribute("start", start)
                    .setAttribute("location", location);

            if (duration != -1) {
                slotBuilder.setAttribute("duration", duration);
            } else {
                slotBuilder.setAttribute("end", end);
            }

            // Add additional attributes for columns beyond the mandatory ones
            for (int i = 0; i < columnNames.length; i++) {
                if (i != nameIndex && i != startIndex && i != locationIndex && i != durationIndex && i != endIndex) {
                    // This column is an extra attribute
                    slotBuilder.setAttribute(columnNames[i].trim(), values[i].trim());
                }
            }

            ScheduleSlot slot = slotBuilder.build();
        }

        reader.close();
        return scheduleSlotsList;

    }
}
