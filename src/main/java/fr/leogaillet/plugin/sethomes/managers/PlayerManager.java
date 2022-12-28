package fr.leogaillet.plugin.sethomes.managers;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerManager {

    private final String STORAGE_FOLDER = "Homes";
    private final File file;
    private YamlConfiguration yamlConfiguration;

    private final HashMap<UUID, HomeManager> homeManagerHashMap = new HashMap<UUID, HomeManager>();

    public PlayerManager(final File file) {
        this.file = file;

    }

    public void createStorageFolder() {
        File folder = new File(file.getParentFile() + File.pathSeparator + STORAGE_FOLDER);
        if(!folder.exists()) {
            folder.mkdirs();
        }
    }

    public File getFile() {
        return file;
    }
    private void addHomeManager(UUID pUuid, String uuid) {
        HomeManager pHM = new HomeManager(pUuid, new File(
                file.getParentFile() + File.pathSeparator + STORAGE_FOLDER + File.pathSeparator + uuid + ".yml"
        ));
        homeManagerHashMap.put(pUuid, pHM);
    }


    public void loadConfiguration() {
        yamlConfiguration = new YamlConfiguration();
        try {
            yamlConfiguration.load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        List<String> uuidList = yamlConfiguration.getStringList("files");
        for (String uuid : uuidList) {
            UUID pUUID = UUID.fromString(uuid);
            addHomeManager(pUUID, uuid);
        }
    }

    public void saveConfiguration() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isUUIDKnown(UUID uuid) {
        return homeManagerHashMap.containsKey(uuid);
    }

    public void createHomeManager(UUID uuid) {
        List<String> uuids = yamlConfiguration.getStringList("files");
        uuids.add(uuid.toString());
        yamlConfiguration.set("files", uuids);
        addHomeManager(uuid, uuid.toString());
    }

    public HomeManager getHomeManager(UUID uuid) {
        return homeManagerHashMap.get(uuid);
    }

}
