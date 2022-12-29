package fr.leogaillet.plugin.sethomes.managers;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PlayerManager {

    private static final HashMap<UUID, PlayerManager> instances = new HashMap<UUID, PlayerManager>();
    private final File playerFile;
    private final Player player;
    private final HomeManager homeManager;

    public PlayerManager(File playerFile, Player player) {
        this.playerFile = playerFile;
        this.player = player;
        this.homeManager = new HomeManager(player);
        createIfNotExists();
    }

    public void registerPlayer() {
        instances.put(player.getUniqueId(), this);
    }

    public void unregisterPlayer() {
        instances.remove(player.getUniqueId());
    }

    public static PlayerManager getInstance(UUID playerUUID) {
        return instances.get(playerUUID);
    }

    public void saveConfiguration() {
        YamlConfiguration configuration = new YamlConfiguration();
        for (Map.Entry<String, Location> home : homeManager.getHomeMap().entrySet()) {
            configuration.set("homes."+home.getKey(), home.getValue());
        }
        try {
            configuration.save(playerFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadConfiguration() {
        YamlConfiguration configuration = new YamlConfiguration();
        try {
            configuration.load(playerFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        ConfigurationSection configurationSection = configuration.getConfigurationSection("homes");
        if(configurationSection != null) {
            for(String homeName : configurationSection.getKeys(false)) {
                homeManager.addHome(homeName, configuration.getLocation("homes."+homeName));
            }
        }
    }

    private void createIfNotExists() {
        if(!playerFile.exists()) {
            try {
                playerFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
