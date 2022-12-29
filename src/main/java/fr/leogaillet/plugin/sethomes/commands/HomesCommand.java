package fr.leogaillet.plugin.sethomes.commands;

import fr.leogaillet.plugin.sethomes.managers.HomeManager;
import fr.leogaillet.plugin.sethomes.managers.PlayerManager;
import fr.leogaillet.plugin.sethomes.records.Home;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HomesCommand extends AbstractHomeCommand {

    public HomesCommand(PlayerManager playerManager) {
        super(playerManager);
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (command.getName().equalsIgnoreCase("homes")) {

            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("Vous devez être un joueur !");
                return false;
            }

            if (args.length == 0) {

                Player player = (Player) commandSender;
                HomeManager manager = getPlayerManager().getHomeManager(player.getUniqueId());

                List<Home> homes = manager.listHomes(false);

                if (homes.isEmpty()) {
                    player.sendMessage("Vous n'avez encore aucune location d'enregistrée !");
                    return false;
                }

                StringBuilder stringBuilder = new StringBuilder();
                int i = 1;
                for (Home home : homes) {
                    stringBuilder.append(home.getName());
                    if (i != homes.size())
                        stringBuilder.append(", ");
                    i++;
                }
                player.sendMessage("Vous avez les locations suivantes : ");
                player.sendMessage(stringBuilder.toString().trim());

                return true;

            } else {

                commandSender.sendMessage("Usage : /homes");
                return false;

            }

        }

        return false;
    }

}
