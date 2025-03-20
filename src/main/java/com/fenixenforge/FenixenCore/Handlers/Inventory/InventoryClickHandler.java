package com.fenixenforge.FenixenCore.Handlers.Inventory;

import org.bukkit.event.inventory.InventoryClickEvent;

@FunctionalInterface public interface InventoryClickHandler {
    void handle(InventoryClickEvent event);
}