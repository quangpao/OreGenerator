package me.microdragon.oregenerator;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class OreGenerator extends JavaPlugin {

    public final Logger logger = Logger.getLogger("Minecraft");

    @Override
    public void onEnable() {
        new Listeners(this);
        getCommand("oregen").setExecutor(new Commands(this));
        if(!getDataFolder().exists()) {
            getConfig().options().copyDefaults(true);
            getConfig().set("Worlds", addWorlds());
            saveConfig();
        }

    }

    List<String> addWorlds() {
       List<String> worlds = new ArrayList<>();
       for (World w : getServer().getWorlds()) {
           worlds.add(w.getName());
       }
       return worlds;
    }


}
