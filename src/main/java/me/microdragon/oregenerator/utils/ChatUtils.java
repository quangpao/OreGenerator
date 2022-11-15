package me.microdragon.oregenerator.utils;

import net.md_5.bungee.api.ChatColor;

public class ChatUtils {

    public static String getPrefix() {
        return ChatColor.of("#8EC3B0") + "O" +
                ChatColor.of("#9ED5C5") + "r" +
                ChatColor.of("#BCEAD5") + "e" +
                ChatColor.of("#DEF5E5") + "Generator" +
                ChatColor.RESET + " - ";
    }

}
