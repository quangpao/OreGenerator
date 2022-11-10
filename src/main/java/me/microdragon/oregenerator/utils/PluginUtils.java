package me.microdragon.oregenerator.utils;

import me.microdragon.oregenerator.OreGenerator;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class PluginUtils {

    private static final OreGenerator _oreGen = OreGenerator.getPlugin();

    public static Map<Material, Double> getMaterialChance() {
        Map<Material, Double> materialChance = new HashMap<>();
        for (String key : _oreGen.getConfig().getConfigurationSection("Chances").getKeys(false)) {
            materialChance.put(Material.getMaterial(key), _oreGen.getConfig().getDouble("Chances." + key));
        }
        return materialChance;
    }

}
