package raf.sk_schedule.util.exporter;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.model.location.RoomProperties;
import raf.sk_schedule.model.schedule.ScheduleSlot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ScheduleExporterJSON {


    public static String listToJSON(List<?> objects) {

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

    public static String slotToJSON(ScheduleSlot slot) {
        return "{ " +
                "\"startTime\": " + serializeObject(slot.getStartTime()) + "," +
                "\"endTime\": " + serializeObject(slot.getStartTime()) + "," +
                "\"duration\": " + serializeObject(slot.getDuration()) + "," +
                "\"location\": " + serializeObject(slot.getLocation()) + "," +
                "\"attributes\": " + serializeObject(slot.getAttributes()) +
                "}";
    }


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

    public static String roomToJSON(RoomProperties room) {
        return "{ " +
                "\"name\":" + serializeObject(room.getName()) + "," +
                "\"capacity\":" + serializeObject(room.getCapacity()) + "," +
                "\"hasComputers\":" + serializeObject(room.hasComputers()) + "," +
                "\"hasProjector\":" + serializeObject(room.hasProjector()) + "," +
                "\"extra\":" + serializeObject(room.getAttributes()) +
                "}";
    }

    public static String mapToJSON(Map<?, ?> map) {
        StringBuilder result = new StringBuilder("{ ");
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            result.append("\"")
                    .append(serializeObject(entry.getKey()))
                    .append("\" : ")
                    .append(serializeObject(entry.getValue()))
                    .append(",");
        }
        if (!map.isEmpty()) {
            result.setLength(result.length() - 1); // Remove the trailing comma and \n
        }
        result.append("}");
        return result.toString();
    }

}
