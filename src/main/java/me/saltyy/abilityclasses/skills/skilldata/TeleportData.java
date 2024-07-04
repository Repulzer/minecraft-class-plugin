package me.saltyy.abilityclasses.skills.skilldata;

import me.saltyy.abilityclasses.ParticleData;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.configdata.CooldownData;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportData extends CooldownData {

    private boolean leftClick;
    private int distance;
    private double yAxisMultiplier;
    private PlayerTeleportEvent.TeleportCause teleportCause;
    private ParticleData particleData;
    private Material teleportItem;

    protected TeleportData(Skill skill, ConfigurationSection configurationSection) {
        super(skill, configurationSection, "&5&lTeleport &7has %s seconds until it can be used again!", 10);
        leftClick = configurationSection.getBoolean("leftClick", true);
        distance = configurationSection.getInt("distance", 30);
        yAxisMultiplier = configurationSection.getDouble("yAxisDistanceMultiplier", 1);
        teleportCause = PlayerTeleportEvent.TeleportCause.valueOf(configurationSection.getString("teleportCause", "ENDER_PEARL"));
        ConfigurationSection particleSection = configurationSection.getConfigurationSection("particle");
        if (particleSection == null) {
            particleSection = configurationSection;
        }
        particleData = new ParticleData(particleSection);
        teleportItem = Material.valueOf(configurationSection.getString("teleportItem", "AIR"));
    }

    public boolean isLeftClick() {
        return leftClick;
    }

    public int getDistance() {
        return distance;
    }

    public double getyAxisMultiplier() {
        return yAxisMultiplier;
    }

    public PlayerTeleportEvent.TeleportCause getTeleportCause() {
        return teleportCause;
    }

    public ParticleData getParticleData() {
        return particleData;
    }

    public Material getTeleportItem() {
        return teleportItem;
    }
}
