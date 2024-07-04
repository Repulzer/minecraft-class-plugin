package me.saltyy.abilityclasses.skills.implementations;

import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.ShieldData;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class ShieldSkill extends SkillImplementation {
    public ShieldSkill(PowersHandler powersHandler) {
        super(powersHandler);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            Collection<SkillData> skillDatas = powersHandler.getPlayerclass(player).getSkillData(Skill.SHIELD);
            for (SkillData skillData : skillDatas) {
                ShieldData shieldData = (ShieldData) skillData;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.setCooldown(Material.SHIELD, shieldData.getCooldown());
                    }
                }.runTaskLater(powersHandler.getPlugin(), 1L);
            }
        }
    }
}
