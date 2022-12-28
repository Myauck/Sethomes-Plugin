package fr.leogaillet.plugin.sethomes.records;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Home implements ConfigurationSerializable {

    private final UUID uuid;
    private String name;
    private Location location;

    public Home(final UUID uuid, final String name, final Location location) {
        this.uuid = uuid;
        this.name = name;
        this.location = location;
    }

    public Home(final String name, final Location location) {
        this(UUID.randomUUID(), name, location);
    }

    public UUID getUUID() {
        return uuid;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Home deserialize(Map<String, Object> serializedHome) {
        UUID uuid = UUID.fromString((String) serializedHome.get("uuid"));
        String name = (String) serializedHome.get("name");
        Location location = Location.deserialize((Map<String, Object>) serializedHome.get("location"));

        return new Home(uuid, name, location);
    }

    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("uuid", this.uuid.toString());
        map.put("name", this.name);
        map.put("location", this.location.serialize());

        return map;
    }
}
