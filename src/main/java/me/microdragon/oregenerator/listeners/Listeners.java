package me.microdragon.oregenerator.listeners;

import me.microdragon.oregenerator.OreGenerator;
import me.microdragon.oregenerator.objects.CustomGen;
import me.microdragon.oregenerator.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.Map;
import java.util.Random;

public class Listeners implements Listener {

    private CustomGen resultOre;
    @EventHandler
    public void onFromTo(BlockFromToEvent event) {


        if (event.getBlock().getType() == Material.AIR) {
            return;
        }
        if (event.getToBlock().getType() != Material.AIR) {
            return;
        }

        if (resultOre == null) {
            return;
        }

        Block source = event.getBlock();
        Block to = event.getToBlock();


        if ((source.getType() == resultOre.getSource())) {
            if((to.getType() == Material.AIR)
                && event.getFace() != BlockFace.DOWN) {
                event.setCancelled(true);
                to.setType(randomChance());
            }
        }
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        Map<String, CustomGen> customGens = OreGenerator.getCustomGens();
        Player player = event.getPlayer();
        String resultOre = null;
        for (Map.Entry<String, CustomGen> entry : customGens.entrySet()) {
            CustomGen customGen = entry.getValue();
            if(generateCobble(event.getBlock(), customGen.getSource(), customGen.getTarget())) {

                if (!customGen.isEnabled()) {
                    continue;
                }

                //Check if player has permission to use this generator
                if (customGen.getPermission() != null) {
                    if (!player.hasPermission(customGen.getPermission())) {
                        continue;
                    }
                }

                if (customGen.getWorlds().size() > 0) {
                    if (!customGen.getWorlds().contains(player.getWorld())) {
                        continue;
                    }
                }

                if (customGen.getPriority() > 0) {
                    if (resultOre == null) {
                        resultOre = entry.getKey();
                    } else {
                        if (customGens.get(resultOre).getPriority() < customGen.getPriority()) {
                            resultOre = entry.getKey();
                        }
                    }
                }

            }
        }
        this.resultOre = customGens.get(resultOre);
    }

    boolean generateCobble(Block block, Material source, Material target) {
        boolean mirMat = false;
        boolean mirMat2 = false;
        for(BlockFace face : BlockUtils.FACES) {
            Block relative = block.getRelative(face,1);
            if(relative.getType() == source) {
                mirMat = true;
            }
            if(relative.getType() == target) {
                mirMat2 = true;
            }
        }
        return mirMat && mirMat2;
    }

    private Material randomChance() {
        Random random = new Random();

        Map<Material, Double> chances = resultOre.getMaterialChance();

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
