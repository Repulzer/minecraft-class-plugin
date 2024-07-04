package me.saltyy.abilityclasses;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import me.saltyy.abilityclasses.events.PlayerGainedPlayerclassEvent;
import me.saltyy.abilityclasses.events.PlayerLostPlayerclassEvent;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.CraftingData;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class RecipeHandler implements Listener {

    private HashMap<NamespacedKey, Playerclass> recipeToPower = new HashMap<>();
    private PowersHandler powersHandler;


    public RecipeHandler(PowersHandler powersHandler) {
        this.powersHandler = powersHandler;
    }

    @EventHandler
    public void prepareCrafting(PrepareItemCraftEvent e) {
        Playerclass power = null;
        Recipe eventRecipe = e.getRecipe();
        if (eventRecipe instanceof Keyed) {
            power = recipeToPower.get(((Keyed)eventRecipe).getKey());
        }
        if (power == null) {
            return;
        }
        e.getInventory().setResult(new ItemStack(Material.AIR));
        List<HumanEntity> viewers = e.getViewers();
        for (HumanEntity humanEntity : viewers) {
            if (humanEntity instanceof Player) {
                Player player = (Player) humanEntity;
                if (power.equals(powersHandler.getPlayerclass(player))) {
                    e.getInventory().setResult(eventRecipe.getResult());
                }
            }
        }
    }

    @EventHandler
    public void onPowerGain(PlayerGainedPlayerclassEvent e) {
        Collection<NamespacedKey> recipeKeys = getRecipesFromPlayerclass(e.getHero());
        e.getPlayer().discoverRecipes(recipeKeys);
    }

    public Collection<NamespacedKey> getRecipesFromPlayerclass(Playerclass playerclass) {
        Collection<SkillData> skillDatas = playerclass.getSkillData(Skill.CRAFTING);
        List<NamespacedKey> recipes = new ArrayList<>();
        for (SkillData skillData : skillDatas) {
            CraftingData craftingData = (CraftingData) skillData;
            recipes.add(((Keyed) craftingData.getRecipe()).getKey());
        }
        return recipes;
    }

    @EventHandler
    public void onPowerLost(PlayerLostPlayerclassEvent e) {
        Collection<NamespacedKey> recipeKeys = getRecipesFromPlayerclass(e.getHero());
        e.getPlayer().undiscoverRecipes(recipeKeys);
    }

    public void registerRecipe(Recipe recipe, Playerclass playerclass) {
        if (recipe instanceof Keyed) {
            NamespacedKey key = ((Keyed)recipe).getKey();
            recipeToPower.put(key, playerclass);
        }
        Bukkit.addRecipe(recipe);
    }
}
