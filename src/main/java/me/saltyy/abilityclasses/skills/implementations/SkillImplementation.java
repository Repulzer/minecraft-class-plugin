package me.saltyy.abilityclasses.skills.implementations;

import me.saltyy.abilityclasses.PowersHandler;
import org.bukkit.event.Listener;

public abstract class SkillImplementation implements Listener {

    protected PowersHandler powersHandler;

    public SkillImplementation(PowersHandler powersHandler) {
        this.powersHandler = powersHandler;
    }

    public PowersHandler getPowersHandler() {
        return powersHandler;
    }

}
