package fr.leogaillet.plugin.sethomes.listeners;

import fr.leogaillet.plugin.sethomes.managers.HomeManager;
import fr.leogaillet.plugin.sethomes.managers.PlayerManager;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;

public class PlayerJoinListener implements Listener {

    private final PlayerManager playerManager;

    public PlayerJoinListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if(!playerManager.isUUIDKnown(p.getUniqueId()))
            playerManager.createHomeManager(p.getUniqueId());
        else
            playerManager.loadConfiguration();

        HomeManager homeManager = playerManager.getHomeManager(p.getUniqueId());
        try {
            homeManager.loadConfiguration();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InvalidConfigurationException ex) {
            throw new RuntimeException(ex);
        }

    }

}
