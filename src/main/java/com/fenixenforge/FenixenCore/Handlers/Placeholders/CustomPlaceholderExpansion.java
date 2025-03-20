package com.fenixenforge.FenixenCore.Handlers.Placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class CustomPlaceholderExpansion extends PlaceholderExpansion {
    private final JavaPlugin plugin;
    private final Map<String, PlaceholderFunction> placeholders = new HashMap<>();

    public CustomPlaceholderExpansion(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void addPlaceholder(String identifier, PlaceholderFunction function) {
        placeholders.put(identifier, function);
    }

    @Override public String getIdentifier() {
        return plugin.getName();
    }

    @Override public String getAuthor() {
        return String.join(", ", plugin.getDescription().getAuthors());
    }

    @Override public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override public String onPlaceholderRequest(Player player, String identifier) {
        if (placeholders.containsKey(identifier)) {
            return placeholders.get(identifier).apply(player);
        }
        return null;
    }
}