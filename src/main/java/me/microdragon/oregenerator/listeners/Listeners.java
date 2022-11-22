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
import java.util.Objects;
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
//        System.out.println(resultOre);
        if (resultOre == null) {
            return;
        }
        Block source = event.getBlock();
        Block to = event.getToBlock();
//        System.out.println(source.getType());
//        System.out.println(to.getType());
        if ((Objects.equals(getType(source.getType().name()), getType(resultOre.getSource().name())))) {
            if((to.getType() == Material.AIR)
                && generateCobble(to, source.getType().name(),resultOre.getTarget().name())
                && event.getFace() != BlockFace.DOWN) {
                System.out.println("Can it come here?");
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
//            System.out.println(customGen);
            if(generateCobble(event.getBlock(), customGen.getSource().name(), customGen.getTarget().name())) {
                if (!customGen.isEnabled()) {
                    continue;
                }
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
//        System.out.println(resultOre);
        this.resultOre = customGens.get(resultOre);
    }
    boolean generateCobble(Block block, String source, String target) {
        boolean mirMat = false;
        boolean mirMat2 = false;

        String sourceMat = getType(source);
        String targetMat = getType(target);
//        System.out.println("Source: " + sourceMat);
//        System.out.println("Target: " + targetMat);
        for(BlockFace face : BlockUtils.FACES) {
            Block relative = block.getRelative(face,1);
//            System.out.println("Relative: " + relative.getType());
            if(Objects.equals(getType(relative.getType().name()), sourceMat)) {
                mirMat = true;
            }
            if(Objects.equals(getType(relative.getType().name()), targetMat)) {
                mirMat2 = true;
            }
        }
//        System.out.println("mirMat: " + mirMat);
//        System.out.println("mirMat2: " + mirMat2);
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


    private String getType(String materialName) {
        if (materialName.equals("LAVA") || materialName.equals("STATIONARY_LAVA")) {
            return "LAVA";
        } else if (materialName.equals("WATER") || materialName.equals("STATIONARY_WATER")) {
            return "WATER";
        }
        return materialName;
    }

}
