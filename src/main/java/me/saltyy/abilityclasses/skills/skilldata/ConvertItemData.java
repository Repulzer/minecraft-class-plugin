package me.saltyy.abilityclasses.skills.skilldata;

import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.configdata.ItemStackData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class ConvertItemData extends SkillData {

    private ItemStack inputItem;
    private ItemStack outputItem;
    private double chance;

    protected ConvertItemData(Skill skill, ConfigurationSection configurationSection) {
        super(skill, configurationSection);
        final ConfigurationSection inputItemSection = configurationSection.getConfigurationSection("inputItem");
        if (inputItemSection != null) {
            inputItem = new ItemStackData(inputItemSection).getItem();
        }
        final ConfigurationSection outputItemSection = configurationSection.getConfigurationSection("outputItem");
        if (outputItemSection != null) {
            outputItem = new ItemStackData(outputItemSection).getItem();
        }
        chance = configurationSection.getDouble("chance", 1);
    }

    public ItemStack getInputItem() {
        return inputItem;
    }

    public ItemStack getOutputItem() {
        return outputItem;
    }

    public double getChance() {
        return chance;
    }
}
