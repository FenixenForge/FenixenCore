package com.fenixenforge.FenixenCore.Handlers.Events;

import com.fenixenforge.Core.Utils.Messages;
import java.lang.reflect.Modifier;
import java.util.Set;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

public class Events {

    public static EHandlerBuilder event() {
        return new EHandlerBuilder();
    }

    public static void RegisterAll(JavaPlugin
                    plugin,
            String Path, boolean debug) {
        String path = plugin.getClass().getPackage().getName() + ".";
        Reflections reflections = new Reflections(path + Path);
        Set<Class<? extends EHandlerBuilder>> classes = reflections.getSubTypesOf(EHandlerBuilder.class);

        for (Class<? extends EHandlerBuilder> clazz : classes) {
            if (Modifier.isAbstract(clazz.getModifiers())) {
                continue;
            }

            try {
                EHandlerBuilder handler = clazz.getDeclaredConstructor().newInstance();
                handler.register(plugin);
                if (debug) {
                    Messages.Console("&d" + plugin.getName() + "&b Registrado event handler: " + clazz.getSimpleName());
                }
            } catch (Exception e) {
                if (debug) {
                    Messages.Console("&d" + plugin.getName() + "&c Error al registrar el event handler: " + clazz.getName());
                }
                e.printStackTrace();
            }
        }
    }
}

