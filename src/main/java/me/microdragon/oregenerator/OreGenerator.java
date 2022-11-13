package me.microdragon.oregenerator;

import me.microdragon.oregenerator.commands.Commands;
import me.microdragon.oregenerator.listeners.Listeners;
import me.microdragon.oregenerator.listeners.ListenersDebug;
import me.microdragon.oregenerator.utils.PluginUtils;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Objects;
import java.util.logging.Logger;

public final class OreGenerator extends JavaPlugin {
    private static OreGenerator plugin;
    public final Logger logger = Logger.getLogger("Minecraft");

    public static OreGenerator getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {

        plugin = this;
        Objects.requireNonNull(getCommand("oregen")).setExecutor(new Commands());
        PluginUtils.loadConfigFolder();
        if (PluginUtils.getDebug()) {
            getServer().getPluginManager().registerEvents(new ListenersDebug(), this);
        } else {
            getServer().getPluginManager().registerEvents(new Listeners(), this);
        }

    }



}
