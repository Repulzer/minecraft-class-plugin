package me.saltyy.abilityclasses.skills.implementations;

import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.skills.Skill;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class NoHungerSkill extends SkillImplementation {
    public NoHungerSkill(PowersHandler powersHandler) {
        super(powersHandler);
    }

    @EventHandler
    public void onSaturation(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (e.getFoodLevel() <= player.getFoodLevel()) {
                if (powersHandler.getPlayerclass(player).hasSkill(Skill.NOHUNGER)) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
