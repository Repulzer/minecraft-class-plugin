package me.saltyy.abilityclasses.skills.implementations;

import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.SkillCooldownHandler;
import me.saltyy.abilityclasses.Playerclass;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;
import me.saltyy.abilityclasses.skills.skilldata.SlamData;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;

public class SlamSkill extends SkillImplementation {

    SkillCooldownHandler skillCooldownHandler = new SkillCooldownHandler();

    public SlamSkill(PowersHandler powersHandler) {
        super(powersHandler);
    }

    @EventHandler
    public void onPunch(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR) {
            Playerclass playerclass = powersHandler.getPlayerclass(e.getPlayer());
            Collection<SkillData> skillDatas = playerclass.getSkillData(Skill.SLAM);
            for (SkillData skillData : skillDatas) {
                SlamData slamData = (SlamData) skillData;
                if (e.getPlayer().getInventory().getItemInMainHand().getType() == slamData.getHand()) {
                    if (skillCooldownHandler.isCooldownOver(slamData, e.getPlayer().getUniqueId())) {
                        if (e.getPlayer().getFoodLevel() > slamData.getMinimumFood()) {
                            skillCooldownHandler.startCooldown(slamData, slamData.getAirCooldown(), e.getPlayer().getUniqueId());
                            e.getPlayer().setFoodLevel(e.getPlayer().getFoodLevel() - slamData.getFoodCost());
                            doDoomfistJump(e.getPlayer(), playerclass, slamData);
                        }
                    }
                }
            }
        }
    }

    public void doDoomfistJump(Player player, Playerclass playerclass, SlamData slamData) {
        player.setVelocity(player.getEyeLocation().getDirection().multiply(1.8).add(new Vector(0, 0.5, 0)));
        new BukkitRunnable() {
            @Override
            public void run() {
                Block under = player.getWorld().getBlockAt(player.getLocation().subtract(0, 1, 0));
                if (!powersHandler.getPlayerclass(player).equals(playerclass)) {
                    cancel();
                    return;
                }
                if (!under.getType().isAir()) {
                    World world = player.getWorld();
                    Collection<Entity> entities = world.getNearbyEntities(player.getLocation(), slamData.getDiameterRadius(), 3, slamData.getDiameterRadius(), entity -> entity instanceof LivingEntity);
                    for (Entity entity : entities) {
                        if (!player.equals(entity)) {
                            LivingEntity livingEntity = (LivingEntity) entity;
                            livingEntity.damage(slamData.getDamage(), player);
                        }
                    }
                    skillCooldownHandler.startCooldown(slamData, player.getUniqueId());
                    cancel();
                }
            }
        }.runTaskTimer(powersHandler.getPlugin(), 6L, 2L);
    }
}
