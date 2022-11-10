package me.microdragon.oregenerator;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.List;
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
        getCommand("oregen").setExecutor(new Commands());
        if(!getDataFolder().exists()) {
            getConfig().options().copyDefaults(true);
            getConfig().set("Worlds", addWorlds());
            saveConfig();
        }
        getServer().getPluginManager().registerEvents(new Listeners(), this);

    }

    List<String> addWorlds() {
       List<String> worlds = new ArrayList<>();
       for (World w : getServer().getWorlds()) {
           worlds.add(w.getName());
       }
       return worlds;
    }


}
