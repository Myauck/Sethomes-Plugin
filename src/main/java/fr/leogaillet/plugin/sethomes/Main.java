package fr.leogaillet.plugin.sethomes;

import fr.leogaillet.plugin.sethomes.listeners.PlayerQuitListener;
import fr.leogaillet.plugin.sethomes.managers.PlayerManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {

    private PlayerManager playerManager;

    @Override
    public void onEnable() {
        this.getLogger().info("Enabling Sethomes Plugin");

        initManagers();
        initCommands();
        initListeners();

        super.onEnable();
    }

    private void initCommands() {
    }

    private void initListeners() {
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
    }

    private void initManagers() {
        File fileManager = new File(getDataFolder() + File.pathSeparator + "filemanager.yml");
        if(!fileManager.exists()) {
            try {
                fileManager.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        playerManager = new PlayerManager(fileManager);

        playerManager.createStorageFolder();
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
