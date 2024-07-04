package me.saltyy.abilityclasses.skills.implementations;

import me.saltyy.abilityclasses.ParticleHandler;
import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.SkillCooldownHandler;
import me.saltyy.abilityclasses.Playerclass;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;
import me.saltyy.abilityclasses.skills.skilldata.TeleportData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Collection;

public class TeleportSkill extends SkillImplementation {


    SkillCooldownHandler skillCooldownHandler = new SkillCooldownHandler();
    public TeleportSkill(PowersHandler powersHandler) {
        super(powersHandler);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Playerclass playerclass = getPowersHandler().getPlayerclass(player);
        Collection<SkillData> skillDatas = playerclass.getSkillData(Skill.TELEPORT);
        for (SkillData skillData : skillDatas) {
            TeleportData teleportData = (TeleportData) skillData;
            if ((e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR) == teleportData.isLeftClick()) {
                if ((e.getItem() == null ? Material.AIR : e.getItem().getType()) == teleportData.getTeleportItem()) {
                    if (skillCooldownHandler.isCooldownOver(teleportData, player.getUniqueId())) {
                        doEnderTeleport(player, teleportData);
                        skillCooldownHandler.startCooldown(teleportData, teleportData.getCooldown(), player.getUniqueId());
                    }
                }
            }
        }
    }

    public void doEnderTeleport(Player player, TeleportData teleportData) {
        World world = player.getWorld();
        Location eyeLoc = player.getEyeLocation().clone();
        Vector travelVector = eyeLoc.getDirection().setY(eyeLoc.getDirection().getY() * teleportData.getyAxisMultiplier());
        RayTraceResult rayTraceResult = world.rayTraceBlocks(eyeLoc, travelVector, teleportData.getDistance());
        Vector hitPosition;
        if (rayTraceResult == null || rayTraceResult.getHitPosition() == null) {
            hitPosition = eyeLoc.toVector().add(travelVector.multiply(teleportData.getDistance()));
        } else {
            hitPosition = rayTraceResult.getHitPosition();
        }
        Location location = new Location(world, hitPosition.getX(), hitPosition.getY(), hitPosition.getZ());
        Location eyeLocation = player.getEyeLocation();
        location.setYaw(eyeLocation.getYaw());
        location.setPitch(eyeLocation.getPitch());
        player.teleport(location, teleportData.getTeleportCause());
        ParticleHandler particleHandler = new ParticleHandler(player);
        particleHandler.setupFromParticleData(teleportData.getParticleData());
        particleHandler.runTaskTimer(powersHandler.getPlugin(), 0L, 5L);
    }

}
