package com.fenixenforge.FenixenCore.Handlers.Placeholders;

import org.bukkit.entity.Player;

@FunctionalInterface public interface PFunction {
    String apply(Player player);
}
