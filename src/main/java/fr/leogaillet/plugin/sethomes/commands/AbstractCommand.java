package fr.leogaillet.plugin.sethomes.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

public abstract class AbstractCommand implements CommandExecutor {

    protected PluginCommand pluginCommand;

    public AbstractCommand(PluginCommand pluginCommand) {
        this.pluginCommand = pluginCommand;
        pluginCommand.setExecutor(this);
    }

    protected void setUsage(String usageParameters) {
        pluginCommand.setUsage(ChatColor.GOLD + "Usage: " + ChatColor.DARK_GREEN + "/" + pluginCommand.getName() + " " + ChatColor.GOLD + usageParameters);
    }

    public abstract boolean onCommand(CommandSender commandSender, Command command, String label, String[] arguments);

}
