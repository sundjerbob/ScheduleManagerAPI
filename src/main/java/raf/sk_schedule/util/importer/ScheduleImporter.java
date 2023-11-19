package raf.sk_schedule.util.importer;

import raf.sk_schedule.exception.ScheduleIOException;
import raf.sk_schedule.model.location_node.RoomProperties;
import raf.sk_schedule.model.schedule_node.ScheduleSlot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The `ScheduleImporter` class provides utility methods for importing schedule and room data from CSV files.
 * It allows you to create `ScheduleSlot` instances and `RoomProperties` instances based on the data stored in CSV files.
 */
public class ScheduleImporter {


    /**
     * Imports schedule data from a CSV file and constructs a list of `ScheduleSlot` instances.
     *
     * @param csvPath The path to the CSV file containing schedule data.
     * @param rooms   A map of room names to `RoomProperties` instances for location lookup.
     * @return A list of `ScheduleSlot` instances parsed from the CSV file.
     * @throws IOException    If an I/O error occurs during file reading.
     * @throws ParseException If there is an issue parsing date and time information.
     */
    public static List<ScheduleSlot> importScheduleCSV(String csvPath, Map<String, RoomProperties> rooms) throws IOException, ParseException {

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
            throw new ScheduleIOException("Missing required columns in the CSV file.\nColumns that are missing are:"
                    + (startIndex == -1 ? " 'start' " : "")
                    + (locationIndex == -1 ? " 'location' " : "")
                    + (startIndex == -1 ? " 'start' " : "")
                    + (durationIndex == -1 && endIndex == -1 ? " 'duration' or 'end' " : "."));
        }


        // read the rest of the rows and build slots
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");

            if (values.length < 3) { // check if there is non-sufficient number of columns missing from current row
                throw new ScheduleIOException("There is a column missing in a row.");
            }

            String start = values[startIndex].trim();
            String location = values[locationIndex].trim();
            int duration = -1;
            String end = "";
            if (durationIndex != -1)
                duration = Integer.parseInt(values[durationIndex].trim());
            else
                end = values[endIndex].trim();

            // Create a ScheduleSlot instance and add attributes for mandatory columns
            ScheduleSlot.Builder slotBuilder =
                    new ScheduleSlot.Builder()
                            .setStartTime(start)
                            .setLocation(rooms.get(location));

            if (duration != -1) {
                slotBuilder.setDuration(duration);
            } else {
                slotBuilder.setEndTime(end);
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

    /**
     * Imports room data from a CSV file and constructs a map of room names to respective `RoomProperties` instances.
     *
     * @param csvPath The path to the CSV file containing room data.
     * @return A map of room names to `RoomProperties` instances.
     * @throws IOException If an I/O error occurs during file reading.
     */
    public static Map<String, RoomProperties> importRoomsCSV(String csvPath) throws IOException {

        Map<String, RoomProperties> roomPropertiesMap = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(csvPath));
        String line;

        // Read the header row to get column names
        String header = reader.readLine();
        String[] columnNames = header.split(",");

        // Find the indices of the mandatory columns
        int nameIndex = -1;
        int capacityIndex = -1;
        int hasComputersIndex = -1;
        int hasProjectorIndex = -1;

        for (int i = 0; i < columnNames.length; i++) {
            String columnName = columnNames[i].trim().toLowerCase();
            switch (columnName) {
                case "name" -> nameIndex = i;
                case "capacity" -> capacityIndex = i;
                case "has_computers" -> hasComputersIndex = i;
                case "has_projector" -> hasProjectorIndex = i;
            }
        }

        if (nameIndex == -1 || capacityIndex == -1 || hasComputersIndex == -1 || hasProjectorIndex == -1) {
            throw new ScheduleIOException("Missing required columns in the CSV file.");
        }

        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            if (values.length < 4) {
                // Handle the case where there are not enough columns
                continue;
            }

            String name = values[nameIndex].trim();
            int capacity = Integer.parseInt(values[capacityIndex].trim());
            boolean hasComputers = Boolean.parseBoolean(values[hasComputersIndex].trim());
            boolean hasProjector = Boolean.parseBoolean(values[hasProjectorIndex].trim());

            RoomProperties.Builder roomBuilder = new RoomProperties.Builder()
                    .setName(name)
                    .setCapacity(capacity)
                    .setHasComputers(hasComputers)
                    .setHasProjector(hasProjector);

            for (int i = 0; i < columnNames.length; i++) {
                if (i != nameIndex && i != capacityIndex && i != hasComputersIndex && i != hasProjectorIndex) {
                    // This column is an extra attribute
                    roomBuilder.setAttribute(columnNames[i].trim(), values[i].trim());
                }
            }

            RoomProperties roomProperties = roomBuilder.build();
            roomPropertiesMap.put(roomProperties.getName(), roomProperties);
        }

        reader.close();

        return roomPropertiesMap;
    }
}
