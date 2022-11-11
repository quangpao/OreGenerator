package me.microdragon.oregenerator.utils;

import me.microdragon.oregenerator.OreGenerator;
import org.bukkit.Material;
import org.bukkit.World;

import java.io.File;
import java.util.*;

public class PluginUtils {

    private static final OreGenerator _oreGen = OreGenerator.getPlugin();

    public static Map<Material, Double> getMaterialChance() {
        Map<Material, Double> materialChance = new HashMap<>();
        for (String key : Objects.requireNonNull(_oreGen.getConfig().getConfigurationSection("Chances")).getKeys(false)) {
            materialChance.put(Material.getMaterial(key), _oreGen.getConfig().getDouble("Chances." + key));
        }
        return materialChance;
    }

    public static void loadConfigFolder() {
        if(_oreGen.getDataFolder().exists()) {
            File configFile = new File(_oreGen.getDataFolder(), "config.yml");
            if(!configFile.exists()) {
                loadConfigFile();
                return;
            }
            _oreGen.reloadConfig();
        } else {
            loadConfigFile();
        }
    }

    private static void loadConfigFile() {
        _oreGen.getConfig().options().copyDefaults(true);
        _oreGen.getConfig().set("Worlds", addWorlds());
        _oreGen.saveConfig();
    }

    static List<String> addWorlds() {
        List<String> worlds = new ArrayList<>();
        for (World w : _oreGen.getServer().getWorlds()) {
            worlds.add(w.getName());
        }
        return worlds;
    }

}
