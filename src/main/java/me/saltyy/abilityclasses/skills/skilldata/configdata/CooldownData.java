package me.saltyy.abilityclasses.skills.skilldata.configdata;

import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

public abstract class CooldownData extends SkillData implements Cooldown {

    private double cooldown;
    private String cooldownMessage;

    protected CooldownData(Skill skill, ConfigurationSection configurationSection, String defaultCooldownMessage, int defaultCooldown) {
        super(skill, configurationSection);
        cooldown = configurationSection.getDouble("cooldown", defaultCooldown);
        cooldownMessage = ChatColor.translateAlternateColorCodes('&', configurationSection.getString("cooldownMessage", defaultCooldownMessage));
    }

    @Override
    public double getCooldown() {
        return cooldown;
    }

    @Override
    public String getCooldownMessage() {
        return cooldownMessage;
    }
}
