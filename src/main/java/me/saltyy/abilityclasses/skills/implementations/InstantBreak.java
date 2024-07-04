package me.saltyy.abilityclasses.skills.implementations;

import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.Playerclass;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.InstantBreakData;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class InstantBreak extends SkillImplementation {
    public InstantBreak(PowersHandler powersHandler) {
        super(powersHandler);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            Playerclass hero = getPowersHandler().getPlayerclass(e.getPlayer());
            Collection<SkillData> skillDatas = hero.getSkillData(Skill.INSTANTBREAK);
            for (SkillData skillData : skillDatas) {
                InstantBreakData instantBreakData = (InstantBreakData) skillData;
                Block block = e.getClickedBlock();
                if (instantBreakData.canBreak(block.getType())) {
                    block.breakNaturally(new ItemStack(Material.DIAMOND_PICKAXE));
                }
            }
        }
    }
}
