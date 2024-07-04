package me.saltyy.abilityclasses.skills.implementations;

import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.Playerclass;
import me.saltyy.abilityclasses.events.PlayerLostPlayerclassEvent;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.DecoyData;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class DecoySkill extends SkillImplementation {

    HashMap<SkillData, HashMap<UUID, UUID>> playerToDecoy = new HashMap<>();
    NamespacedKey namespacedKey;

    public DecoySkill(PowersHandler powersHandler) {
        super(powersHandler);
        namespacedKey = new NamespacedKey(powersHandler.getPlugin(), "decoy");
    }

    @EventHandler
    public void disableSlots(PlayerArmorStandManipulateEvent e) {
        ArmorStand armorStand = e.getRightClicked();
        if (armorStand.getPersistentDataContainer().has(namespacedKey, PersistentDataType.INTEGER)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        Player player = e.getPlayer();
        Playerclass playerclass = powersHandler.getPlayerclass(player);
        Collection<SkillData> skillDatas = playerclass.getSkillData(Skill.DECOY);
        for (SkillData skillData : skillDatas) {
            DecoyData decoyData = (DecoyData) skillData;
            if (e.isSneaking()) {
                ArmorStand armorStand = createArmorStand(player, decoyData);
                HashMap<UUID, UUID> hashMap = playerToDecoy.getOrDefault(decoyData, new HashMap<>());
                hashMap.put(player.getUniqueId(), armorStand.getUniqueId());
                playerToDecoy.put(skillData, hashMap);
            }
            else {
                removeArmorStand(player, decoyData);
            }
        }
    }

    public void removeArmorStand(Player player, DecoyData decoyData) {
        HashMap<UUID, UUID> hashMap = playerToDecoy.getOrDefault(decoyData, new HashMap<>());
        UUID armorStandUUID = hashMap.get(player.getUniqueId());
        if (armorStandUUID != null) {
            Bukkit.getEntity(armorStandUUID).remove();
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
            player.removePotionEffect(PotionEffectType.SPEED);
            hashMap.remove(player.getUniqueId());
            playerToDecoy.put(decoyData, hashMap);
        }
    }

    public ArmorStand createArmorStand(Player player, DecoyData decoyData) {
        World world = player.getWorld();
        ArmorStand armorStand = world.spawn(player.getLocation(), ArmorStand.class);
        armorStand.setBasePlate(false);
        EntityEquipment entityEquipment = armorStand.getEquipment();
        entityEquipment.setHelmet(decoyData.getSkull(player));
        entityEquipment.setChestplate(createLeatherGear(Material.LEATHER_CHESTPLATE, decoyData));
        entityEquipment.setLeggings(createLeatherGear(Material.LEATHER_LEGGINGS, decoyData));
        entityEquipment.setBoots(createLeatherGear(Material.LEATHER_BOOTS, decoyData));
        armorStand.setInvulnerable(true);
        armorStand.setArms(true);
        armorStand.setCustomName("Decoy");
        armorStand.getPersistentDataContainer().set(namespacedKey, PersistentDataType.INTEGER, 0);
        return armorStand;
    }

    public ItemStack createLeatherGear(Material leatherArmor, DecoyData decoyData) {
        ItemStack itemStack = new ItemStack(leatherArmor);
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        leatherArmorMeta.setColor(decoyData.getColor());
        itemStack.setItemMeta(leatherArmorMeta);
        return itemStack;
    }

    @EventHandler
    public void onLost(PlayerLostPlayerclassEvent e) {
        Playerclass playerclass = e.getHero();
        Collection<SkillData> skillDatas = playerclass.getSkillData(Skill.DECOY);
        for (SkillData skillData : skillDatas) {
            removeArmorStand(e.getPlayer(), (DecoyData) skillData);
        }
    }

}
