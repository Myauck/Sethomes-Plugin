package fr.leogaillet.plugin.sethomes.listeners;

import fr.leogaillet.plugin.sethomes.events.HomePlayerQuitEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerQuit(PlayerQuitEvent e) {
        HomePlayerQuitEvent event = new HomePlayerQuitEvent(e.getPlayer());
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

}
