package me.saltyy.abilityclasses.skills.skilldata;

import me.saltyy.abilityclasses.skills.Skill;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

public class StrongmanData extends SkillData {

    private double velocity;
    private double upwardsVelocity;
    private String tooMuscularMessage;
    private int maxPassengers;

    protected StrongmanData(Skill skill, ConfigurationSection configurationSection) {
        super(skill, configurationSection);
        velocity = configurationSection.getDouble("velocity", 2.5);
        upwardsVelocity = configurationSection.getDouble("upwardsVelocity", 1);
        tooMuscularMessage = ChatColor.translateAlternateColorCodes('&', configurationSection.getString("tooMuscularMessage", " &fis too strong to sit in a vehicle!"));
        maxPassengers = configurationSection.getInt("maxPassengers", 10);
    }

    public double getVelocity() {
        return velocity;
    }

    public double getUpwardsVelocity() {
        return upwardsVelocity;
    }

    public String getTooMuscularMessage() {
        return tooMuscularMessage;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }
}
