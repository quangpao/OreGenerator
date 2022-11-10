package me.microdragon.oregenerator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Listeners implements Listener {

    private final OreGenerator _oreGen = OreGenerator.getPlugin();

    @EventHandler
    public void onFromTo(BlockFromToEvent event) {
        if (event.getBlock().getType() == Material.AIR) {
            return;
        }
        Block source = event.getBlock();
        Block to = event.getToBlock();
        if ((source.getType() == Material.WATER
            || source.getType() == Material.LAVA)) {
            if((to.getType() == Material.AIR
                || to.getType() == Material.WATER
            )
                && generateCobble(source.getType(), to)
                && event.getFace() != BlockFace.DOWN) {
                if(source.getType() == Material.LAVA
                ) {
                    if(!isSurroundedByWater(to.getLocation())) {
                        return;
                    }
                }

                event.setCancelled(true);
                to.setType(randomChance());
            }
        }
    }

    public final BlockFace[] faces = new BlockFace[] { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN };

    private boolean generateCobble(Material material, Block block) {
        Material mirMat1 = material == Material.WATER ? Material.LAVA : Material.WATER;
        Material mirMat2 = material == Material.WATER ? Material.LAVA : Material.WATER;
        for(BlockFace face : faces) {
            Block relative = block.getRelative(face,1);
            if(relative.getType() == mirMat1 || relative.getType() == mirMat2) {
                return true;
            }
        }
        return false;
    }

    private Material randomChance() {
        Random random = new Random();

        Map<Material, Double> chances = new HashMap<>();

        double coalChance = _oreGen.getConfig().getDouble("Chances.Coal");
        double ironChance = _oreGen.getConfig().getDouble("Chances.Iron");
        double goldChance = _oreGen.getConfig().getDouble("Chances.Gold");
        double redstoneChance = _oreGen.getConfig().getDouble("Chances.Redstone");
        double lapisChance = _oreGen.getConfig().getDouble("Chances.Lapis");
        double diamondChance = _oreGen.getConfig().getDouble("Chances.Diamond");
        double emeraldChance = _oreGen.getConfig().getDouble("Chances.Emerald");

        chances.put(Material.COAL_ORE, coalChance);
        chances.put(Material.IRON_ORE, ironChance);
        chances.put(Material.GOLD_ORE, goldChance);
        chances.put(Material.REDSTONE_ORE, redstoneChance);
        chances.put(Material.LAPIS_ORE, lapisChance);
        chances.put(Material.DIAMOND_ORE, diamondChance);
        chances.put(Material.EMERALD_ORE, emeraldChance);

        double total = 0;
        for (Map.Entry<Material, Double> entry : chances.entrySet()) {
            total += entry.getValue();
        }
        double randomValue = random.nextDouble() * total;
        for (Map.Entry<Material, Double> entry : chances.entrySet()) {
            if (randomValue < entry.getValue()) {
                return entry.getKey();
            }
            randomValue -= entry.getValue();
        }
        return Material.COBBLESTONE;

    }

    private boolean isSurroundedByWater(Location loc) {
        try {
            Block[] blocks = {
                    loc.getWorld().getBlockAt(loc.getBlockX() + 1, loc.getBlockY(), loc.getBlockZ()),
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
