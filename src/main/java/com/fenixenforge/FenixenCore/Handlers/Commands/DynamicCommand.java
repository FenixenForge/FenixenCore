package com.fenixenforge.FenixenCore.Handlers.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class DynamicCommand extends Command {
    private final MainCommandBuilder builder;

    protected DynamicCommand(String name, MainCommandBuilder builder) {
        super(name);
        this.builder = builder;
    }

    @Override public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        return builder.onCommand(sender, this, commandLabel, args);
    }

    @Override public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
        return builder.onTabComplete(sender, this, alias, args);
    }
}
