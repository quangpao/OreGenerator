package me.microdragon.oregenerator;

import me.microdragon.oregenerator.commands.Commands;
import me.microdragon.oregenerator.listeners.Listeners;
import me.microdragon.oregenerator.objects.CustomGen;
import me.microdragon.oregenerator.utils.BlockUtils;
import me.microdragon.oregenerator.utils.ConfigUtils;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Map;
import java.util.Objects;

public final class OreGenerator extends JavaPlugin {
    private static OreGenerator plugin;
    private static ConfigUtils _configUtils;
    private static Map<String, CustomGen> _customGens;
    public static void setConfigUtils(ConfigUtils configUtils) {
        _configUtils = configUtils;
    }
    public static ConfigUtils getConfigUtils() {
        return _configUtils;
    }
    public static OreGenerator getPlugin() {
        return plugin;
    }
    public static Map<String, CustomGen> getCustomGens() {
        return _customGens;
    }
    public static void setCustomGens() {
        _customGens = BlockUtils.getCustomGens(_configUtils);
    }
    @Override
    public void onEnable() {

        plugin = this;
        _configUtils = new ConfigUtils();
        _configUtils.setup();
        _customGens = BlockUtils.getCustomGens(_configUtils);
        Objects.requireNonNull(getCommand("oregen")).setExecutor(new Commands());
        getServer().getPluginManager().registerEvents(new Listeners(), this);

    }



}
