package me.saltyy.abilityclasses.skills.implementations;

import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.Playerclass;
import me.saltyy.abilityclasses.events.PlayerLostPlayerclassEvent;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.DamageResistanceData;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Collection;

public class DamageResistanceSkill extends SkillImplementation {

    public DamageResistanceSkill(PowersHandler powersHandler) {
        super(powersHandler);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) e.getEntity();
        Playerclass playerclass = powersHandler.getPlayerclass(player);
        Collection<SkillData> skillDatas = playerclass.getSkillData(Skill.DAMAGERESISTANCE);
        for (SkillData skillData : skillDatas) {
            DamageResistanceData damageResistanceData = (DamageResistanceData) skillData;
            if (damageResistanceData.getDamageCause() == null || damageResistanceData.getDamageCause().contains(e.getCause())) {
                e.setDamage(e.getDamage() * damageResistanceData.getDamageMultiplier());
                if (damageResistanceData.getDamageMultiplier() == 0) {
                    e.setCancelled(true);
                }
                if (damageResistanceData.getPotionEffect() != null) {
                    if (!player.hasPotionEffect(damageResistanceData.getPotionEffect().getType())) {
                        player.addPotionEffect(damageResistanceData.getPotionEffect());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPowerLoss(PlayerLostPlayerclassEvent e) {
        Player player = e.getPlayer();
        Playerclass playerclass = powersHandler.getPlayerclass(player);
        Collection<SkillData> skillDatas = playerclass.getSkillData(Skill.DAMAGERESISTANCE);
        if (!skillDatas.isEmpty()) {
            for (SkillData skillData : skillDatas) {
                DamageResistanceData damageResistanceData = (DamageResistanceData) skillData;
                if (damageResistanceData.getPotionEffect() != null) {
                    player.removePotionEffect(damageResistanceData.getPotionEffect().getType());
                }
            }
        }
    }


}
