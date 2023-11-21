package raf.sk_schedule.model.location_node;


import raf.sk_schedule.exception.ScheduleException;

import java.util.HashMap;
import java.util.Map;

import static raf.sk_schedule.util.exporter.ScheduleExporterJSON.serializeObject;


public class RoomProperties {

    private String name;
    private int capacity;
    private int hasComputers;
    private boolean hasProjector;

    private final Map<String, Object> attributes;

    private RoomProperties(String name, int capacity, int hasComputers, boolean hasProjector, Map<String, Object> attributes) {
        this.name = name;
        this.capacity = capacity;
        this.hasComputers = hasComputers;
        this.hasProjector = hasProjector;
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "name: " + name + " capacity: " + capacity + " has_computers: " + hasComputers + " has projector: " + hasProjector + " " + serializeObject(attributes);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RoomProperties)
            return ((RoomProperties) obj).name.equals(name);
        if (obj instanceof String)
            return obj.equals(name);
        return false;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public int hasComputers() {
        return hasComputers;
    }

    public boolean hasProjector() {
        return hasProjector;
    }

    public boolean hasAttribute(String attributeName) {
        return attributes.containsKey(attributeName);
    }

    public Object getAttribute(String attributeName) {
        return attributes.get(attributeName);
    }

    public Map<String, Object> getAttributes() {
        return new HashMap<>(attributes);
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setHasComputers(int hasComputers) {
        this.hasComputers = hasComputers;
    }

    public void setHasProjector(boolean hasProjector) {
        this.hasProjector = hasProjector;
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public static class Builder {
        private String name;
        private int capacity;
        private int hasComputers;
        private boolean hasProjector;
        private final Map<String, Object> attributes;

        public Builder() {
            // Set default values or customize them as needed
            capacity = 0;
            hasComputers = 0;
            hasProjector = false;
            attributes = new HashMap<>();
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCapacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder setHasComputers(int hasComputers) {
            this.hasComputers = hasComputers;
            return this;
        }

        public Builder setHasProjector(boolean hasProjector) {
            this.hasProjector = hasProjector;
            return this;
        }

        public Builder setAttribute(String attributeName, Object attributeValue) {

            this.attributes.put(attributeName, attributeValue);
            return this;
        }


        public RoomProperties build() {
            if (name == null || name.isEmpty())
                throw new ScheduleException("Room description has to have a name.");

            return new RoomProperties(name, capacity, hasComputers, hasProjector, attributes);
        }
    }

}
