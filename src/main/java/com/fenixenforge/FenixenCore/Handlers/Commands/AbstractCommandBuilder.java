package com.fenixenforge.FenixenCore.Handlers.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractCommandBuilder<T extends AbstractCommandBuilder<T>> implements CommandExecutor, TabCompleter {
    protected String name;
    protected String permission;
    protected List<String> aliases = new ArrayList<>();
    protected final Map<String, AbstractCommandBuilder<?>> subcommands = new HashMap<>();
    protected CommandExecutor executor;

    @SuppressWarnings("unchecked") public T name(String name) {
        this.name = name;
        return (T) this;
    }

    @SuppressWarnings("unchecked") public T permission(String permission) {
        this.permission = permission;
        return (T) this;
    }

    @SuppressWarnings("unchecked") public T aliases(String... aliases) {
        this.aliases.addAll(Arrays.asList(aliases));
        return (T) this;
    }

    public List<String> getAliases() {
        return aliases;
    }

    @SuppressWarnings("unchecked") public T execute(CommandExecutor executor) {
        this.executor = executor;
        return (T) this;
    }

    @SuppressWarnings("unchecked") public T addSubcommand(String name, AbstractCommandBuilder<?> subcommand) {
        subcommands.put(name, subcommand);
        return (T) this;
    }

    @Override public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length > 0 && subcommands.containsKey(args[0])) {
            return subcommands.get(args[0]).onCommand(sender, cmd, label, removeFirstArg(args));
        }
        if (executor != null) {
            return executor.onCommand(sender, cmd, label, args);
        }
        return false;
    }

    @Override public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(subcommands.keySet());
        }
        if (args.length > 1 && subcommands.containsKey(args[0])) {
            return subcommands.get(args[0]).onTabComplete(sender, cmd, alias, removeFirstArg(args));
        }
        return new ArrayList<>();
    }

    private String[] removeFirstArg(String[] args) {
        if (args.length <= 1) {
            return new String[0];
        }
        String[] newArgs = new String[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, args.length - 1);
        return newArgs;
    }
}
