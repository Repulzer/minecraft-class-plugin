package me.saltyy.abilityclasses.skills.implementations;

import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.Playerclass;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.PickpocketData;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.List;

public class PickpocketSkill extends SkillImplementation {
    public PickpocketSkill(PowersHandler powersHandler) {
        super(powersHandler);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        Playerclass playerclass = powersHandler.getPlayerclass(player);
        Collection<SkillData> skillDatas = playerclass.getSkillData(Skill.PICKPOCKET);
        if (e.getRightClicked() instanceof Player) {
            for (SkillData skillData : skillDatas) {
                PickpocketData pickpocketData = (PickpocketData) skillData;
                if (player.isSneaking() != pickpocketData.isSneaking()) {
                    return;
                }
                Player otherPlayer = (Player) e.getRightClicked();
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1F, 0.5F);
                otherPlayer.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1F, 0.5F);
                Inventory inventory =  otherPlayer.getInventory();
                InventoryView inventoryView = player.openInventory(inventory);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (inventoryView == null) {
                            inventoryView.close();
                            cancel();
                            return;
                        }
                        if (!playerclass.equals(powersHandler.getPlayerclass(player))) {
                            inventoryView.close();
                            cancel();
                            return;
                        }
                        if (otherPlayer.getLocation().distanceSquared(player.getLocation()) > pickpocketData.getRangeSquared()) {
                            inventoryView.close();
                            cancel();
                            return;
                        }
                    }
                }.runTaskTimer(powersHandler.getPlugin(), 0L, 4L);

            }
        }
    }
}
