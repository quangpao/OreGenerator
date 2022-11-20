package me.microdragon.oregenerator.utils;

import me.microdragon.oregenerator.OreGenerator;
import me.microdragon.oregenerator.objects.CustomGen;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;

import java.util.*;

public class BlockUtils {
    public static final BlockFace[] FACES =
            new BlockFace[] {
                    BlockFace.NORTH,
                    BlockFace.EAST,
                    BlockFace.SOUTH,
                    BlockFace.WEST,
            };

    public static Map<String, CustomGen> getCustomGens(ConfigUtils _configUtils) {
        Map<String, CustomGen> customGens = new HashMap<>();

        _configUtils.getCustom().getKeys(false).forEach(
                key -> {
                    boolean enabled =  _configUtils.getCustom().getBoolean(key + ".enabled");

                    String name =  _configUtils.getCustom().getString(key + ".name");

                    String permission =  _configUtils.getCustom().getString((key + ".permission"));

                    ArrayList<World> worlds = new ArrayList<>();
                    _configUtils.getCustom().getStringList(key + ".worlds").forEach(world -> worlds.add(OreGenerator.getPlugin().getServer().getWorld(world)));

                    int priority =  _configUtils.getCustom().getInt(key + ".priority");

                    HashMap<Material, Double> materialChance = new HashMap<>();
                    Objects.requireNonNull( _configUtils.getCustom().getConfigurationSection(key + ".chances")).getKeys(false).forEach(material -> materialChance.put(Material.getMaterial(material),  _configUtils.getCustom().getDouble(key + ".chances." + material)));

                    if (_configUtils.getCustom().getBoolean(key + ".custom.enabled")) {
                        Material source = Material.getMaterial(Objects.requireNonNull(_configUtils.getCustom().getString(key + ".custom.source")));
                        Material target = Material.getMaterial(Objects.requireNonNull(_configUtils.getCustom().getString(key + ".custom.target")));

                        customGens.put(key, new CustomGen(enabled, name, permission, worlds, priority, source, target, materialChance));
                    } else {
                        customGens.put(key, new CustomGen(enabled, name, permission, worlds, priority, materialChance));
                    }

                });

        return customGens;
    }
}
