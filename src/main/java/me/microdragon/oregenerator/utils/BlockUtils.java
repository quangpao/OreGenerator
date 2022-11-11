package me.microdragon.oregenerator.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.Objects;

public class BlockUtils {
    public static final BlockFace[] FACES =
            new BlockFace[] {
                    BlockFace.NORTH,
                    BlockFace.EAST,
                    BlockFace.SOUTH,
                    BlockFace.WEST,
                    BlockFace.UP,
                    BlockFace.DOWN
            };
    public static boolean isSurroundedByWater(Location loc) {
        try {
            Block[] blocks = {
                    Objects.requireNonNull(loc.getWorld()).getBlockAt(loc.getBlockX() + 1, loc.getBlockY(), loc.getBlockZ()),
                    loc.getWorld().getBlockAt(loc.getBlockX() - 1, loc.getBlockY(), loc.getBlockZ()),
                    loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() + 1),
                    loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() - 1)
            };

            for(Block block : blocks) {
                if(block.getType() == Material.WATER) {
                    return true;
                }
            }
        } catch (NullPointerException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
        return false;
    }
}
