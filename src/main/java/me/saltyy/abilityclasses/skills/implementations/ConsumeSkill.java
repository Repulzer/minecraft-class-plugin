package me.saltyy.abilityclasses.skills.implementations;

import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.Playerclass;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.ConsumeData;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class ConsumeSkill extends SkillImplementation {

    public ConsumeSkill(PowersHandler powersHandler) {
        super(powersHandler);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        Player player = e.getPlayer();
        Playerclass playerclass = powersHandler.getPlayerclass(player);
        Collection<SkillData> skillDatas = playerclass.getSkillData(Skill.CONSUME);
        for (SkillData skillData : skillDatas) {
            ConsumeData consumeData = (ConsumeData) skillData;
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType() == consumeData.getMaterial()) {
                if (consumeData != null) {
                    player.addPotionEffect(consumeData.getPotionEffect());
                }
                player.setFoodLevel(player.getFoodLevel() + consumeData.getHunger());
                if (item.getAmount() == 1) {
                    player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                }
                item.setAmount(item.getAmount() - 1);
            }
        }
    }


}
