package raf.sk_schedule.util.importer;

import raf.sk_schedule.exception.ScheduleIOException;
import raf.sk_schedule.model.RoomProperties;
import raf.sk_schedule.model.ScheduleSlot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScheduleSlotImporterCSV {


    public static  List<ScheduleSlot> importRoomsCSV(String csvPath, Map<String, RoomProperties> rooms) throws IOException, ParseException {

        List<ScheduleSlot> scheduleSlotsList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(csvPath));
        String line;

        // Read the header row to get column names
        String header = reader.readLine();
        String[] columnNames = header.split(",");

        // Find the indices of the mandatory columns
        int startIndex = -1;
        int locationIndex = -1;
        int durationIndex = -1;
        int endIndex = -1;

        // match mandatory columns names
        for (int i = 0; i < columnNames.length; i++) {
            String columnName = columnNames[i].trim().toLowerCase();
            switch (columnName) {
                case "start" -> startIndex = i;
                case "location" -> locationIndex = i;
                case "duration" -> durationIndex = i;
                case "end" -> endIndex = i;
            }
        }


        // check if there is any mandatory column missing
        if (startIndex == -1 || locationIndex == -1 || (durationIndex == -1 && endIndex == -1)) {
            throw new ScheduleIOException("Missing required columns in the CSV file.");
        }


        // read the rest of the rows and build slots
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");

            if (values.length < 3) { // check if there is non-sufficient number of columns missing from current row
                throw new ScheduleIOException("There is a column missing in a row.");
            }

            String start = values[startIndex].trim();
            String location = values[locationIndex].trim();
            long duration = -1;
            String end = "";
            if (durationIndex != -1)
                duration = Long.parseLong(values[durationIndex].trim());
            else
                end = values[endIndex].trim();

            // Create a ScheduleSlot instance and add attributes for mandatory columns
            ScheduleSlot.Builder slotBuilder = new ScheduleSlot.Builder()
                    .setStart(start)
                    .setLocation(rooms.get(location));
            if (duration != -1) {
                slotBuilder.setDuration(duration);
            } else {
                slotBuilder.setEnd(end);
            }

            // Add additional attributes for columns beyond the mandatory ones
            for (int i = 0; i < columnNames.length; i++) {
                if (i != startIndex && i != locationIndex && i != durationIndex && i != endIndex) {
                    // This column is an extra attribute
                    slotBuilder.setAttribute(columnNames[i].trim(), values[i].trim());
                }
            }

            scheduleSlotsList.add(slotBuilder.build());

        }

        reader.close();
        return scheduleSlotsList;

    }
}
