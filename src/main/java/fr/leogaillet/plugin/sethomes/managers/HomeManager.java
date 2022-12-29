package fr.leogaillet.plugin.sethomes.managers;

import fr.leogaillet.plugin.sethomes.records.Home;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class HomeManager {
    private final File file;
    private YamlConfiguration configuration;
    private ConfigurationSection visibleHomesSection, registeredHomeSection;
    private final HashMap<String, UUID> visibleHomes = new HashMap<String, UUID>();
    private final HashMap<UUID, Home> registeredHomes = new HashMap<UUID, Home>();

    public HomeManager(File file) {
        this.file = file;

        if(!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            createConfiguration();
        }

    }

    private void createConfiguration() {
        this.configuration = new YamlConfiguration();
        this.configuration.set("visible", new HashMap<String, String>());
        this.visibleHomesSection = this.configuration.getConfigurationSection("visible");
        this.configuration.set("registered", new HashMap<String, Home>());
        this.registeredHomeSection = this.configuration.getConfigurationSection("registered");
        saveConfiguration();
    }

    public void loadConfiguration() throws IOException, InvalidConfigurationException {
        this.configuration = new YamlConfiguration();
        this.configuration.load(this.file);

        this.visibleHomesSection = this.configuration.getConfigurationSection("visible");
        this.registeredHomeSection = this.configuration.getConfigurationSection("registered");

        for (Map.Entry<String, Object> e : visibleHomesSection.getValues(false).entrySet()) {
            String key = e.getKey();
            String value = (String) e.getValue();
            visibleHomes.put(key, UUID.fromString(value));
        }

        for (Map.Entry<String, Object> e : registeredHomeSection.getValues(false).entrySet()) {
            UUID uuid = UUID.fromString(e.getKey());
            ConfigurationSection home = (ConfigurationSection) e.getValue();
            Location location = home.getLocation("location");
            String name = home.getString("name");
            registeredHomes.put(uuid, new Home(uuid, name, location));
        }

    }

    public void saveConfiguration() {
        this.configuration.set("visible", this.visibleHomesSection);
        this.configuration.set("registered", this.registeredHomeSection);
        try {
            this.configuration.save(this.file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isHomeExists(final String name) {
        return visibleHomes.containsKey(name);
    }

    public HashMap<String, Home> getVisibleHomes() {
        HashMap<String, Home> map = new HashMap<String, Home>();
        for (Map.Entry<String, UUID> homeMap : visibleHomes.entrySet()) {
            map.put(homeMap.getKey(), registeredHomes.get(homeMap.getValue()));
        }
        return map;
    }

    public List<Home> listHomes(boolean showHidden) {
        List<Home> homes = new ArrayList<Home>();
        for (Home home : registeredHomes.values()) {
            if (visibleHomes.containsValue(home.getUUID()) || showHidden)
                homes.add(home);
        }
        return homes;
    }

    public boolean addHome(final String name, final Location location) {
        if(isHomeExists(name)) return false;

        Home home = new Home(name, location);

        this.visibleHomes.put(name, home.getUUID());
        visibleHomesSection.set(home.getName(), home.getUUID().toString());

        this.registeredHomes.put(home.getUUID(), home);
        registeredHomeSection.set(home.getUUID().toString(), home);

        return true;
    }

    public boolean removeHome(final String name) {
        if(!isHomeExists(name)) return false;

        this.visibleHomes.remove(name);
        visibleHomesSection.set(getHome(name).getUUID().toString(), null);

        return true;
    }

    public Home getHome(final UUID uuid) {
        return this.registeredHomes.get(uuid);
    }

    public Home getHome(final String name) {
        return getHome(visibleHomes.get(name));
    }

}
