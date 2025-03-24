package com.fenixenforge.FenixenCore.Handlers.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class CRegister {
    public static CommandMap getCommandMap() {
        try {
            Field CMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            CMapField.setAccessible(true);
            return (CommandMap) CMapField.get(Bukkit.getServer());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void registerDynamicCommand(Command command, JavaPlugin plugin) {
        CommandMap commandMap = getCommandMap();
        if (commandMap != null) {
            commandMap.register(plugin.getDescription().getName(), command);
        }
    }
}
