package raf.sk_schedule.util.exporter;

import raf.sk_schedule.exception.ScheduleException;
import raf.sk_schedule.model.RoomProperties;
import raf.sk_schedule.model.ScheduleSlot;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static raf.sk_schedule.util.exporter.ScheduleSerializer.serializeValue;

public class ScheduleExporterJSON {


    public static String listToJSON(List<?> objects) throws ParseException {
        if (objects == null)
            return null;
        if (objects.isEmpty())
            return "[ ]";


        if (objects.get(0) instanceof ScheduleSlot) {
            List<ScheduleSlot> scheduleSlots = new ArrayList<>();
            for (Object obj : objects) {
                scheduleSlots.add((ScheduleSlot) obj);
            }

            StringBuilder json = new StringBuilder("[");

            for (ScheduleSlot slot : scheduleSlots) {
                json.append(slotToJSON(slot)).append(",");
            }

            json.setLength(json.length() - 1); // Remove the trailing comma

            json.append("]");

            return json.toString();

        } else
            throw new ScheduleException("ScheduleExporter: \"I support only serialization of ScheduleSlot objects!\"");

    }

    public static String slotToJSON(ScheduleSlot slot) throws ParseException {
        StringBuilder jsonSlot = new StringBuilder();
        jsonSlot.append("{");
        jsonSlot.append("\"start\":\"").append(slot.getStartAsString()).append("\",");
        jsonSlot.append("\"duration\":").append(slot.getDuration()).append(",");
        jsonSlot.append("\"location\":").append(roomToJSON(slot.getLocation())).append(",");

        // Serialize attributes
        jsonSlot.append("\"attributes\":{");
        for (Map.Entry<String, Object> entry : slot.getAttributes().entrySet()) {
            jsonSlot.append("\"").append(entry.getKey()).append("\":").append(serializeValue(entry.getValue())).append(",");
        }
        // Remove the trailing comma
        if (!slot.getAttributes().isEmpty()) {
            jsonSlot.setLength(jsonSlot.length() - 1);
        }
        jsonSlot.append("}");
        jsonSlot.append("}");
        return jsonSlot.toString();
    }


    public static String roomToJSON(RoomProperties room) {
        StringBuilder jsonList = new StringBuilder();
        jsonList.append("{");
        jsonList.append("\"name\":\"").append(room.getName()).append("\",");
        jsonList.append("\"capacity\":").append(room.getCapacity()).append(",");
        jsonList.append("\"hasComputers\":").append(room.hasComputers()).append(",");
        jsonList.append("\"hasProjector\":").append(room.hasProjector()).append(",");

        // Serialize extra attributes
        jsonList.append("\"extra\":{");
        for (Map.Entry<String, Object> entry : room.getExtra().entrySet()) {
            jsonList.append("\"").append(entry.getKey()).append("\":").append(serializeValue(entry.getValue())).append(",");
        }
        // Remove the trailing comma
        if (!room.getExtra().isEmpty()) {
            jsonList.setLength(jsonList.length() - 1);
        }
        jsonList.append("}");
        jsonList.append("}");
        return jsonList.toString();
    }




}
