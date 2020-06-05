package me.xemor.superheroes.Superpowers;

import me.xemor.superheroes.PowersHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class LavaWalker extends Superpower {
    public LavaWalker(PowersHandler powersHandler) {
        super(powersHandler);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (player.isSneaking()) {
            return;
        }
        if (powersHandler.getPower(player) == Power.LavaWalker) {
            Location location = e.getTo();
            World world = player.getWorld();
            Block block = world.getBlockAt(location.clone().subtract(0, 1, 0));
            if (block.getType() == Material.LAVA) {
                block.setType(Material.MAGMA_BLOCK);
            }
        }
    }
}
