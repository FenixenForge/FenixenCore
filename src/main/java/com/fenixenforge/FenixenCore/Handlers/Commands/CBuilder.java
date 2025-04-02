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
        this.name = name.toLowerCase();  // Convertir el nombre del comando a minúsculas
        return (T) this;
    }

    @SuppressWarnings("unchecked") public T aliases(String... aliases) {
        for (String alias : aliases) {
            this.aliases.add(alias.toLowerCase());  // Convertir los alias a minúsculas
        }
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
        subCommands.put(name.toLowerCase(), subCommand);  // Convertir los subcomandos a minúsculas
        return (T) this;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = args.length > 0 ? args[0].toLowerCase():"";
        if (args.length > 0 && this.subCommands.containsKey(commandName)) {
            return ((CBuilder) this.subCommands.get(commandName)).onCommand(sender, command, label, args);
        } else {
            return this.executor!=null ? this.executor.onCommand(sender, command, label, args):false;
        }
    }

    @Override public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length==1) {
            return filterMatchingCommands(subCommands.keySet(), args[0].toLowerCase());
        }

        if (args.length > 1 && subCommands.containsKey(args[0].toLowerCase())) {
            return subCommands.get(args[0].toLowerCase()).onTabComplete(sender, command, label, removeFirstArg(args));
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

    private List<String> filterMatchingCommands(Iterable<String> commands, String start) {
        List<String> matchingCommands = new ArrayList<>();
        for (String command : commands) {
            if (command.startsWith(start)) {
                matchingCommands.add(command);
            }
        }
        return matchingCommands;
    }
}
