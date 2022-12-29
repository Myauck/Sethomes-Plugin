package fr.leogaillet.plugin.sethomes;

import fr.leogaillet.plugin.sethomes.commands.DelHomeCommand;
import fr.leogaillet.plugin.sethomes.commands.HomeCommand;
import fr.leogaillet.plugin.sethomes.commands.HomesCommand;
import fr.leogaillet.plugin.sethomes.commands.SetHomeCommand;
import fr.leogaillet.plugin.sethomes.events.HomePlayerJoinEvent;
import fr.leogaillet.plugin.sethomes.events.HomePlayerQuitEvent;
import fr.leogaillet.plugin.sethomes.listeners.PlayerJoinListener;
import fr.leogaillet.plugin.sethomes.listeners.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();

        if(!getDataFolder().exists())
            getDataFolder().mkdirs();

        this.getLogger().info("Enabling Sethomes Plugin");

        initManagers();
        initCommands();
        initListeners();

        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            new HomePlayerJoinEvent(getDataFolder(), player);
        }
    }

    private void initCommands() {
        new SetHomeCommand(getCommand("sethome"));
        new DelHomeCommand(getCommand("delhome"));
        new HomesCommand(getCommand("homes"));
        new HomeCommand(getCommand("home"));
    }

    private void initListeners() {
        this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(getDataFolder()), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
    }

    private void initManagers() {

        if(!getDataFolder().exists())
            getDataFolder().mkdir();

    }

    @Override
    public void onDisable() {
        super.onDisable();

        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            new HomePlayerQuitEvent(player);
        }
    }

}
