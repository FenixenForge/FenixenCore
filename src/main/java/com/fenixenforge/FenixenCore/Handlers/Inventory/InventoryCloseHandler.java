package com.fenixenforge.FenixenCore.Handlers.Inventory;

import org.bukkit.event.inventory.InventoryCloseEvent;

@FunctionalInterface public interface InventoryCloseHandler {
    void handle(InventoryCloseEvent event);
}
