package fr.leogaillet.plugin.sethomes.listeners;

import fr.leogaillet.plugin.sethomes.managers.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private PlayerManager playerManager;

    public PlayerJoinListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if(!playerManager.isUUIDKnown(p.getUniqueId())) {
            playerManager.createHomeManager(p.getUniqueId());
        }

    }

}
