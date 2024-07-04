package me.saltyy.abilityclasses.skills.skilldata;

import me.saltyy.abilityclasses.skills.Skill;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class BlockRayData extends SkillData {

    private int maxDistance;
    private List<Material> blocksToPlace;
    private List<Material> blocksToReplace;
    private BlockRayMode blockRayMode;


    protected BlockRayData(Skill skill, ConfigurationSection configurationSection) {
        super(skill, configurationSection);
        blocksToPlace = configurationSection.getStringList("blocksToPlace").stream().map(str -> Material.valueOf(str)).collect(Collectors.toList());
        blocksToReplace = configurationSection.getStringList("blocksToReplace").stream().map(str -> Material.valueOf(str)).collect(Collectors.toList());
        blockRayMode = BlockRayMode.valueOf(configurationSection.getString("blockRayMode"));
        maxDistance = configurationSection.getInt("maxDistance", 20);
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public List<Material> getBlocksToPlace() {
        return blocksToPlace;
    }

    public Material getRandomBlockToPlace() {
        Random random = new Random();
        int index = random.nextInt(blocksToPlace.size());
        return blocksToPlace.get(index);
    }

    public List<Material> getBlocksToReplace() {
        return blocksToReplace;
    }

    public BlockRayMode getBlockRayMode() {
        return blockRayMode;
    }


}
