package me.saltyy.abilityclasses.skills.skilldata;

import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.configdata.CooldownData;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

public class SlamData extends CooldownData {

    private Material hand;
    private double airCooldown;
    private int foodCost;
    private int minimumFood;
    private double diameterRadius;
    private double damage;

    protected SlamData(Skill skill, ConfigurationSection configurationSection) {
        super(skill, configurationSection, "&8&lSlam &fCooldown: %s seconds", 10);
        hand = Material.valueOf(configurationSection.getString("item", "AIR").toUpperCase());
        airCooldown = configurationSection.getDouble("airCooldown", 1);
        foodCost = configurationSection.getInt("foodCost", 0);
        minimumFood = configurationSection.getInt("minimumFood", 0);
        diameterRadius = configurationSection.getDouble("radius", 5) * 2;
        damage = configurationSection.getDouble("damage", 0);
    }

    public Material getHand() {
        return hand;
    }

    public double getAirCooldown() {
        return airCooldown;
    }

    public int getFoodCost() {
        return foodCost;
    }

    public int getMinimumFood() {
        return minimumFood;
    }

    public double getDiameterRadius() {
        return diameterRadius;
    }

    public double getDamage() {
        return damage;
    }

}
