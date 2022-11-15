package me.microdragon.oregenerator.utils;

import me.microdragon.oregenerator.OreGenerator;
import me.microdragon.oregenerator.objects.CustomGen;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class PluginUtils {

    private static final OreGenerator _oreGen = OreGenerator.getPlugin();
    private static final FileConfiguration _custom = OreGenerator.getCustom();

    public static Map<String, CustomGen> getCustomGens() {
        Map<String, CustomGen> customGens = new HashMap<>();

        _custom.getKeys(false).forEach(
                key -> {
                    boolean enabled = _custom.getBoolean(key + ".enabled");

                    String name = _custom.getString(key + ".name");

                    String permission = _custom.getString((key + ".permission"));

                    List<World> worlds = new ArrayList<>();
                    _custom.getStringList(key + ".worlds").forEach(world -> worlds.add(_oreGen.getServer().getWorld(world)));

                    int priority = _custom.getInt(key + ".priority");

                    Material with = Material.getMaterial(Objects.requireNonNull(_custom.getString(key + ".with") == null ? "WATER" : _custom.getString(key + ".with")));

                    HashMap<Material, Double> materialChance = new HashMap<>();
                    Objects.requireNonNull(_custom.getConfigurationSection(key + ".chances")).getKeys(false).forEach(material -> materialChance.put(Material.getMaterial(material), _custom.getDouble(key + ".chances." + material)));
                    customGens.put(key, new CustomGen(enabled, name, permission, worlds.toArray(new World[0]), priority, with, materialChance));
        });

        return customGens;
    }

    public static boolean getDebug() {
        return _oreGen.getConfig().getBoolean("debug");
    }

}
