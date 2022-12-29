package fr.leogaillet.plugin.sethomes.commands;

import fr.leogaillet.plugin.sethomes.managers.HomeManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import java.util.Map;

public class HomesCommand extends AbstractCommand {

    public HomesCommand(PluginCommand pluginCommand) {
        super(pluginCommand);
        setUsage("");
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (command.getName().equalsIgnoreCase("homes")) {

            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(ChatColor.RED+"Vous devez être un joueur !");
                return true;
            }

            if (args.length != 0)
                return false;

            Player player = (Player) commandSender;
            HomeManager homeManager = HomeManager.getInstance(player.getUniqueId());

            Map<String, Location> homes = homeManager.getHomeMap();

            if (homes.isEmpty()) {
                player.sendMessage(ChatColor.RED+"Vous n'avez encore aucune location d'enregistrée !");
                return true;
            }

            StringBuilder stringBuilder = new StringBuilder();
            int i = 1;
            for (String homeName : homes.keySet()) {
                stringBuilder.append(ChatColor.DARK_GREEN);
                stringBuilder.append(homeName);
                if (i != homes.size()) {
                    stringBuilder.append(ChatColor.GOLD);
                    stringBuilder.append(", ");
                }
                i++;
            }

            player.sendMessage(ChatColor.GOLD+"Vous avez pouvez accédez aux locations suivantes : ");
            player.sendMessage(stringBuilder.toString().trim());

            return true;

        }

        return false;
    }

}
