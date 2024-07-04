package me.saltyy.abilityclasses.skills.implementations;

import me.saltyy.abilityclasses.PowersHandler;
import me.saltyy.abilityclasses.Playerclass;
import me.saltyy.abilityclasses.skills.Skill;
import me.saltyy.abilityclasses.skills.skilldata.BlockRayData;
import me.saltyy.abilityclasses.skills.skilldata.BlockRayMode;
import me.saltyy.abilityclasses.skills.skilldata.SkillData;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.RayTraceResult;

import java.util.Collection;

public class BlockRaySkill extends SkillImplementation {

    public BlockRaySkill(PowersHandler powersHandler) {
        super(powersHandler);
    }

    @EventHandler
    public void onLookChange(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Playerclass playerclass = powersHandler.getPlayerclass(player);
        Collection<SkillData> skillDatas = playerclass.getSkillData(Skill.BLOCKRAY);
        for (SkillData skillData : skillDatas) {
            BlockRayData blockRayData = (BlockRayData) skillData;
            World world = player.getWorld();
            Location eyeLocation = player.getEyeLocation();
            RayTraceResult rayTraceResult = world.rayTraceBlocks(eyeLocation, eyeLocation.getDirection(), blockRayData.getMaxDistance());
            if (rayTraceResult == null) {
                return;
            }
            Block block = rayTraceResult.getHitBlock();
            if (blockRayData.getBlocksToReplace().contains(block.getType()) && blockRayData.getBlockRayMode() == BlockRayMode.THEBLOCK) {
                block.setType(blockRayData.getRandomBlockToPlace());
                return;
            }
            if (blockRayData.getBlocksToReplace().contains(block.getType()) && blockRayData.getBlockRayMode() == BlockRayMode.ABOVEBLOCK) {
                Block aboveBlock = block.getRelative(BlockFace.UP);
                if (blockRayData.getBlocksToReplace().contains(aboveBlock.getType())) {
                    aboveBlock.setType(blockRayData.getRandomBlockToPlace());
                }
            }
        }
    }
}
