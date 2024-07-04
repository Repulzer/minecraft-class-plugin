package me.saltyy.abilityclasses.skills.implementations;

import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.Playerclass;
import me.saltyy.abilityclasses.events.PlayerGainedPlayerclassEvent;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.AuraData;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class AuraSkill extends SkillImplementation {

    public AuraSkill(PowersHandler powersHandler) {
        super(powersHandler);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        runnable(e.getPlayer());
    }

    @EventHandler
    public void onPowerGain(PlayerGainedPlayerclassEvent e) {
        runnable(e.getPlayer());
    }

    public void runnable(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player == null) {
                    cancel();
                    return;
                }
                if (!player.isOnline()) {
                    cancel();
                    return;
                }
                Playerclass playerclass = powersHandler.getPlayerclass(player);
                if (playerclass == null) {
                    cancel();
                    return;
                }
                Collection<SkillData> skillDatas = playerclass.getSkillData(Skill.AURA);
                if (skillDatas.isEmpty()) {
                    cancel();
                    return;
                }
                for (SkillData skillData : skillDatas) {
                    AuraData auraData = (AuraData) skillData;
                    World world = player.getWorld();
                    Location location = player.getLocation();
                    double diameter = auraData.getDiameter();
                    Collection<Entity> nearbyLivingEntities = world.getNearbyEntities(location, diameter, diameter, diameter, (entity) -> !player.equals(entity) && entity instanceof LivingEntity);
                    for (Entity entity : nearbyLivingEntities) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        livingEntity.addPotionEffect(auraData.getPotionEffect());
                    }
                }
            }
        }.runTaskTimer(powersHandler.getPlugin(), 10L, 10L);

    }
}
