package com.fenixenforge.FenixenCore.Handlers.Placeholders;

import com.fenixenforge.FenixenCore.Utils.Messages;
import java.util.Set;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

public class Placeholder {
    private static CPExpansion expansion;

    public static void registerExpansion(JavaPlugin plugin, String packagePath, boolean debug) {

        expansion = new CPExpansion(plugin);
        if (expansion.register()) {

            if (debug) {
                Messages.Console("&d" + plugin.getName() + "&a Expansion registrada correctamente.");
            }

        } else {

            if (debug) {
                Messages.Console("&d" + plugin.getName() + "&c No se pudo registrar Expansion.");
            }
        }
        String path = plugin.getClass().getPackage().getName() + ".";
        Reflections reflections = new Reflections(path + packagePath);
        Set<Class<? extends PHolder>> classes = reflections.getSubTypesOf(PHolder.class);
        for (Class<? extends PHolder> clazz : classes) {
            try {
                PHolder holder = clazz.getDeclaredConstructor().newInstance();
                holder.registerPlaceholders();

                if (debug) {
                    Messages.Console("&d" + plugin.getName() + "&a PlaceHolder registrado correctamente: " + clazz.getSimpleName());
                }
            } catch (Exception e) {

                if (debug) {
                    Messages.Console("&d" + plugin.getName() + "&c Error al registrar PlaceHolder: " + clazz.getName());
                }

                e.printStackTrace();
            }
        }
    }

    public static String Process(String message) {
        if (message==null || message.isEmpty()) return "";

        Player player = Bukkit.getOnlinePlayers().stream().findFirst().orElse(null);
        if (player!=null) {
            return PlaceholderAPI.setPlaceholders(player, message);
        }
        return message;
    }


    public static void registerPlaceholder(String identifier, PFunction function) {
        if (expansion!=null) {
            expansion.addPlaceholder(identifier, function);
        }
    }
}
