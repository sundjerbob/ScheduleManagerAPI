package raf.sk_schedule.util.exporter;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.model.location_node.RoomProperties;
import raf.sk_schedule.model.schedule_node.ScheduleSlot;

import java.util.List;
import java.util.Map;

/**
 * The `ScheduleExporterJSON` class provides utility methods for exporting schedule data to JSON format.
 * It allows you to convert `ScheduleSlot`, `RoomProperties`, and other objects to JSON-formatted strings.
 */
public class ScheduleExporterJSON {

    /**
     * Serializes an object to its JSON representation.
     *
     * @param value The object to be serialized.
     * @return A JSON-formatted string representing the provided object.
     * @throws ScheduleException If the serialization of the object is not supported.
     */
    public static String serializeObject(Object value) {
        if (value == null)
            return "null";
        else if (value instanceof String)
            return "\"" + value + "\"";
        else if (value instanceof Number || value instanceof Boolean)
            return value.toString();
        else if (value instanceof Map<?, ?>)
            return mapToJSON((Map<?, ?>) value);
        else if (value instanceof List<?>)
            return listToJSON((List<?>) value);
        else if (value instanceof RoomProperties)
            return roomToJSON((RoomProperties) value);
        else if (value instanceof ScheduleSlot)
            return slotToJSON((ScheduleSlot) value);
        else
            throw new ScheduleException("Serialization of object of class: \"" + value.getClass() + "\" is not supported within schedule component!");
    }


    /**
     * Converts a list of objects to a JSON-formatted string.
     *
     * @param objects The list of objects to be converted.
     * @return A JSON-formatted string representing the provided list of objects.
     */
    private static String listToJSON(List<?> objects) {

        if (objects == null)
            return null;
        if (objects.isEmpty())
            return "[ ]";

        StringBuilder json = new StringBuilder("[ ");

        for (Object object : objects) {
            json.append(serializeObject(object)).append(",");
        }

        json.setLength(json.length() - 1); // Remove the trailing comma

        json.append(" ]");

        return json.toString();

    }

    /**
     * Converts a `ScheduleSlot` instance to a JSON-formatted string.
     *
     * @param slot The `ScheduleSlot` instance to be converted.
     * @return A JSON-formatted string representing the provided `ScheduleSlot`.
     */
    private static String slotToJSON(ScheduleSlot slot) {
        return "{ " +
                "\"startTime\": " + serializeObject(slot.getStartTime()) + "," +
                "\"endTime\": " + serializeObject(slot.getStartTime()) + "," +
                "\"duration\": " + serializeObject(slot.getDuration()) + "," +
                "\"location\": " + serializeObject(slot.getLocation()) + "," +
                "\"attributes\": " + serializeObject(slot.getAttributes()) +
                "}";
    }


    /**
     * Converts a `RoomProperties` instance to a JSON-formatted string.
     *
     * @param room The `RoomProperties` instance to be converted.
     * @return A JSON-formatted string representing the provided `RoomProperties`.
     */
    private static String roomToJSON(RoomProperties room) {
        return "{ " +
                "\"name\":" + serializeObject(room.getName()) + "," +
                "\"capacity\":" + serializeObject(room.getCapacity()) + "," +
                "\"hasComputers\":" + serializeObject(room.hasComputers()) + "," +
                "\"hasProjector\":" + serializeObject(room.hasProjector()) + "," +
                "\"extra\":" + serializeObject(room.getAttributes()) +
                "}";
    }

    /**
     * Converts a map to a JSON-formatted string.
     *
     * @param map The map to be converted.
     * @return A JSON-formatted string representing the provided map.
     */
    private static String mapToJSON(Map<?, ?> map) {
        StringBuilder result = new StringBuilder("{ ");
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            result
                    .append("\"")
                    .append(serializeObject(entry.getKey()))
                    .append("\" : ")
                    .append(serializeObject(entry.getValue()))
                    .append(",");
        }
        if (!map.isEmpty()) {
            result.setLength(result.length() - 1); // Remove the trailing comma
        }
        result.append("}");
        return result.toString();
    }

}
