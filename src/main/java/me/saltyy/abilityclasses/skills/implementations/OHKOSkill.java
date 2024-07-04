package me.saltyy.abilityclasses.skills.implementations;

import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.Playerclass;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.OHKOData;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Collection;

public class OHKOSkill extends SkillImplementation {
    public OHKOSkill(PowersHandler powersHandler) {
        super(powersHandler);
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity) {
            Player player = (Player) e.getDamager();
            Playerclass playerclass = powersHandler.getPlayerclass(player);
            Collection<SkillData> skillDatas = playerclass.getSkillData(Skill.OHKO);
            for (SkillData skillData : skillDatas) {
                OHKOData ohkoData = (OHKOData) skillData;
                LivingEntity livingEntity = (LivingEntity) e.getEntity();
                if (ohkoData.getDisplayName() == null || livingEntity.getCustomName().equals(ohkoData.getDisplayName())) {
                    if (ohkoData.getEntityTypes().contains(e.getEntity().getType())) {
                        livingEntity.damage(livingEntity.getHealth(), e.getDamager());
                    }
                }
            }
        }
    }

}
