package fr.leogaillet.plugin.sethomes.events;

import fr.leogaillet.plugin.sethomes.managers.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class HomePlayerQuitEvent extends Event {

    private final Player player;
    private static final HandlerList HANDLERS = new HandlerList();

    public HomePlayerQuitEvent(Player player) {
        this.player = player;
        PlayerManager playerManager = PlayerManager.getInstance(player.getUniqueId());
        playerManager.saveConfiguration();
        playerManager.unregisterPlayer();
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return player;
    }

}
