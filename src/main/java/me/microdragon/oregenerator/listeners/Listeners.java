package me.microdragon.oregenerator.listeners;

import me.microdragon.oregenerator.utils.BlockUtils;
import me.microdragon.oregenerator.utils.PluginUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.Map;
import java.util.Random;

public class Listeners implements Listener {


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
                    if(!BlockUtils.isSurroundedByWater(to.getLocation())) {
                        return;
                    }
                }

                event.setCancelled(true);
                to.setType(randomChance());
            }
        }
    }

    private boolean generateCobble(Material material, Block block) {
        Material mirMat = material == Material.WATER ? Material.LAVA : Material.WATER;
        for(BlockFace face : BlockUtils.FACES) {
            Block relative = block.getRelative(face,1);
            if(relative.getType() == mirMat) {
                return true;
            }
        }
        return false;
    }

    private Material randomChance() {
        Random random = new Random();

        Map<Material, Double> chances = PluginUtils.getMaterialChance();

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


}
