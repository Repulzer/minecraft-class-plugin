package me.saltyy.abilityclasses.skills.implementations;

import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.SkillCooldownHandler;
import me.saltyy.abilityclasses.Playerclass;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;
import me.saltyy.abilityclasses.skills.skilldata.SummonData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Collection;

public class SummonSkill extends SkillImplementation {

    SkillCooldownHandler skillCooldownHandler = new SkillCooldownHandler();

    public SummonSkill(PowersHandler powersHandler) {
        super(powersHandler);
    }

    @EventHandler
    public void onPunch(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Playerclass playerclass = powersHandler.getPlayerclass(player);
        Collection<SkillData> skillDatas = playerclass.getSkillData(Skill.SUMMON);
        for (SkillData skillData : skillDatas) {
            SummonData summonData = (SummonData) skillData;
            if (summonData.getAction().contains(e.getAction())) {
                if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                    if ((summonData.mustSneak() && player.isSneaking()) || !summonData.mustSneak()) {
                        if (skillCooldownHandler.isCooldownOver(summonData, player.getUniqueId())) {
                            strikeLightning(player, summonData.getEntityType(), summonData.getRange());
                            skillCooldownHandler.startCooldown(summonData, player.getUniqueId());
                            if (summonData.doesRepel()) {
                                player.setVelocity(player.getEyeLocation().getDirection().multiply(-0.5));
                            }
                            if (summonData.getPotionEffect() != null) {
                                player.addPotionEffect(summonData.getPotionEffect());
                            }
                        }
                    }
                }
            }
        }
    }

    public void strikeLightning(Player player, EntityType entityType, int blocksToTravel) {
        World world = player.getWorld();
        Location eyeLoc = player.getEyeLocation().clone();
        Vector travelVector = eyeLoc.getDirection();
        RayTraceResult rayTraceResult = world.rayTraceBlocks(eyeLoc, travelVector, blocksToTravel);
        Vector hitPosition;
        if (rayTraceResult == null || rayTraceResult.getHitPosition() == null) {
            hitPosition = eyeLoc.toVector().add(travelVector.multiply(blocksToTravel));
        } else {
            hitPosition = rayTraceResult.getHitPosition();
        }
        Location location = new Location(world, hitPosition.getX(), hitPosition.getY(), hitPosition.getZ());
        if (entityType == EntityType.LIGHTNING) world.strikeLightning(location);
        else world.spawnEntity(location, entityType);
    }
}
