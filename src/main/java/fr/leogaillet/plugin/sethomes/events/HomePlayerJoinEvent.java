package fr.leogaillet.plugin.sethomes.events;

import fr.leogaillet.plugin.sethomes.managers.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.io.File;

public class HomePlayerJoinEvent extends Event {

    private final Player player;
    private static final HandlerList HANDLERS = new HandlerList();

    public HomePlayerJoinEvent(File pluginFolder, Player player) {
        this.player = player;
        PlayerManager playerManager = new PlayerManager(
                new File(pluginFolder + File.separator + player.getUniqueId() + ".yml"),
                player
        );
        playerManager.registerPlayer();
        playerManager.loadConfiguration();
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

}
