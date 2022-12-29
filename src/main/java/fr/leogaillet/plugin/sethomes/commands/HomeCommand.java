package fr.leogaillet.plugin.sethomes.commands;

import fr.leogaillet.plugin.sethomes.managers.HomeManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class HomeCommand extends AbstractCommand {

    public HomeCommand(PluginCommand pluginCommand) {
        super(pluginCommand);
        setUsage("<name>");
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(command.getName().equalsIgnoreCase("home")) {

            if(!(commandSender instanceof Player)) {
                commandSender.sendMessage(ChatColor.RED+"Vous devez être un joueur !");
                return true;
            }

            if(args.length != 1)
                return false;

            Player player = (Player) commandSender;
            HomeManager homeManager = HomeManager.getInstance(player.getUniqueId());

            String name = args[0].toLowerCase();
            if(!homeManager.exists(name)) {
                player.sendMessage(ChatColor.RED+"Vous ne pouvez pas vous téléporter à une location qui n'existe pas !");
                return true;
            }

            Location location = homeManager.getHome(name);

            player.teleport(location);
            player.sendMessage(ChatColor.DARK_GREEN+"Vous avez été téléporté à votre location "+ChatColor.GREEN+name+ChatColor.DARK_GREEN+" !");

            return true;

        }

        return false;
    }

}
