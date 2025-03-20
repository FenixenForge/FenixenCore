package com.fenixenforge.FenixenCore.Handlers.Events;

import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.Set;

public class Events {

    public static EventHandlerBuilder event() {
        return new EventHandlerBuilder();
    }


    public static void registerAll(JavaPlugin plugin, String packagePath) {
        Reflections reflections = new Reflections(packagePath);
        Set<Class<? extends EventHandlerBuilder>> classes = reflections.getSubTypesOf(EventHandlerBuilder.class);

        for (Class<? extends EventHandlerBuilder> clazz : classes) {
            if (Modifier.isAbstract(clazz.getModifiers())) {
                continue;
            }

            try {
                EventHandlerBuilder handler = clazz.getDeclaredConstructor().newInstance();
                handler.register(plugin);
                plugin.getLogger().info("Registrado event handler: " + clazz.getSimpleName());
            } catch (Exception e) {
                plugin.getLogger().severe("Error al registrar el event handler: " + clazz.getName());
                e.printStackTrace();
            }
        }
    }
}
