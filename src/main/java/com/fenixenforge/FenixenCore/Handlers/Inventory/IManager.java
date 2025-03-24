package com.fenixenforge.Core.Handlers.Inventory;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class IManager implements Listener {

    private static final Map<Inventory, IBuilder> inventoryMap = new HashMap<>();

    public static void register(Inventory inventory, IBuilder builder) {
        inventoryMap.put(inventory, builder);
    }

    public static void unregister(Inventory inventory) {
        inventoryMap.remove(inventory);
    }


}
