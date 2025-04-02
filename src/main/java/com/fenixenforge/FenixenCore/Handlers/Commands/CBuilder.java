package com.fenixenforge.FenixenCore.Handlers.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public abstract class CBuilder<T extends CBuilder<T>> implements CommandExecutor, TabCompleter {

    protected String name;
    protected String permission;
    protected List<String> aliases = new ArrayList<>();
    protected final Map<String, CBuilder<?>> subCommands = new HashMap<>();
    protected CommandExecutor executor;
    private String noPermissionMsg;

    @SuppressWarnings("unchecked") public T name(String name) {
        this.name = name;
        return (T) this;
    }


    @SuppressWarnings("unchecked") public T aliases(String... aliases) {
        this.aliases.addAll(List.of(aliases));
        return (T) this;
    }

    public List<String> getAliases() {
        return aliases;
    }

    @SuppressWarnings("unchecked") public T execute(CommandExecutor executor) {
        this.executor = executor;
        return (T) this;
    }

    @SuppressWarnings("unchecked") public T addSubcommand(String name, CBuilder<?> subCommand) {
        subCommands.put(name, subCommand);
        return (T) this;
    }


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && this.subCommands.containsKey(args[0])) {
            return ((CBuilder) this.subCommands.get(args[0])).onCommand(sender, command, label, args);
        } else {
            return this.executor!=null ? this.executor.onCommand(sender, command, label, args):false;
        }
    }

    @Override public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length==1) {
            return new ArrayList<>(subCommands.keySet());
        }

        if (args.length > 1 && subCommands.containsKey(args[0])) {

            return subCommands.get(args[0]).onTabComplete(sender, command, label, removeFirstArg(args));
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
