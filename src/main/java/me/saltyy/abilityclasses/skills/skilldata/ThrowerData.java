package me.saltyy.abilityclasses.skills.skilldata;

import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.configdata.CooldownData;
import me.saltyy.abilityclasses.skills.skilldata.configdata.ItemStackData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class ThrowerData extends CooldownData {

    private int ammoCost;
    private ItemStack ammo;
    private EntityType entityType;
    private List<Action> actions;
    private AbstractArrow.PickupStatus canPickUp;
    private double velocity;
    private double damage;

    protected ThrowerData(Skill skill, ConfigurationSection configurationSection) {
        super(skill, configurationSection, "&7Your projectile has %s seconds remaining!", 0);
        ammoCost = configurationSection.getInt("ammoCost", 1);
        final ConfigurationSection ammoItemSection = configurationSection.getConfigurationSection("item");
        if (ammoItemSection != null) {
            ammo = new ItemStackData(ammoItemSection).getItem();
        }
        entityType = EntityType.valueOf(configurationSection.getString("projectile", "SNOWBALL").toUpperCase());
        actions = configurationSection.getStringList("actions").stream().map(Action::valueOf).collect(Collectors.toList());
        if (actions.isEmpty()) {
            actions.add(Action.RIGHT_CLICK_AIR);
            actions.add(Action.RIGHT_CLICK_BLOCK);
        }
        canPickUp = configurationSection.getBoolean("canPickUp", false) ? AbstractArrow.PickupStatus.ALLOWED : AbstractArrow.PickupStatus.CREATIVE_ONLY;
        velocity = configurationSection.getDouble("velocity", 1.4);
        damage = configurationSection.getDouble("damage", 3);
    }

    public int getAmmoCost() {
        return ammoCost;
    }

    public ItemStack getAmmo() {
        return ammo;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public List<Action> getActions() {
        return actions;
    }

    public AbstractArrow.PickupStatus canPickUp() {
        return canPickUp;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getDamage() {
        return damage;
    }

}
