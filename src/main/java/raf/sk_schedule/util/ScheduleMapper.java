package raf.sk_schedule.util;

import java.util.Map;

public class ScheduleMapper {

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
