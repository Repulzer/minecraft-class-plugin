package me.saltyy.abilityclasses.skills.implementations;

import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.SkillCooldownHandler;
import me.saltyy.abilityclasses.Playerclass;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.EraserData;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.RayTraceResult;

import java.util.Collection;

public class EraserSkill extends SkillImplementation {
    public EraserSkill(PowersHandler powersHandler) {
        super(powersHandler);
    }

    SkillCooldownHandler skillCooldownHandler =new SkillCooldownHandler();

    @EventHandler
    public void onSight(PlayerToggleSneakEvent e) {
        if (e.isSneaking()) {
            Player player = e.getPlayer();
            Playerclass playerclass = powersHandler.getPlayerclass(player);
            Collection<SkillData> skillDatas = playerclass.getSkillData(Skill.ERASER);
            for (SkillData skillData : skillDatas) {
                EraserData eraserData = (EraserData) skillData;
                if (skillCooldownHandler.isCooldownOver(eraserData, player.getUniqueId())) {
                    World world = player.getWorld();
                    Location eyeLocation = player.getEyeLocation();
                    eyeLocation = eyeLocation.clone().add(eyeLocation.getDirection());
                    RayTraceResult rayTraceResult = world.rayTrace(eyeLocation, eyeLocation.getDirection(), eraserData.getRange(), FluidCollisionMode.NEVER, true, 1.0, entity -> entity instanceof Player && entity != player);
                    Entity entity = rayTraceResult.getHitEntity();
                    if (entity != null) {
                        Player hitPlayer = (Player) entity;
                        powersHandler.temporarilyRemovePower(hitPlayer, player, eraserData.getDuration());
                        skillCooldownHandler.startCooldown(eraserData, eraserData.getCooldown(), player.getUniqueId());
                    }
                }
            }
        }
    }
}
