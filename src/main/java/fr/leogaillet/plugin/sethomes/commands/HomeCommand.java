package fr.leogaillet.plugin.sethomes.commands;

import fr.leogaillet.plugin.sethomes.managers.HomeManager;
import fr.leogaillet.plugin.sethomes.managers.PlayerManager;
import fr.leogaillet.plugin.sethomes.records.Home;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand extends AbstractHomeCommand {

    public HomeCommand(PlayerManager playerManager) {
        super(playerManager);
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(command.getName().equalsIgnoreCase("home")) {

            if(!(commandSender instanceof Player)) {
                commandSender.sendMessage("Vous devez être un joueur !");
                return false;
            }

            if(args.length == 1) {

                Player player = (Player) commandSender;
                HomeManager manager = getPlayerManager().getHomeManager(player.getUniqueId());

                String name = args[0].toLowerCase();
                if(!manager.isHomeExists(name)) {
                    player.sendMessage("Vous ne pouvez pas vous téléporter à une location qui n'existe pas !");
                    return false;
                }

                Home home = manager.getHome(name);

                player.teleport(home.getLocation());
                player.sendMessage("Vous avez été téléporté à votre home '"+name+"'");

                return true;

            }

            else {

                commandSender.sendMessage("Usage : /home <name>");
                return false;

            }

        }

        return false;
    }

}
