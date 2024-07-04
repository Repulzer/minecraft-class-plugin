package me.saltyy.abilityclasses.events;

import me.saltyy.abilityclasses.Playerclass;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerGainedPlayerclassEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private Player player;
    private Playerclass hero;

    public PlayerGainedPlayerclassEvent(Player player, Playerclass hero) {
        this.player = player;
        this.hero = hero;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return player;
    }

    public Playerclass getHero() {
        return hero;
    }
}
