package raf.sk_schedule.util.exporter;

import raf.sk_schedule.model.RoomProperties;

import java.util.Map;

public class ScheduleSerializer {

    // Helper method to serialize attribute values in readable format
    public static String serializeObject(Object value) {
        if (value instanceof String) {
            return "\"" + value + "\"";
        } else if (value instanceof Number || value instanceof Boolean) {
            return value.toString();
        } else if (value instanceof RoomProperties)
            return ((RoomProperties) value).getName();
        return "\"\"";
    }

    public static String serializeMap(Map<?, ?> map) {
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
