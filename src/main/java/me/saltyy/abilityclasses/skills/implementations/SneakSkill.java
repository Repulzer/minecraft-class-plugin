package me.saltyy.abilityclasses.skills.implementations;

import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;
import me.saltyy.abilityclasses.skills.skilldata.SneakData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import java.util.Collection;

public class SneakSkill extends SkillImplementation {

    public SneakSkill(PowersHandler powersHandler) {
        super(powersHandler);
    }

    @EventHandler
    public void onSneak(EntityTargetLivingEntityEvent e) {
        if (e.getTarget() instanceof Player) {
            Player player = (Player) e.getTarget();
            Collection<SkillData> skillDatas = powersHandler.getPlayerclass(player).getSkillData(Skill.SNEAK);
            for (SkillData skillData : skillDatas) {
                SneakData sneakData = (SneakData) skillData;
                if ((sneakData.mustSneak() && player.isSneaking()) || !sneakData.mustSneak()) {
                    if (sneakData.needsInvisibility() && player.isInvisible() || !sneakData.needsInvisibility()) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

}
