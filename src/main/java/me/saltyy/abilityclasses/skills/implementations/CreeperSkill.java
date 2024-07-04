package me.saltyy.abilityclasses.skills.implementations;

import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.SkillCooldownHandler;
import me.saltyy.abilityclasses.Playerclass;
import me.saltyy.abilityclasses.AbilityClasses;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.CreeperData;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;

public class CreeperSkill extends SkillImplementation {

    SkillCooldownHandler skillCooldownHandler = new SkillCooldownHandler();

    public CreeperSkill(PowersHandler powersHandler) {
        super(powersHandler);
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        if (e.isSneaking()) {
            Player player = e.getPlayer();
            Playerclass playerclass = powersHandler.getPlayerclass(player);
            Collection<SkillData> skillDatas = playerclass.getSkillData(Skill.CREEPER);
            for (SkillData skillData : skillDatas) {
                final CreeperData creeperData = (CreeperData) skillData;
                if (isOnGround(player)) {
                    final int[] timer = {0};
                    World world = player.getWorld();
                    if (skillCooldownHandler.isCooldownOver(creeperData, player.getUniqueId())) {
                        world.playSound(player.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 1.0F, 1.0F);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (!player.isSneaking()) {
                                    cancel(); return;
                                }
                                if (powersHandler.getPlayerclass(player).getSkillData(Skill.CREEPER).isEmpty()) {
                                    cancel(); return;
                                }
                                if (!isOnGround(player)) {
                                    cancel(); return;
                                }
                                if (timer[0] >= creeperData.getFuse()) {
                                    explosion(creeperData, player, world);
                                    cancel(); return;
                                } else {
                                    timer[0]++;
                                }
                            }
                        }.runTaskTimer(JavaPlugin.getPlugin(AbilityClasses.class), 0L, 1L);
                    }
                }
            }
        }
    }

    public boolean isOnGround(Player player) {
        return !player.getLocation().clone().subtract(0, 1, 0).getBlock().getType().isAir();
    }

    public void explosion(CreeperData creeperData, Player player, World world) {
        world.createExplosion(player.getLocation(), creeperData.getCreeperPower(), false);
        skillCooldownHandler.startCooldown(creeperData, creeperData.getCooldown(), player.getUniqueId());
        player.setVelocity(new Vector(0, creeperData.getUpwardsVelocity(), 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, creeperData.getSlowfallDuration(), 0));
    }

}
