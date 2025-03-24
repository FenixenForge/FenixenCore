package com.fenixenforge.FenixenCore.Handlers.Placeholders;

import java.util.HashMap;
import java.util.Map;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class CPExpansion extends PlaceholderExpansion {

    private final JavaPlugin plugin;
    private final Map<String, PFunction> placeholders = new HashMap<>();

    public CPExpansion(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void addPlaceholder(String identifier, PFunction function) {
        placeholders.put(identifier, function);
    }

    @Override public @NotNull String getIdentifier() {
        return plugin.getName();
    }

    @Override public @NotNull String getAuthor() {
        return String.join(", ", plugin.getDescription().getAuthors());
    }

    @Override public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override public @NotNull String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (placeholders.containsKey(identifier)) {
            return placeholders.get(identifier).apply(player);
        }
        return null;
    }

}
