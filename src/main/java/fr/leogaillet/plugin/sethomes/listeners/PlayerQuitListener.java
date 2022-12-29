package fr.leogaillet.plugin.sethomes.listeners;

import fr.leogaillet.plugin.sethomes.managers.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final PlayerManager playerManager;

    public PlayerQuitListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        playerManager.getHomeManager(player.getUniqueId()).saveConfiguration();

    }

}
