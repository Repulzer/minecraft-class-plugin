package me.xemor.superheroes2.skills.skilldata;

import me.xemor.superheroes2.skills.Skill;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

public class ConsumeData extends PotionEffectData {

    private Material material;
    private int hunger;

    protected ConsumeData(Skill skill, ConfigurationSection configurationSection) {
        super(skill, configurationSection);
        material = Material.valueOf(configurationSection.getString("material", "DIRT"));
        hunger = configurationSection.getInt("hunger", 0);
    }

    public int getHunger() {
        return hunger;
    }

    public Material getMaterial() {
        return material;
    }

}
