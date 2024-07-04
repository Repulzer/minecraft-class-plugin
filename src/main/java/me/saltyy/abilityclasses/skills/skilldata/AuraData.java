package me.saltyy.abilityclasses.skills.skilldata;

import me.saltyy.abilityclasses.skills.Skill;
import org.bukkit.configuration.ConfigurationSection;

public class AuraData extends PotionEffectData {

    private double diameter;

    protected AuraData(Skill skill, ConfigurationSection configurationSection) {
        super(skill, configurationSection);
        diameter = configurationSection.getDouble("radius", 5) * 2;
        super.createPotion(getPotionEffect().getType(), configurationSection.getInt("duration", 200), potionEffect.getAmplifier());
    }

    public double getDiameter() {
        return diameter;
    }
}
