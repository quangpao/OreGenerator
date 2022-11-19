package me.microdragon.oregenerator.utils;

import com.google.common.base.Charsets;
import me.microdragon.oregenerator.OreGenerator;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigUtils {

    private static final OreGenerator _oreGen = OreGenerator.getPlugin();

    private FileConfiguration config;
    private File conFile;

    private FileConfiguration custom;
    private File customFile;

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

        if (conFile == null) {
            conFile = new File(_oreGen.getDataFolder(), "config.yml");
        }

        if (customFile == null) {
            customFile = new File(_oreGen.getDataFolder(), "custom.yml");
        }

        config = YamlConfiguration.loadConfiguration(conFile);
        custom = YamlConfiguration.loadConfiguration(customFile);

        final InputStream defConfigStream = _oreGen.getResource("config.yml");
        final InputStream defCustomStream = _oreGen.getResource("custom.yml");

        if (defConfigStream == null || defCustomStream == null) {
            return;
        }

        config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
        custom.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defCustomStream, Charsets.UTF_8)));
    }



}
