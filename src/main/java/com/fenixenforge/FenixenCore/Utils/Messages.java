package com.fenixenforge.FenixenCore.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class Messages {
    private static CommandSender sender;

    public static final Pattern HEX = Pattern.compile("#([A-Fa-f0-9]{6})");

    public static String Color(String message) {
        Matcher matcher = HEX.matcher(message);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(buffer, ChatColor.of("#" + matcher.group(1)).toString());
        }

        matcher.appendTail(buffer);
        message = buffer.toString();

        return org.bukkit.ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void Console(String message) {
        Bukkit.getConsoleSender().sendMessage(Color(message));
    }

    public static void setSender(CommandSender sender) {
        Messages.sender = sender;
    }

    public static void Sender(String message) {
        sender.sendMessage(Color(message));
    }
}
