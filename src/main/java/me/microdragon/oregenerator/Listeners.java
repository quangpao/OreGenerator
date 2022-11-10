package me.microdragon.oregenerator;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.List;
import java.util.Random;

public class Listeners implements Listener {

    private OreGenerator _oreGen;

    public Listeners(OreGenerator oreGen) {
        _oreGen = oreGen;
        _oreGen.getServer().getPluginManager().registerEvents(this, _oreGen);
    }

    @EventHandler
    public void onFromTo(BlockFromToEvent event) {
        Material material = event.getBlock().getType();
        if (material == Material.LAVA || material == Material.WATER) {
            Block to = event.getToBlock();
            Material toMaterial = to.getType();
            if (toMaterial == Material.AIR) {
                if(generate(material, to)) {
                    List<String> worlds = _oreGen.getConfig().getStringList("Worlds");
                    if (worlds.contains(to.getWorld().getName())) {
                        Random pick = new Random();
                        int chance = pick.nextInt(100);

                        double coal = _oreGen.getConfig().getDouble("Chance.Coal");
                        double iron = _oreGen.getConfig().getDouble("Chance.Iron");
                        double gold = _oreGen.getConfig().getDouble("Chance.Gold");
                        double redstone = _oreGen.getConfig().getDouble("Chance.Redstone");
                        double lapis = _oreGen.getConfig().getDouble("Chance.Lapis");
                        double diamond = _oreGen.getConfig().getDouble("Chance.Diamond");
                        double emerald = _oreGen.getConfig().getDouble("Chance.Emerald");

                        if (chance > 0 && chance < coal) {
                            to.setType(Material.COAL_ORE);
                        } else if (chance > coal && chance < iron) {
                            to.setType(Material.IRON_ORE);
                        } else if (chance > iron && chance < gold) {
                            to.setType(Material.GOLD_ORE);
                        } else if (chance > gold && chance < redstone) {
                            to.setType(Material.REDSTONE_ORE);
                        } else if (chance > redstone && chance < lapis) {
                            to.setType(Material.LAPIS_ORE);
                        } else if (chance > lapis && chance < diamond) {
                            to.setType(Material.DIAMOND_ORE);
                        } else if (chance > diamond && chance < emerald) {
                            to.setType(Material.EMERALD_ORE);
                        }
                    }
                }
            }
        }

    }

    public final BlockFace[] faces = new BlockFace[] { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN };

    public boolean generate(Material m, Block b) {
        Material mirror1 = (m == Material.WATER) ? Material.LAVA : Material.WATER;
        Material mirror2 = (m == Material.WATER) ? Material.LAVA : Material.WATER;
        for(BlockFace face : faces) {
            Block relative = b.getRelative(face);
            if(relative.getType() == mirror1 || relative.getType() == mirror2) {
                return true;
            }
        }
        return false;
    }

}
