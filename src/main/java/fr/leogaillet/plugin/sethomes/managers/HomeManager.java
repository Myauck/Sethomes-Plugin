package fr.leogaillet.plugin.sethomes.managers;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HomeManager {
    private static final HashMap<UUID, HomeManager> instances = new HashMap<UUID, HomeManager>();

    private final HashMap<String, Location> homes = new HashMap<String, Location>();

    public HomeManager(Player player) {
        instances.put(player.getUniqueId(), this);
    }

    public static HomeManager getInstance(UUID playerUUID) {
        return instances.get(playerUUID);
    }

    public void addHome(String name, Location location) {
        this.homes.put(name, location);
    }

    public void removeHome(String name) {
        this.homes.remove(name);
    }

    public boolean exists(String name) {
        return this.homes.containsKey(name);
    }

    public Location getHome(String name) {
        return this.homes.get(name);
    }

    public Map<String, Location> getHomeMap() {
        return homes;
    }

}
