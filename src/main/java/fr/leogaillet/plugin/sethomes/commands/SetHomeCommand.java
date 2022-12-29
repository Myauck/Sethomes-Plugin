package fr.leogaillet.plugin.sethomes.commands;

import fr.leogaillet.plugin.sethomes.managers.HomeManager;
import fr.leogaillet.plugin.sethomes.managers.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommand extends AbstractHomeCommand {

    public SetHomeCommand(PlayerManager playerManager) {
        super(playerManager);
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(command.getName().equalsIgnoreCase("sethome")) {

            if(!(commandSender instanceof Player)) {
                commandSender.sendMessage("Vous devez être un joueur !");
                return false;
            }

            if(args.length == 1) {

                Player player = (Player) commandSender;
                HomeManager manager = getPlayerManager().getHomeManager(player.getUniqueId());

                String name = args[0].toLowerCase();
                if(manager.isHomeExists(name)) {
                    player.sendMessage("Vous avez déjà une location qui porte ce nom !");
                    return false;
                }

                boolean added = manager.addHome(name, player.getLocation());
                if(!added) {
                    player.sendMessage("Une erreur est survenue, vous ne pouvez pas créer un home");
                    return false;
                }

                manager.saveConfiguration();
                player.sendMessage("Votre home a été créé sous le nom de '"+name+"' !");
                return true;

            }

            else {

                commandSender.sendMessage("Usage : /sethome <name>");
                return false;

            }

        }

        return false;
    }

}
