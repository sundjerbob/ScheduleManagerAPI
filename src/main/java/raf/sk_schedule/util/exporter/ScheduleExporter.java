package raf.sk_schedule.util.exporter;

import raf.sk_schedule.model.RoomProperties;
import raf.sk_schedule.model.ScheduleSlot;

import java.text.ParseException;
import java.util.Map;

public class ScheduleExporter {


    public static String slotToJSON(ScheduleSlot slot) throws ParseException {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"start\":\"").append(slot.getStartAsString()).append("\",");
        json.append("\"duration\":").append(slot.getDuration()).append(",");
        json.append("\"location\":").append(roomToJSON(slot.getLocation())).append(",");

        // Serialize attributes
        json.append("\"attributes\":{");
        for (Map.Entry<String, Object> entry : slot.getAttributes().entrySet()) {
            json.append("\"").append(entry.getKey()).append("\":").append(serializeValue(entry.getValue())).append(",");
        }
        // Remove the trailing comma
        if (!slot.getAttributes().isEmpty()) {
            json.setLength(json.length() - 1);
        }
        json.append("}");

        json.append("}");
        return json.toString();
    }

    public static String roomToJSON(RoomProperties room) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"name\":\"").append(room.getName()).append("\",");
        json.append("\"capacity\":").append(room.getCapacity()).append(",");
        json.append("\"hasComputers\":").append(room.hasComputers()).append(",");
        json.append("\"hasProjector\":").append(room.hasProjector()).append(",");

        // Serialize extra attributes
        json.append("\"extra\":{");
        for (Map.Entry<String, Object> entry : room.getExtra().entrySet()) {
            json.append("\"").append(entry.getKey()).append("\":").append(serializeValue(entry.getValue())).append(",");
        }
        // Remove the trailing comma
        if (!room.getExtra().isEmpty()) {
            json.setLength(json.length() - 1);
        }
        json.append("}");

        json.append("}");
        return json.toString();
    }

    // Helper method to serialize attribute values to JSON
    private static String serializeValue(Object value) {
        if (value instanceof String) {
            return "\"" + value + "\"";
        } else if (value instanceof Number || value instanceof Boolean) {
            return value.toString();
        }
        // Handle other data types as needed
        return "\"\"";
    }

    public static String mapToString(Map<?, ?> map) {
        StringBuilder result = new StringBuilder("{");
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            result.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
        }
        if (!map.isEmpty()) {
            result.setLength(result.length() - 2); // Remove the trailing comma and space
        }
        result.append("}");
        return result.toString();
    }
}
