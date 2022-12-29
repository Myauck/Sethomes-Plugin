package fr.leogaillet.plugin.sethomes.commands;

import fr.leogaillet.plugin.sethomes.managers.HomeManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class DelHomeCommand extends AbstractCommand {

    public DelHomeCommand(PluginCommand pluginCommand) {
        super(pluginCommand);
        setUsage("<name>");
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(command.getName().equalsIgnoreCase("delhome")) {

            if(!(commandSender instanceof Player)) {
                commandSender.sendMessage(ChatColor.RED + "Vous devez être un joueur !");
                return true;
            }

            if(args.length != 1)
                return false;

            Player player = (Player) commandSender;
            HomeManager homeManager = HomeManager.getInstance(player.getUniqueId());

            String name = args[0].toLowerCase();
            if(!homeManager.exists(name)) {
                player.sendMessage(ChatColor.RED + "Vous n'avez aucune location qui porte le nom "+ChatColor.GRAY+name+ChatColor.RED+" !");
                return true;
            }

            homeManager.removeHome(name);

            if(homeManager.exists(name)) {
                player.sendMessage(ChatColor.RED + "Une erreur est survenue ! La location "+ChatColor.GRAY+name+ChatColor.RED+" n'a pas pu être supprimée !");
                return true;
            }

            player.sendMessage(ChatColor.DARK_GREEN + "Votre home "+ ChatColor.GREEN +name+ ChatColor.DARK_GREEN +" a été retiré !");

            return true;

        }

        return false;
    }

}
