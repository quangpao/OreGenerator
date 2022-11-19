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

        System.out.println("Result Ore: " + resultOre);
        if (resultOre == null) {
            return;
        }

        Block source = event.getBlock();
        Block to = event.getToBlock();


        if ((source.getType() == Material.LAVA)) {
            if((to.getType() == Material.AIR)
                && generateCobble(to)
                && event.getFace() != BlockFace.DOWN) {
//                    if(!BlockUtils.isSurroundedByWater(to.getLocation())) {
//                        return;
//                }

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
            if (customGen.getPermission() != null) {
                if (player.hasPermission(customGen.getPermission())) {
                    if (customGen.isEnabled()) {
                        int prio = entry.getValue().getPriority();
                        if (customGen.getWorlds().length > 0) {
                            for (int i = 0; i < customGen.getWorlds().length; i++) {
                                if (customGen.getWorlds()[i].equals(event.getBlock().getWorld())) {
                                    resultOre = getPriority(resultOre, prio, entry, customGen);
                                }
                            }
                        } else {
                            resultOre = getPriority(resultOre, prio, entry, customGen);
                        }
                    }
                }
            }
        }
        this.resultOre = customGens.get(resultOre);
    }

    private String getPriority(String resultOre, int priority, Map.Entry<String, CustomGen> entry, CustomGen customGen) {
        if(customGen.getPriority() > 0) {
            if(resultOre == null) {
                resultOre = entry.getKey();
            } else {
                if(priority < customGen.getPriority()) {
                    resultOre = entry.getKey();
                }
            }
        } else {
            resultOre = entry.getKey();
        }
        return resultOre;
    }

    boolean generateCobble(Block block) {
        Material mirMat = resultOre.getWith();
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
