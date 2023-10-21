package raf.sk_schedule.util;

import raf.sk_schedule.exception.ScheduleIOException;
import raf.sk_schedule.model.RoomProperties;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RoomPropertiesImporterCSV {

    public static List<RoomProperties> importRoomsCSV(String csvPath) throws IOException {

        List<RoomProperties> roomPropertiesList = new ArrayList<>();
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
            if (columnName.equals("name")) {
                nameIndex = i;
            } else if (columnName.equals("capacity")) {
                capacityIndex = i;
            } else if (columnName.equals("has_computers")) {
                hasComputersIndex = i;
            } else if (columnName.equals("has_projector")) {
                hasProjectorIndex = i;
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
                    .setCapacity(capacity)
                    .setHasComputers(hasComputers)
                    .setHasProjector(hasProjector);

            for (int i = 0; i < columnNames.length; i++) {
                if (i != nameIndex && i != capacityIndex && i != hasComputersIndex && i != hasProjectorIndex) {
                    // This column is an extra attribute
                    roomBuilder.addExtra(columnNames[i].trim(), values[i].trim());
                }
            }

            RoomProperties roomProperties = roomBuilder.build();
            roomPropertiesList.add(roomProperties);
        }


        reader.close();
        return roomPropertiesList;

    }

}
