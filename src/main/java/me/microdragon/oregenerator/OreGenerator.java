package me.microdragon.oregenerator;

import me.microdragon.oregenerator.commands.Commands;
import me.microdragon.oregenerator.listeners.Listeners;
import me.microdragon.oregenerator.listeners.ListenersDebug;
import me.microdragon.oregenerator.utils.ConfigUtils;
import me.microdragon.oregenerator.utils.PluginUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Objects;

public final class OreGenerator extends JavaPlugin {
    private static OreGenerator plugin;
    FileConfiguration config;
    FileConfiguration custom;

    ConfigUtils configUtils;

    public static OreGenerator getPlugin() {
        return plugin;
    }
    public static FileConfiguration getCustom() {
        return getPlugin().custom;
    }

    public static ConfigUtils getConfigUtils() {
        return getPlugin().configUtils;
    }
    @Override
    public void onEnable() {

        plugin = this;
        loadConfig();
        Objects.requireNonNull(getCommand("oregen")).setExecutor(new Commands());
        if (PluginUtils.getDebug()) {
            getServer().getPluginManager().registerEvents(new ListenersDebug(), this);
        } else {
            getServer().getPluginManager().registerEvents(new Listeners(), this);
        }

    }

    void loadConfig() {
        configUtils = new ConfigUtils();
        configUtils.setup();
        config = configUtils.getConfig();
        custom = configUtils.getCustom();
    }



}
