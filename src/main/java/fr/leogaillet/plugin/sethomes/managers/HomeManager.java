package fr.leogaillet.plugin.sethomes.managers;

import fr.leogaillet.plugin.sethomes.records.Home;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class HomeManager {

    private int limitOfHomes;
    private final File file;
    private YamlConfiguration configuration;
    private final UUID playerUUID;
    private final HashMap<String, UUID> homeHashMap = new HashMap<String, UUID>();
    private final HashMap<UUID, Home> uuidHomeHashMap = new HashMap<UUID, Home>();

    private static final HashMap<UUID, HomeManager> homeManagerHashMap = new HashMap<UUID, HomeManager>();

    public HomeManager(UUID playerUUID, File file) {
        homeManagerHashMap.put(playerUUID, this);
        this.playerUUID = playerUUID;
        this.file = file;
    }

    public static HomeManager getFromPlayerUUID(UUID playerUUID) {
        return homeManagerHashMap.get(playerUUID);
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    public void loadConfiguration() throws IOException, InvalidConfigurationException {
        this.configuration = new YamlConfiguration();
        this.configuration.load(this.file);

        for(String name : (List<String>) configuration.getList("storage")) {

        }

    }

    public void saveConfiguration() throws IOException {
        this.configuration.save(this.file);
    }

    public boolean isHomeExists(final String name) {
        return homeHashMap.containsKey(name);
    }

    public boolean isHomeExists(final UUID uuid) {
        return uuidHomeHashMap.containsKey(uuid);
    }

    public HashMap<String, UUID> getHomeMap() {
        return homeHashMap;
    }

    public List<Home> listHomes(boolean showHidden) {
        List<Home> homes = new ArrayList<Home>();
        for (Home home : uuidHomeHashMap.values()) {
            if (homeHashMap.containsValue(home.getUUID()) || showHidden)
                homes.add(home);
        }
        return homes;
    }

    public void loadHome(final UUID uuid) {
        Location location = configuration.getLocation("storage." + uuid.toString() + ".location");
        String name = configuration.getString("storage." + uuid.toString() + ".name");

        Home home = new Home(uuid, name, location);
        this.homeHashMap.put(name, uuid);
    }

    public boolean addHome(final String name, final Location location) {
        if(isHomeExists(name)) return false;

        Home home = new Home(name, location);
        configuration.set("storage." + home.getUUID(), home);
        this.homeHashMap.put(name, home.getUUID());
        return true;
    }

    public void removeHome(final String name) {
        this.homeHashMap.remove(name);
    }

    public Home getHomeFromName(final String name) {
        return this.;
    }

}
