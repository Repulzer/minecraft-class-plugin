package me.saltyy.abilityclasses;

import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;

public class ParticleData {

    private boolean isHelix;
    private double radius;
    private int duration;
    private Particle particle;

    public ParticleData(ConfigurationSection configurationSection) {
        isHelix = configurationSection.getBoolean("isHelix", true);
        radius = configurationSection.getDouble("radius", 1);
        duration = (int) Math.round(configurationSection.getDouble("duration", 2.5D) * 20);
        particle = Particle.valueOf(configurationSection.getString("particleType", "PORTAL"));
    }

    public boolean isHelix() {
        return isHelix;
    }

    public double getRadius() {
        return radius;
    }

    public int getDuration() {
        return duration;
    }

    public Particle getParticle() {
        return particle;
    }
}
