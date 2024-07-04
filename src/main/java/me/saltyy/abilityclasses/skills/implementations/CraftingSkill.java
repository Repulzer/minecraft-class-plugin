package me.saltyy.abilityclasses.skills.implementations;

import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.Playerclass;
import me.saltyy.abilityclasses.events.PlayerGainedPlayerclassEvent;
import me.saltyy.abilityclasses.events.PlayerLostPlayerclassEvent;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.CraftingData;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Collection;
import java.util.List;

public class CraftingSkill extends SkillImplementation {
    public CraftingSkill(PowersHandler powersHandler) {
        super(powersHandler);
    }

    @EventHandler
    public void onPowerGain(PlayerGainedPlayerclassEvent e) {
        Playerclass playerclass = powersHandler.getPlayerclass(e.getPlayer());
        Collection<SkillData> skills = playerclass.getSkillData(Skill.CRAFTING);
        for (SkillData skill : skills) {
            CraftingData craftingData = (CraftingData) skill;
            NamespacedKey namespacedKey = ((Keyed) craftingData.getRecipe()).getKey();
            e.getPlayer().discoverRecipe(namespacedKey);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Playerclass playerclass = powersHandler.getPlayerclass(e.getPlayer());
        Collection<SkillData> skills = playerclass.getSkillData(Skill.CRAFTING);
        for (SkillData skill : skills) {
            CraftingData craftingData = (CraftingData) skill;
            NamespacedKey namespacedKey = ((Keyed) craftingData.getRecipe()).getKey();
            e.getPlayer().discoverRecipe(namespacedKey);
        }
    }

    @EventHandler
    public void onPowerLost(PlayerLostPlayerclassEvent e) {
        Playerclass playerclass = powersHandler.getPlayerclass(e.getPlayer());
        Collection<SkillData> skills = playerclass.getSkillData(Skill.CRAFTING);
        for (SkillData skill : skills) {
            CraftingData craftingData = (CraftingData) skill;
            NamespacedKey namespacedKey = ((Keyed) craftingData.getRecipe()).getKey();
            e.getPlayer().undiscoverRecipe(namespacedKey);
        }
    }

    @EventHandler
    public void prepareCrafting(PrepareItemCraftEvent e) {
        Recipe eventRecipe = e.getRecipe();
        NamespacedKey eventKey = null;
        if (eventRecipe instanceof Keyed) {
            eventKey = ((Keyed) eventRecipe).getKey();
            if (!eventKey.getNamespace().equals("abilityclasses")) {
                return;
            }
        }
        if (eventKey == null) {
            return;
        }
        e.getInventory().setResult(new ItemStack(Material.AIR));
        List<HumanEntity> viewers = e.getViewers();
        for (HumanEntity humanEntity : viewers) {
            if (humanEntity instanceof Player) {
                Player player = (Player) humanEntity;
                Playerclass playerclass = powersHandler.getPlayerclass(player);
                Collection<SkillData> skills = playerclass.getSkillData(Skill.CRAFTING);
                if (eventRecipe == null) {
                    return;
                }
                for (SkillData skill : skills) {
                    CraftingData craftingData = (CraftingData) skill;
                    NamespacedKey namespacedKey = ((Keyed)craftingData.getRecipe()).getKey();
                    if (namespacedKey.equals(eventKey)) {
                        e.getInventory().setResult(new ItemStack(eventRecipe.getResult()));
                    }
                }
            }
        }
    }


}
