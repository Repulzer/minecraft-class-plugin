package me.saltyy.abilityclasses.skills.skilldata;

import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.configdata.CooldownData;
import me.saltyy.abilityclasses.skills.skilldata.configdata.ItemStackData;
import me.saltyy.abilityclasses.skills.skilldata.configdata.ParticleData;
import me.saltyy.abilityclasses.skills.skilldata.configdata.SoundData;
import org.bukkit.configuration.ConfigurationSection;

public class GunData extends CooldownData {

    private double damage;
    private double maxDistance;
    private double bulletSize;
    private ItemStackData itemStackData;
    private SoundData shootSoundData;
    private ParticleData trailParticle;
    private ParticleData hitParticle;

    protected GunData(Skill skill, ConfigurationSection configurationSection) {
        super(skill, configurationSection, "&8&lGun &7has %s seconds left until it can be used again!", 2);
        maxDistance = configurationSection.getDouble("maxDistance", 64);
        bulletSize = configurationSection.getDouble("bulletSize", 1.0);
        damage = configurationSection.getDouble("damage", 5.0);
        ConfigurationSection itemSection = configurationSection.getConfigurationSection("item");
        if (itemSection != null) {
            itemStackData = new ItemStackData(itemSection);
        }
        ConfigurationSection shootSoundSection = configurationSection.getConfigurationSection("shootSound");
        if (shootSoundSection != null) {
            shootSoundData = new SoundData(shootSoundSection);
        }
        ConfigurationSection trailParticleSection = configurationSection.getConfigurationSection("trailParticle");
        if (trailParticleSection != null) {
            trailParticle = new ParticleData(trailParticleSection);
        }
        ConfigurationSection hitParticleSection = configurationSection.getConfigurationSection("hitParticle");
        if (hitParticleSection != null) {
            hitParticle = new ParticleData(hitParticleSection);
        }
    }

    public double getDamage() {
        return damage;
    }

    public ItemStackData getItemStackData() {
        return itemStackData;
    }

    public SoundData getShootSoundData() {
        return shootSoundData;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public double getBulletSize() {
        return bulletSize;
    }

    public ParticleData getTrailParticle() {
        return trailParticle;
    }

    public ParticleData getHitParticle() {
        return hitParticle;
    }
}
