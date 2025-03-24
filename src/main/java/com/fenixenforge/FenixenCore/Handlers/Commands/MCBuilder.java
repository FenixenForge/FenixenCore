package com.fenixenforge.FenixenCore.Handlers.Commands;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class MCBuilder extends CBuilder<MCBuilder> {
    public String getName() {
        return name;
    }

    public MCBuilder register(JavaPlugin plugin) {
        if (this.getClass().equals(MCBuilder.class)) {
            return this;
        }

        PluginCommand pluginCommand = plugin.getCommand(name);

        if (pluginCommand==null) {
            DCommands dCommands = new DCommands(name, this);
            dCommands.setAliases(getAliases());
            CRegister.registerDynamicCommand(dCommands, plugin);
        } else {
            pluginCommand.setExecutor(this);
            pluginCommand.setTabCompleter(this);
        }
        CHolder.rMCommand(this);
        return this;
    }
}
