package me.saltyy.abilityclasses.commands;

import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.Playerclass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HeroCMD implements CommandExecutor, TabExecutor {

    private PowersHandler powersHandler;
    private final String noPermission = ChatColor.translateAlternateColorCodes('&', "&4You do not have permission to use this power!");
    public HeroCMD(PowersHandler powersHandler) {
        this.powersHandler = powersHandler;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("playerclasses.ability")) {
            if (args.length >= 1) {
                Playerclass power = powersHandler.getPlayerclass(args[0]);
                if (power == null) {
                    return false;
                }
                if (!sender.hasPermission("playerclasses.ability." + power.toString().toLowerCase())) {
                    sender.sendMessage(noPermission);
                }
                Player player;
                if (args.length >= 2) {
                    player = Bukkit.getPlayer(args[1]);
                    if (player == null) {
                        return false;
                    }
                }
                else {
                    if (sender instanceof Player) {
                        player = (Player) sender;
                    }
                    else {
                        return false;
                    }
                }
                powersHandler.setHero(player, power);
            }
        }
        else {
            sender.sendMessage(noPermission);
        }
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
