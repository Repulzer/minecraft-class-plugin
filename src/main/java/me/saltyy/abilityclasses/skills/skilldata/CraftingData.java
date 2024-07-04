package me.saltyy.abilityclasses.skills.skilldata;

import me.saltyy.abilityclasses.AbilityClasses;
import me.saltyy.abilityclasses.skills.Skill;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class CraftingData extends SkillData {

    private Recipe recipe;

    protected CraftingData(Skill skill, ConfigurationSection configurationSection) {
        super(skill, configurationSection);
        boolean isShaped = configurationSection.getBoolean("isShaped", true);
        NamespacedKey namespacedKey = new NamespacedKey(JavaPlugin.getPlugin(AbilityClasses.class), UUID.randomUUID().toString());
        ConfigurationSection resultSection = configurationSection.getConfigurationSection("result");
        String typeStr = resultSection.getString("type", "STONE");
        Material type = Material.valueOf(typeStr);
        int amount = resultSection.getInt("amount", 1);
        ItemStack result = new ItemStack(type, amount);
        if (isShaped) {
            ConfigurationSection recipeKeys = configurationSection.getConfigurationSection("recipeKeys");
            ShapedRecipe shapedRecipe = new ShapedRecipe(namespacedKey, result);
            List<String> recipeShape = configurationSection.getStringList("recipe");
            shapedRecipe.shape(recipeShape.toArray(new String[recipeShape.size()]));
            for (Map.Entry<String, Object> entry : recipeKeys.getValues(false).entrySet()) {
                Material material = Material.valueOf(entry.getKey().toUpperCase());
                String symbol = (String) entry.getValue();
                shapedRecipe.setIngredient(symbol.charAt(0), material);
            }
            recipe = shapedRecipe;
        }
        else {
            ShapelessRecipe shapelessRecipe = new ShapelessRecipe(namespacedKey, result);
            ConfigurationSection ingredients = configurationSection.getConfigurationSection("ingredients");
            for (Map.Entry<String, Object> entry : ingredients.getValues(false).entrySet()) {
                Integer x = (Integer) entry.getValue();
                shapelessRecipe.addIngredient(x, Material.valueOf(entry.getKey()));
            }
            recipe = shapelessRecipe;
        }
        Bukkit.addRecipe(recipe);
    }

    public Recipe getRecipe() {
        return recipe;
    }

}
