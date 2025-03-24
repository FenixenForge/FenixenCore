package com.fenixenforge.FenixenCore.Handlers.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class DCommands extends Command {

    private final MCBuilder builder;

    protected DCommands(String name, MCBuilder builder) {
        super(name);
        this.builder = builder;
    }

    @Override public boolean execute(CommandSender sender, String label, String[] args) {
        return builder.onCommand(sender, this, label, args);
    }

    @Override public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
        return builder.onTabComplete(sender, this, alias, args);
    }
}
