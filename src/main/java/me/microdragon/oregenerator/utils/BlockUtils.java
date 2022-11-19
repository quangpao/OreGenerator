package me.microdragon.oregenerator.utils;

import me.microdragon.oregenerator.OreGenerator;
import me.microdragon.oregenerator.objects.CustomGen;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.*;

public class BlockUtils {
    private static final OreGenerator _oreGen = OreGenerator.getPlugin();
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

    public static Map<String, CustomGen> getCustomGens(ConfigUtils _configUtils) {
        Map<String, CustomGen> customGens = new HashMap<>();

        _configUtils.getCustom().getKeys(false).forEach(
                key -> {
                    boolean enabled =  _configUtils.getCustom().getBoolean(key + ".enabled");

                    String name =  _configUtils.getCustom().getString(key + ".name");

                    String permission =  _configUtils.getCustom().getString((key + ".permission"));

                    List<World> worlds = new ArrayList<>();
                    _configUtils.getCustom().getStringList(key + ".worlds").forEach(world -> worlds.add(_oreGen.getServer().getWorld(world)));

                    int priority =  _configUtils.getCustom().getInt(key + ".priority");

                    Material with = Material.getMaterial(Objects.requireNonNull( _configUtils.getCustom().getString(key + ".with") == null ? "WATER" :  _configUtils.getCustom().getString(key + ".with")));

                    HashMap<Material, Double> materialChance = new HashMap<>();
                    Objects.requireNonNull( _configUtils.getCustom().getConfigurationSection(key + ".chances")).getKeys(false).forEach(material -> materialChance.put(Material.getMaterial(material),  _configUtils.getCustom().getDouble(key + ".chances." + material)));
                    customGens.put(key, new CustomGen(enabled, name, permission, worlds.toArray(new World[0]), priority, with, materialChance));
                });

        return customGens;
    }
}
