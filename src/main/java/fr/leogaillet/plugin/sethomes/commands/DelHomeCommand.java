package fr.leogaillet.plugin.sethomes.commands;

import fr.leogaillet.plugin.sethomes.managers.HomeManager;
import fr.leogaillet.plugin.sethomes.managers.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelHomeCommand extends AbstractHomeCommand {

    public DelHomeCommand(PlayerManager playerManager) {
        super(playerManager);
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(command.getName().equalsIgnoreCase("delhome")) {

            if(!(commandSender instanceof Player)) {
                commandSender.sendMessage("Vous devez être un joueur !");
                return false;
            }

            if(args.length == 1) {

                Player player = (Player) commandSender;
                HomeManager manager = getPlayerManager().getHomeManager(player.getUniqueId());

                String name = args[0].toLowerCase();
                if(!manager.isHomeExists(name)) {
                    player.sendMessage("Vous n'avez aucune location qui porte ce nom !");
                    return false;
                }

                boolean removed = manager.removeHome(name);
                if(!removed) {
                    player.sendMessage("Une erreur est survenue, vous ne pouvez pas supprimer cette location");
                    return false;
                }

                manager.saveConfiguration();
                player.sendMessage("Votre home '"+name+"' a été retiré !");
                return true;

            }

            else {

                commandSender.sendMessage("Usage : /delhome <name>");
                return false;

            }

        }

        return false;
    }

}
