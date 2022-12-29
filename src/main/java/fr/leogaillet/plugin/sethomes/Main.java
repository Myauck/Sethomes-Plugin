package fr.leogaillet.plugin.sethomes;

import fr.leogaillet.plugin.sethomes.commands.DelHomeCommand;
import fr.leogaillet.plugin.sethomes.commands.HomeCommand;
import fr.leogaillet.plugin.sethomes.commands.HomesCommand;
import fr.leogaillet.plugin.sethomes.commands.SetHomeCommand;
import fr.leogaillet.plugin.sethomes.listeners.PlayerJoinListener;
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
        this.getCommand("sethome").setExecutor(new SetHomeCommand(playerManager));
        this.getCommand("delhome").setExecutor(new DelHomeCommand(playerManager));
        this.getCommand("homes").setExecutor(new HomesCommand(playerManager));
        this.getCommand("home").setExecutor(new HomeCommand(playerManager));
    }

    private void initListeners() {
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(playerManager), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(playerManager), this);
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

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
