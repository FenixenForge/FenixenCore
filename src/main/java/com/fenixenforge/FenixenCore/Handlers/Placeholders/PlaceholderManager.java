package com.fenixenforge.FenixenCore.Handlers.Placeholders;

import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.util.Set;

public class PlaceholderManager {
    private static CustomPlaceholderExpansion expansion;

    public static void registerExpansion(JavaPlugin plugin, String packagePath) {

        expansion = new CustomPlaceholderExpansion(plugin);
        if (expansion.register()) {
            plugin.getLogger().info("CustomPlaceholderExpansion registrado correctamente.");
        } else {
            plugin.getLogger().severe("No se pudo registrar CustomPlaceholderExpansion.");
        }

        Reflections reflections = new Reflections(packagePath);
        Set<Class<? extends PlaceholderHolder>> classes = reflections.getSubTypesOf(PlaceholderHolder.class);
        for (Class<? extends PlaceholderHolder> clazz : classes) {
            try {
                PlaceholderHolder holder = clazz.getDeclaredConstructor().newInstance();
                holder.registerPlaceholders();
                plugin.getLogger().info("PlaceholderHolder registrado: " + clazz.getSimpleName());
            } catch (Exception e) {
                plugin.getLogger().severe("Error al registrar PlaceholderHolder: " + clazz.getName());
                e.printStackTrace();
            }
        }
    }

    public static void registerPlaceholder(String identifier, PlaceholderFunction function) {
        if (expansion != null) {
            expansion.addPlaceholder(identifier, function);
        }
    }
}