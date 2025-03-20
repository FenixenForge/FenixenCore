package com.fenixenforge.FenixenCore.Handlers.Commands;

import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.Set;

public class Commands {

    public static void RegisterAll(JavaPlugin plugin, String packagePath) {
        Reflections reflections = new Reflections(packagePath);
        Set<Class<? extends AbstractCommandBuilder>> commandClasses = reflections.getSubTypesOf(AbstractCommandBuilder.class);

        for (Class<? extends AbstractCommandBuilder> clazz : commandClasses) {
            if (Modifier.isAbstract(clazz.getModifiers()) || !MainCommandBuilder.class.isAssignableFrom(clazz)) {
                continue;
            }
            try {
                MainCommandBuilder mainCommand = (MainCommandBuilder) clazz.getDeclaredConstructor().newInstance();
                if (mainCommand.name == null) {
                    plugin.getLogger().warning("La clase " + clazz.getName() + " no tiene nombre asignado. Se omitirá su registro.");
                    continue;
                }
                mainCommand.register(plugin);
                plugin.getLogger().info("Registrado comando principal: " + mainCommand.name);
            } catch (Exception e) {
                plugin.getLogger().severe("Error registrando comando principal: " + clazz.getName());
                e.printStackTrace();
            }
        }

        for (Class<? extends AbstractCommandBuilder> clazz : commandClasses) {
            if (Modifier.isAbstract(clazz.getModifiers()) || !SubCommandBuilder.class.isAssignableFrom(clazz)) {
                continue;
            }
            try {
                SubCommandBuilder subCommand = (SubCommandBuilder) clazz.getDeclaredConstructor().newInstance();
                if (subCommand.name == null) {
                    plugin.getLogger().warning("La clase " + clazz.getName() + " no tiene nombre asignado. Se omitirá su registro.");
                    continue;
                }
                String chain = subCommand.getMainCommandChain();
                if (chain == null || chain.isEmpty()) {
                    plugin.getLogger().warning("El subcomando " + subCommand.name + " no tiene cadena de comando principal definida.");
                    continue;
                }
                String[] parts = chain.split(" ");
                MainCommandBuilder main = CommandsHolder.getMainCommand(parts[0]);
                if (main == null) {
                    plugin.getLogger().severe("No se encontró el comando principal '" + parts[0] + "' para el subcomando " + subCommand.name);
                    continue;
                }
                AbstractCommandBuilder<?> parent = main;
                for (int i = 1; i < parts.length; i++) {
                    if (parent.subcommands.containsKey(parts[i])) {
                        parent = parent.subcommands.get(parts[i]);
                    } else {
                        plugin.getLogger().severe("No se encontró el subcomando '" + parts[i] + "' en la cadena '" + chain + "' para " + subCommand.name);
                        parent = null;
                        break;
                    }
                }
                if (parent != null) {
                    parent.addSubcommand(subCommand.name, subCommand);
                    plugin.getLogger().info("Registrado subcomando: " + subCommand.name + " bajo la cadena '" + chain + "'");
                }
            } catch (Exception e) {
                plugin.getLogger().severe("Error registrando subcomando: " + clazz.getName());
                e.printStackTrace();
            }
        }
    }
}
