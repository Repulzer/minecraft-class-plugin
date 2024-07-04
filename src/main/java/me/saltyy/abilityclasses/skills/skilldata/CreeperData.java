package me.saltyy.abilityclasses.skills.skilldata;

import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.configdata.CooldownData;
import org.bukkit.configuration.ConfigurationSection;

public class CreeperData extends CooldownData {

    private int fuse;
    private float creeperPower;
    private double upwardsVelocity;
    private int slowfallDuration;

    protected CreeperData(Skill skill, ConfigurationSection configurationSection) {
        super(skill, configurationSection, "&2&lCreeper &fCooldown: %s seconds", 10);
        creeperPower = (float) configurationSection.getDouble("creeper_power", 1);
        fuse = (int) Math.ceil(configurationSection.getDouble("fuse", 2) * 20);
        slowfallDuration = (int) Math.ceil(configurationSection.getDouble("slowfall_duration", 7) * 20);
        upwardsVelocity = configurationSection.getDouble("upwardsVelocity", 2.5);
    }

    public int getFuse() {
        return fuse;
    }

    public float getCreeperPower() {
        return creeperPower;
    }

    public double getUpwardsVelocity() {
        return upwardsVelocity;
    }

    public int getSlowfallDuration() {
        return slowfallDuration;
    }
}
