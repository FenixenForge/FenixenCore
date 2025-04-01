package com.fenixenforge.FenixenCore.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class Messages {
    private static CommandSender sender;
    private static final Pattern HEX = Pattern.compile("#([A-Fa-f0-9]{6})");

    public static String Color(String message) {
        Matcher matcher = HEX.matcher(message);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            String hexColor = matcher.group(1);
            matcher.appendReplacement(buffer, ChatColor.of("#" + hexColor).toString());
        }

        matcher.appendTail(buffer);
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', buffer.toString());
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

    public static TextComponent getTextComponent(String message) {
        return new TextComponent(Color(message));
    }
}
