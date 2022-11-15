package me.microdragon.oregenerator.utils;

import me.microdragon.oregenerator.OreGenerator;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigUtils {

    private static final OreGenerator _oreGen = OreGenerator.getPlugin();

    public FileConfiguration config;
    public File conFile;

    public FileConfiguration custom;
    public File customFile;

    public void setup() {
        if (!_oreGen.getDataFolder().exists()) {
            _oreGen.getDataFolder().mkdir();
        }
        conFile = new File(_oreGen.getDataFolder(), "config.yml");
        if (!conFile.exists()) {
                _oreGen.saveResource("config.yml", false);
                Bukkit.getServer().getConsoleSender().sendMessage(ChatUtils.getPrefix() +
                        ChatColor.of("#A0E4CB") + "Config.yml" +
                        ChatColor.GREEN + " created");

        }
        config = YamlConfiguration.loadConfiguration(conFile);

        custom();
    }

    void custom() {
        if (!_oreGen.getDataFolder().exists()) {
            _oreGen.getDataFolder().mkdir();
        }
        customFile = new File(_oreGen.getDataFolder(), "custom.yml");
        if (!customFile.exists()) {
            _oreGen.saveResource("custom.yml", false);
            Bukkit.getServer().getConsoleSender().sendMessage(ChatUtils.getPrefix() +
                            ChatColor.of("#A0E4CB") + "Custom.yml" +
                            ChatColor.GREEN + " created");

        }

        custom = YamlConfiguration.loadConfiguration(customFile);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public FileConfiguration getCustom() {
        return custom;
    }

    public void saveConfig() {
        try {
            config.save(conFile);
            Bukkit.getServer().getConsoleSender().sendMessage(ChatUtils.getPrefix() +
                            ChatColor.of("#A0E4CB") + "Config.yml" +
                            ChatColor.GREEN + " has been saved");
        } catch (IOException ex) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatUtils.getPrefix() +
                            ChatColor.of("#A0E4CB") + "Config.yml" +
                            ChatColor.RED + " could not be saved");
        }

        try {
            custom.save(customFile);
            Bukkit.getServer().getConsoleSender().sendMessage(ChatUtils.getPrefix() +
                            ChatColor.of("#A0E4CB") + "Custom.yml" +
                            ChatColor.GREEN + " has been saved");
        } catch (IOException ex) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatUtils.getPrefix() +
                            ChatColor.of("#A0E4CB") + "Custom.yml" +
                            ChatColor.RED + " could not be saved");
        }
    }

    public void reloadConfig() {
        saveConfig();
        config = YamlConfiguration.loadConfiguration(conFile);
        Bukkit.getServer().getConsoleSender().sendMessage(ChatUtils.getPrefix() +
                        ChatColor.of("#A0E4CB") + "Config.yml" +
                        ChatColor.GREEN + " has been reloaded");

        custom = YamlConfiguration.loadConfiguration(customFile);
        Bukkit.getServer().getConsoleSender().sendMessage(ChatUtils.getPrefix() +
                        ChatColor.of("#A0E4CB") + "Custom.yml" +
                        ChatColor.GREEN + " has been reloaded");
    }



}
