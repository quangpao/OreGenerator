package me.microdragon.oregenerator.objects;

import org.bukkit.Material;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomGen {

    private final boolean enabled;
    private final String name;
    private final String permission;
    private final ArrayList<World> worlds;
    private final int priority;
    private Material source = Material.LAVA;
    private Material target = Material.WATER;
    private final HashMap<Material, Double> materialChance;

    /**
     * This is the constructor for the CustomGen class.
     * Des: Using when the custom generator is disabled.
     */
    public CustomGen(boolean enabled, String name, String permission, ArrayList<World> worlds, int priority, HashMap<Material, Double> materialChance) {
        this.enabled = enabled;
        this.name = name;
        this.permission = permission;
        this.worlds = worlds;
        this.priority = priority;
        this.materialChance = materialChance;
    }

    /**
     * This is the constructor for the CustomGen class.
     * Des: Using when the custom generator is enabled and all source and target had been used.
     */
    public CustomGen(boolean enabled, String name, String permission, ArrayList<World> worlds, int priority, Material source, Material target, HashMap<Material, Double> materialChance) {
        this.enabled = enabled;
        this.name = name;
        this.permission = permission;
        this.worlds = worlds;
        this.priority = priority;
        this.source = source;
        this.target = target;
        this.materialChance = materialChance;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getPermission() {
        return permission;
    }

    public ArrayList<World> getWorlds() {
        return worlds;
    }

    public int getPriority() {
        return priority;
    }

    public HashMap<Material, Double> getMaterialChance() {
        return materialChance;
    }

    @Override
    public String toString() {
        return "CustomGen{" +
                "enabled=" + enabled +
                ", name='" + name + '\'' +
                ", permission='" + permission + '\'' +
                ", worlds=" + worlds +
                ", priority=" + priority +
                ", source=" + source +
                ", target=" + target +
                ", materialChance=" + materialChance +
                '}';
    }

    public Material getTarget() {
        return target;
    }

    public Material getSource() {
        return source;
    }

}
