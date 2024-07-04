package me.saltyy.abilityclasses;

import me.saltyy.abilityclasses.events.PlayerGainedPlayerclassEvent;
import me.saltyy.abilityclasses.events.PlayerLostPlayerclassEvent;
import me.saltyy.abilityclasses.skills.Skill;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class PowersHandler implements Listener {

    private HashMap<UUID, Playerclass> uuidToPowers = new HashMap<>();
    FileConfiguration powersFile;
    AbilityClasses playerclasses;
    ConfigHandler configHandler;
    Playerclass erased = new Playerclass("ERASED", ChatColor.translateAlternateColorCodes('&', "&7&lERASED"), "Their power has been erased", "");
    Playerclass noPower = new Playerclass("NOPOWER", ChatColor.translateAlternateColorCodes('&', "&e&lNOPOWER"), "They have no power", "");
    private HashMap<String, Playerclass> nameToPlayerclass = new HashMap<>();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e) {
        configHandler.loadPlayerHero(e.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        configHandler.saveCurrentPowers();
        uuidToPowers.remove(e.getPlayer().getUniqueId());
    }

    public PowersHandler(AbilityClasses playerclasses) {
        this.playerclasses = playerclasses;
    }

    public void setConfigHandler(ConfigHandler configHandler) {
        this.configHandler = configHandler;
        powersFile = configHandler.getCurrentPowersYAML();
    }

    public void registerHeroes(HashMap<String, Playerclass> nameToPlayerclass) {
        this.nameToPlayerclass = nameToPlayerclass;
        nameToPlayerclass.put("NOPOWER", noPower);
    }

    public void setHeroes(HashMap<UUID, Playerclass> playerHeroes) {
        this.uuidToPowers = playerHeroes;
    }

    public Playerclass getPlayerclass(Player player) {
        Playerclass hero = uuidToPowers.get(player.getUniqueId());
        if (player.getGameMode() == GameMode.SPECTATOR && !hero.hasSkill(Skill.PHASE)) {
            return noPower;
        }
        return hero;
    }


    public void setHero(Player player, Playerclass hero) {
        Playerclass currentHero = uuidToPowers.get(player.getUniqueId());
        if (currentHero != null) {
            PlayerLostPlayerclassEvent playerLostHeroEvent = new PlayerLostPlayerclassEvent(player, currentHero);
            Bukkit.getServer().getPluginManager().callEvent(playerLostHeroEvent);
        }
        uuidToPowers.put(player.getUniqueId(), hero);
        showHero(player, hero);
        configHandler.savePlayerclass(player, hero);
        if (currentHero != hero) {
            PlayerGainedPlayerclassEvent playerGainedPowerEvent = new PlayerGainedPlayerclassEvent(player, hero);
            Bukkit.getServer().getPluginManager().callEvent(playerGainedPowerEvent);
        }
    }

    public void temporarilyRemovePower(Player player, Player remover, int timeInTicks) {
        final Playerclass oldPower = uuidToPowers.get(player.getUniqueId());
        uuidToPowers.replace(player.getUniqueId(), erased);
        if (remover != null) {
            remover.sendMessage(ChatColor.BOLD + player.getName() + " has had their power erased temporarily!");
        }
        player.sendMessage(ChatColor.BOLD + player.getName() + " has had their power erased temporarily!");
        PlayerLostPlayerclassEvent playerLostPowerEvent = new PlayerLostPlayerclassEvent(player, oldPower);
        Bukkit.getServer().getPluginManager().callEvent(playerLostPowerEvent);
        showHero(player, erased);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (uuidToPowers.get(player.getUniqueId()) == erased) {
                    PlayerGainedPlayerclassEvent playerGainedPowerEvent = new PlayerGainedPlayerclassEvent(player, oldPower);
                    Bukkit.getServer().getPluginManager().callEvent(playerGainedPowerEvent);
                    uuidToPowers.put(player.getUniqueId(), oldPower);
                    Bukkit.broadcastMessage(ChatColor.BOLD + player.getName() + " has had their powers reinstated!");
                    showHero(player, oldPower);
                }
            }
        }.runTaskLater(playerclasses, timeInTicks);
    }

    public void setRandomHero(Player player) {
        Playerclass playerclass = getRandomHero(player);
        setHero(player, playerclass);
    }

    public Playerclass getRandomHero(Player player) {
        List<Playerclass> playerclasses = new ArrayList<>(nameToPlayerclass.values());
        Collections.shuffle(playerclasses);
        Playerclass newHero = noPower;
        for (Playerclass playerclass : playerclasses) {
            if (configHandler.areHeroPermissionsRequired() && !player.hasPermission(playerclass.getPermission())) {
                continue;
            }
            newHero = playerclass;
            break;
        }
        return newHero;
    }

    public void showHero(Player player, Playerclass hero) {
        player.sendTitle(hero.getColouredName(), hero.getDescription(), 10, 100, 10);
        player.playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 0.5F, 1F);
        Bukkit.broadcastMessage(ChatColor.BOLD + player.getName() + " has gained the power of " + hero.getColouredName());
    }

    public HashMap<UUID, Playerclass> getUuidToPowers() {
        return uuidToPowers;
    }

    public Playerclass getPlayerclass(String name) {
        return nameToPlayerclass.get(name);
    }

    public AbilityClasses getPlugin() {
        return playerclasses;
    }

    public HashMap<String, Playerclass> getNameToPlayerclass() {
        return nameToPlayerclass;
    }

    public Playerclass getNoPower() {
        return noPower;
    }
}
