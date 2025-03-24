package com.fenixenforge.FenixenCore.Handlers.Commands;

import com.fenixenforge.Core.Utils.Messages;
import java.lang.reflect.Modifier;
import java.util.Set;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

public class Commands {

    public static void RegisterAll(JavaPlugin plugin, String Path, boolean debug) {
        String path = plugin.getClass().getPackage().getName() + ".";
        Reflections reflection = new Reflections(path + Path);

        if (debug) {
            Messages.Console("&d" + plugin.getName() + "&a Cargando Comandos de " + path + Path);
        }


        Set<Class<? extends CBuilder>> CClasses = reflection.getSubTypesOf(CBuilder.class);

        for (Class<? extends CBuilder> clazz : CClasses) {
            if (Modifier.isAbstract(clazz.getModifiers()) || clazz.equals(MCBuilder.class) || clazz.equals(SCBuilder.class)) {
                continue;
            }

            if (!MCBuilder.class.isAssignableFrom(clazz)) {
                continue;
            }

            try {

                MCBuilder mCommand = (MCBuilder) clazz.getDeclaredConstructor().newInstance();

                if (mCommand.getName()==null) {
                    if (debug) {
                        Messages.Console("&d" + plugin.getName() + "&c La clase " + clazz.getName() + " no tiene nombre asignado correctamente para un Comando Principal.");
                    }
                    continue;
                }
                mCommand.register(plugin);
                if (debug) {
                    Messages.Console("&d" + plugin.getName() + "&a Registrando Comando principal: " + " ( " + mCommand.getName() + " )");
                }
            } catch (Exception e) {
                if (debug) {
                    Messages.Console("&d" + plugin.getName() + "&c Error registrando comando principal: " + clazz.getName());
                }
                e.printStackTrace();
            }
        }

        for (Class<? extends CBuilder> clazz : CClasses) {
            if (Modifier.isAbstract(clazz.getModifiers()) || clazz.equals(MCBuilder.class) || clazz.equals(SCBuilder.class)) {
                continue;
            }

            if (!SCBuilder.class.isAssignableFrom(clazz)) {
                continue;
            }

            try {
                SCBuilder sCommand = (SCBuilder) clazz.getDeclaredConstructor().newInstance();
                if (sCommand.name==null) {
                    if (debug) {
                        Messages.Console("&d" + plugin.getName() + "&c La clase " + clazz.getName() + " no tiene nombre asignado correctamente para un SubComando.");
                    }
                    continue;
                }

                String chain = sCommand.getmCChain();

                if (chain==null || chain.isEmpty()) {
                    if (debug) {
                        Messages.Console("&d" + plugin.getName() + "&c La clase " + clazz.getName() + " no tiene cadena de comando principal definida.");
                    }
                }

                String[] parts = chain.split(" ");

                MCBuilder main = CHolder.getMCommand(parts[0]);

                if (main==null) {
                    if (debug) {
                        Messages.Console("&d" + plugin.getName() + "&c No se encontró el comando principal '" + parts[0] + "' para el subcomando " + sCommand.name);
                    }
                    continue;
                }

                CBuilder<?> parent = main;

                for (int i = 1; i < parts.length; i++) {
                    if (parent.subCommands.containsKey(parts[i])) {
                        parent = parent.subCommands.get(parts[i]);
                    } else {
                        if (debug) {
                            Messages.Console("&d" + plugin.getName() + "&c No se encontró el subcomando '" + parts[i] + "' en la cadena '" + chain + "' para " + sCommand.name);
                        }
                        parent = null;
                        break;
                    }
                }

                if (parent!=null) {
                    parent.addSubcommand(sCommand.name, sCommand);
                    if (debug) {
                        Messages.Console("&d" + plugin.getName() + "&a Registrando SubComando: " + " ( " + sCommand.name + " ) " + " bajo la cadena '" + chain + "'");
                    }
                }


            } catch (Exception e) {
                if (debug) {
                    Messages.Console("&d" + plugin.getName() + "&c Error registrando subcomando: " + clazz.getName());
                }
                e.printStackTrace();
            }

        }
    }
}
