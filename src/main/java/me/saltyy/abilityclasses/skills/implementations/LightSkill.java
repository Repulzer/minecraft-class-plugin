package me.saltyy.abilityclasses.skills.implementations;

import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.Playerclass;
import me.saltyy.abilityclasses.events.PlayerGainedPlayerclassEvent;
import me.saltyy.abilityclasses.events.PlayerLostPlayerclassEvent;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.LightData;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class LightSkill extends SkillImplementation {
    public LightSkill(PowersHandler powersHandler) {
        super(powersHandler);
    }

    @EventHandler
    public void onGain(PlayerGainedPlayerclassEvent e) {
        Playerclass playerclass = powersHandler.getPlayerclass(e.getPlayer());
        if (playerclass.hasSkill(Skill.LIGHT)) {
            runnable(e.getPlayer());
        }
    }

    @EventHandler
    public void onLost(PlayerLostPlayerclassEvent e) {
        Playerclass playerclass = powersHandler.getPlayerclass(e.getPlayer());
        Collection<SkillData> skillDatas = playerclass.getSkillData(Skill.LIGHT);
        for (SkillData skillData : skillDatas) {
            LightData lightData = (LightData) skillData;
            e.getPlayer().removePotionEffect(lightData.getPotionEffect().getType());
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Playerclass playerclass = powersHandler.getPlayerclass(e.getPlayer());
        if (playerclass.hasSkill(Skill.LIGHT)) {
            runnable(e.getPlayer());
        }
    }

    public void runnable(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Playerclass playerclass1 = powersHandler.getPlayerclass(player);
                Collection<SkillData> data = playerclass1.getSkillData(Skill.LIGHT);
                if (data.isEmpty()) {
                    this.cancel();
                    return;
                }
                for (SkillData skillData : data) {
                    LightData lightData = (LightData) skillData;
                    if (player.getWorld().getBlockAt(player.getLocation()).getLightLevel() > 10) {
                        player.addPotionEffect(lightData.getPotionEffect());
                    }
                    else {
                        player.removePotionEffect(lightData.getPotionEffect().getType());
                    }
                }
            }
        }.runTaskTimer(powersHandler.getPlugin(), 0L, 20L);
    }


}
