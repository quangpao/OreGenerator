package me.microdragon.oregenerator.commands;

import me.microdragon.oregenerator.OreGenerator;
import me.microdragon.oregenerator.utils.ChatUtils;
import me.microdragon.oregenerator.utils.ConfigUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {
    private final ConfigUtils _configUtils = OreGenerator.getConfigUtils();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("oregen")) {
            if (args.length == 0) {
                sender.sendMessage("§6OreGenerator §7- §fVersion 3.0.0-beta");
                sender.sendMessage("§6/oregen reload §7- §fReloads the config");
                return true;
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    _configUtils.reloadConfig();
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatUtils.getPrefix() + ChatColor.of("#A0E4CB") + "Config reloaded");
                    return true;
                }
            }
        }
        return false;
    }
}
