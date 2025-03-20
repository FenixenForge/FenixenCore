package com.fenixenforge.FenixenCore.Handlers.Commands;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class MainCommandBuilder extends AbstractCommandBuilder<MainCommandBuilder> {

    public String getName() {
        return name;
    }

    public MainCommandBuilder register(JavaPlugin plugin) {
        // Evitamos registrar la clase base
        if (this.getClass().equals(MainCommandBuilder.class)) {
            return this;
        }
        PluginCommand pluginCommand = plugin.getCommand(name);
        if (pluginCommand == null) {
            plugin.getLogger().info("El comando '" + name + "' no está definido en plugin.yml. Se creará dinámicamente.");
            DynamicCommand dynamicCommand = new DynamicCommand(name, this);
            dynamicCommand.setAliases(getAliases());
            CommandRegisterUtil.registerDynamicCommand(dynamicCommand, plugin);
        } else {
            pluginCommand.setExecutor(this);
            pluginCommand.setTabCompleter(this);
        }
        CommandsHolder.registerMainCommand(this);
        return this;
    }
}