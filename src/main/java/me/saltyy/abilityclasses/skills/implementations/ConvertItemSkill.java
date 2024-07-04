package me.saltyy.abilityclasses.skills.implementations;

import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.ConvertItemData;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;

public class ConvertItemSkill extends SkillImplementation {
    public ConvertItemSkill(PowersHandler powersHandler) {
        super(powersHandler);
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            Collection<SkillData> skillDatas = powersHandler.getPlayerclass(player).getSkillData(Skill.CONVERTITEM);
            for (SkillData skillData : skillDatas) {
                ConvertItemData convertItemData = (ConvertItemData) skillData;
                ItemStack itemStack = e.getItem().getItemStack();
                if (itemStack.isSimilar(convertItemData.getInputItem())) {
                    if (Math.random() < convertItemData.getChance()) {
                        ItemStack outputItem = convertItemData.getOutputItem().clone();
                        outputItem.setAmount(itemStack.getAmount());
                        e.getItem().remove();
                        e.setCancelled(true);
                        HashMap<Integer, ItemStack> leftovers = player.getInventory().addItem(outputItem);
                        World world = player.getWorld();
                        for (ItemStack item : leftovers.values()) {
                            world.dropItem(player.getLocation(), item);
                        }
                    }
                }
            }
        }
    }
}
