package me.microdragon.oregenerator.commands;

import me.microdragon.oregenerator.OreGenerator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {

    private final OreGenerator _oreGen = OreGenerator.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("oregen")) {
            if (args.length == 0) {
                sender.sendMessage("§6OreGenerator §7- §fVersion 2.2");
                sender.sendMessage("§6/oregen reload §7- §fReloads the config");
                return true;
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    _oreGen.reloadConfig();
                    sender.sendMessage("§6OreGenerator §7- §fConfig reloaded");
                    _oreGen.logger.info("OreGenerator - Config reloaded");
                    return true;
                }
            }
        }
        return false;
    }
}
