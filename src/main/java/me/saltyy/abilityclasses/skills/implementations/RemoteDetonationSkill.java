package me.saltyy.abilityclasses.skills.implementations;

import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.SkillCooldownHandler;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.RemoteDetonationData;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.RayTraceResult;

import java.util.Collection;
import java.util.List;

public class RemoteDetonationSkill extends SkillImplementation {

    SkillCooldownHandler skillCooldownHandler = new SkillCooldownHandler();

    public RemoteDetonationSkill(PowersHandler powersHandler) {
        super(powersHandler);
    }

    @EventHandler
    public void onShift(PlayerToggleSneakEvent e) {
        if (e.isSneaking()) {
            Player player = e.getPlayer();
            Collection<SkillData> skillDatas = powersHandler.getPlayerclass(player).getSkillData(Skill.REMOTEDETONATION);
            for (SkillData skillData : skillDatas) {
                RemoteDetonationData remoteDetonationData = (RemoteDetonationData) skillData;
                if (skillCooldownHandler.isCooldownOver(remoteDetonationData, player.getUniqueId())) {
                    Entity entity = raytrace(player, remoteDetonationData.getExplodable());
                    if (entity == null) {
                        return;
                    }
                    Location location = entity.getLocation();
                    if (!(entity instanceof Player) && remoteDetonationData.removeDetonatedEntity()) {
                        if (entity instanceof LivingEntity) {
                            ((LivingEntity) entity).setHealth(0);
                        }
                        else {
                            entity.remove();
                        }
                    }

                    World world = player.getWorld();
                    world.createExplosion(location, remoteDetonationData.getExplosionStrength(), remoteDetonationData.spawnsFire(), remoteDetonationData.breakBlocks(), entity);
                    skillCooldownHandler.startCooldown(remoteDetonationData, player.getUniqueId());
                }
            }
        }
    }

    public Entity raytrace(Player player, List<EntityType> explodable) {
        World world = player.getWorld();
        RayTraceResult rayTraceResult = world.rayTraceEntities(player.getEyeLocation(), player.getEyeLocation().getDirection(), 32, 0.5, (entity) -> entity != player && explodable.contains(entity.getType()));
        if (rayTraceResult == null) {
            return null;
        }
        return rayTraceResult.getHitEntity();
    }
}
