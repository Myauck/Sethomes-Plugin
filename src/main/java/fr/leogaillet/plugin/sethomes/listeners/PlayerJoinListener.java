package fr.leogaillet.plugin.sethomes.listeners;

import fr.leogaillet.plugin.sethomes.events.HomePlayerJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

public class PlayerJoinListener implements Listener {

    private final File pluginFolder;

    public PlayerJoinListener(final File pluginFolder) {
        this.pluginFolder = pluginFolder;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent e) {
        HomePlayerJoinEvent event = new HomePlayerJoinEvent(pluginFolder, e.getPlayer());
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

}
