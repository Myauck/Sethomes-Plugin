package fr.leogaillet.plugin.sethomes.commands;

import fr.leogaillet.plugin.sethomes.managers.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class AbstractHomeCommand implements CommandExecutor {

    private final PlayerManager playerManager;

    public AbstractHomeCommand(final PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    public abstract boolean onCommand(CommandSender commandSender, Command command, String s, String[] args);

}
