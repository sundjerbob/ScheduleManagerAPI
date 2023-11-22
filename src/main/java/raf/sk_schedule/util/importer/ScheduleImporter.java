package raf.sk_schedule.util.importer;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.exception.ScheduleIOException;
import raf.sk_schedule.model.location_node.RoomProperties;
import raf.sk_schedule.model.schedule_node.ScheduleSlot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static raf.sk_schedule.util.date_formater.DateTimeFormatter.parseDate;
import static raf.sk_schedule.util.persistence.ScheduleFileOperationUnit.readFileToString;

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
     * @throws ScheduleIOException If an I/O error occurs during file reading or if there is an issue parsing date and time information.
     */
    public static List<ScheduleSlot> importScheduleCSV(String csvPath, Map<String, RoomProperties> rooms) throws ScheduleIOException {
        String fileContent = readFileToString(csvPath);
        return parseScheduleCSV(fileContent, rooms);
    }

    /**
     * Imports room data from a CSV file and constructs a map of room names to respective `RoomProperties` instances.
     *
     * @param csvPath The path to the CSV file containing room data.
     * @return A map of room names to `RoomProperties` instances.
     * @throws ScheduleIOException If an I/O error occurs during file reading or if there is an issue parsing date and time information.
     */
    public static Map<String, RoomProperties> importRoomsCSV(String csvPath) throws ScheduleIOException {
        String fileContent = readFileToString(csvPath);
        return parseRoomsCSV(fileContent);
    }

    /**
     * Parses the content of a CSV file and constructs a list of `ScheduleSlot` instances.
     *
     * @param fileContent The content of the CSV file.
     * @param rooms       A map of room names to `RoomProperties` instances for location lookup.
     * @return A list of `ScheduleSlot` instances parsed from the CSV file.
     * @throws ScheduleException If an issue parsing date and time information.
     */
    private static List<ScheduleSlot> parseScheduleCSV(String fileContent, Map<String, RoomProperties> rooms) throws ScheduleIOException {
        List<ScheduleSlot> scheduleSlotsList = new ArrayList<>();

        // Split the file content into lines
        String[] lines = fileContent.split("\n");

        String[] columnNames = lines[0].split(",");

        // Find the indices of the mandatory columns
        int dateIndex = -1;
        int startIndex = -1;
        int locationIndex = -1;
        int durationIndex = -1;
        int endIndex = -1;

        // match mandatory columns names
        for (int i = 0; i < columnNames.length; i++) {
            String columnName = columnNames[i].trim().toLowerCase();
            switch (columnName) {
                case "date" -> dateIndex = i;
                case "start" -> startIndex = i;
                case "location" -> locationIndex = i;
                case "duration" -> durationIndex = i;
                case "end" -> endIndex = i;
            }
        }

        // check if there is any mandatory column missing
        if (dateIndex == -1 || startIndex == -1 || locationIndex == -1 || (durationIndex == -1 && endIndex == -1)) {
            throw new ScheduleIOException("Missing required columns in the CSV file.\nColumns that are missing are:"
                    + (dateIndex == -1 ? " 'date' " : "")
                    + (startIndex == -1 ? " 'start' " : "")
                    + (locationIndex == -1 ? " 'location' " : "")
                    + (startIndex == -1 ? " 'start' " : "")
                    + (durationIndex == -1 && endIndex == -1 ? " 'duration' or 'end' " : "."));
        }

        // read the rest of the rows and build slots
        for (int row = 1; row < lines.length; row++) {

            String[] values = lines[row].split(",");

            if (values.length < 4)  // check if there is non-sufficient number of columns missing from the current row
                throw new ScheduleIOException("There is a column missing in a row.");


            ScheduleSlot.Builder slotBuilder = new ScheduleSlot.Builder()
                    .setDate(parseDate(values[dateIndex].trim()))
                    .setStartTime(values[startIndex].trim());

            if (rooms.containsKey(values[locationIndex] = values[locationIndex].trim()))
                slotBuilder.setLocation(rooms.get(values[locationIndex]));
            else
                throw new ScheduleIOException("ScheduleSCV parse failed! Room with name " + values[locationIndex] + " couldn't be linked.");

            if (durationIndex != -1)
                slotBuilder.setDuration(Integer.parseInt(values[durationIndex].trim()));

            if (endIndex != -1)
                slotBuilder.setEndTime(values[endIndex].trim());

            // Add additional attributes for columns beyond the mandatory ones
            for (int i = 0; i < columnNames.length; i++) {
                if (i != startIndex && i != locationIndex && i != durationIndex && i != endIndex) {
                    // This column is an extra attribute
                    slotBuilder.setAttribute(columnNames[i].trim(), values[i].trim());
                }
            }

            scheduleSlotsList.add(slotBuilder.build());
        }

        return scheduleSlotsList;
    }


    /**
     * Parses the content of a CSV file and constructs a map of room names to respective `RoomProperties` instances.
     *
     * @param fileContent The content of the CSV file.
     * @return A map of room names to `RoomProperties` instances.
     * @throws ScheduleIOException If an I/O error occurs during file reading.
     */
    private static Map<String, RoomProperties> parseRoomsCSV(String fileContent) throws ScheduleIOException {
        Map<String, RoomProperties> roomPropertiesMap = new HashMap<>();

        // Split the file content into lines
        String[] lines = fileContent.split("\n");

        // Extract column names from the header row
        String header = lines[0];
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

        // Iterate through the rows and build room properties
        for (int row = 1; row < lines.length; row++) {
            String[] values = lines[row].split(",");

            if (values.length < 4) {
                // Handle the case where there are not enough columns
                continue;
            }

            String name = values[nameIndex].trim();
            int capacity = Integer.parseInt(values[capacityIndex].trim());
            int hasComputers = Integer.parseInt(values[hasComputersIndex].trim());
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

        return roomPropertiesMap;

    }


}



