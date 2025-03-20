package com.fenixenforge.FenixenCore.Handlers.Placeholders;

import org.bukkit.entity.Player;

@FunctionalInterface public interface PlaceholderFunction {
    String apply(Player player);
}