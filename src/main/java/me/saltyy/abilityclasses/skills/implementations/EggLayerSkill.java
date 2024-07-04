package me.saltyy.abilityclasses.skills.implementations;

import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.Playerclass;
import me.saltyy.abilityclasses.events.PlayerGainedPlayerclassEvent;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.EggLayerData;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class EggLayerSkill extends SkillImplementation {
    public EggLayerSkill(PowersHandler powersHandler) {
        super(powersHandler);
    }

    @EventHandler
    public void onPowerGain(PlayerGainedPlayerclassEvent e) {
        startRunnable(e.getPlayer());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        startRunnable(e.getPlayer());
    }

    public void startRunnable(Player player) {
        Playerclass playerclass = powersHandler.getPlayerclass(player);
        Collection<SkillData> skillDatas = powersHandler.getPlayerclass(player).getSkillData(Skill.EGGLAYER);
        for (SkillData skillData : skillDatas) {
            EggLayerData eggLayerData = (EggLayerData) skillData;
            new BukkitRunnable() {
                @Override
                public void run() {
                    Playerclass currentPower = powersHandler.getPlayerclass(player);
                    if (!currentPower.equals(playerclass)) {
                        cancel();
                        return;
                    }
                    if (!player.isOnline()) {
                        cancel();
                        return;
                    }
                    World world = player.getWorld();
                    Location location = player.getLocation();
                    world.dropItemNaturally(location, eggLayerData.getToLay());
                }
            }.runTaskTimer(powersHandler.getPlugin(), 0L, eggLayerData.getTickDelay());
        }

    }
}
