package me.saltyy.abilityclasses.commands;

import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.Playerclass;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class Info implements Listener, CommandExecutor, TabExecutor {

    private PowersHandler powersHandler;

    public Info(PowersHandler powersHandler) {
        this.powersHandler = powersHandler;
    }

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            // Here we send message
            if (args.length >= 1) {
                Playerclass power = powersHandler.getPlayerclass(args[0]);

                if (power == null) {
                    sender.sendMessage("Selected Power doesn't exist.");

                    return false;
                }
                String abilityDescription = power.getAbilityInfo();
                
                sender.sendMessage(ChatColor.BOLD + "[Ability Info]: ");
                if(abilityDescription != null && !abilityDescription.trim().isEmpty()){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', abilityDescription));
                } else{
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "Currently no Info available"));
                }
            }

        }

        // If the player (or console) uses our command correct, we can return true
        return true;
        
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> heroesTabComplete = new ArrayList<>();
        List<String> heroNames = powersHandler.getNameToPlayerclass().values().stream().map(hero -> hero.getName()).collect(Collectors.toList());
        heroNames.add("None");
        if (args.length == 1) {
            String firstArg = args[0];
            for (Playerclass playerclass : powersHandler.getNameToPlayerclass().values()) {
                if (playerclass.getName().startsWith(firstArg) && sender.hasPermission("playerclasses.ability." + playerclass.getName().toLowerCase())) {
                    heroesTabComplete.add(playerclass.getName());
                }
            }
        }
        return heroesTabComplete;
    }

}
