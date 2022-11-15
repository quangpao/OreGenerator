package me.microdragon.oregenerator.objects;

import org.bukkit.Material;
import org.bukkit.World;

import java.util.Arrays;
import java.util.HashMap;

public class CustomGen {

    private final boolean enabled;
    private final String name;
    private final String permission;
    private final World[] worlds;
    private final int priority;
    private final Material with;
    private final HashMap<Material, Double> materialChance;

    public CustomGen(boolean enabled, String name, String permission, World[] worlds, int priority, Material with, HashMap<Material, Double> materialChance) {
        this.enabled = enabled;
        this.name = name;
        this.permission = permission;
        this.worlds = worlds;
        this.priority = priority;
        this.with = with;
        this.materialChance = materialChance;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public World[] getWorlds() {
        return worlds;
    }

    public int getPriority() {
        return priority;
    }

    public Material getWith() {
        return with;
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
                ", worlds=" + Arrays.toString(worlds) +
                ", priority=" + priority +
                ", with=" + with +
                ", materialChance=" + materialChance +
                '}';
    }
}
