package me.saltyy.abilityclasses.skills.skilldata;

import me.saltyy.abilityclasses.skills.Skill;
import org.bukkit.configuration.ConfigurationSection;

public class PickpocketData extends SkillData {

    private double rangeSquared;
    private boolean isSneaking;

    protected PickpocketData(Skill skill, ConfigurationSection configurationSection) {
        super(skill, configurationSection);
        rangeSquared = Math.pow(configurationSection.getDouble("range", 3), 2);
        isSneaking = configurationSection.getBoolean("isSneaking", true);
    }

    public double getRangeSquared() {
        return rangeSquared;
    }

    public boolean isSneaking() {
        return isSneaking;
    }
}
