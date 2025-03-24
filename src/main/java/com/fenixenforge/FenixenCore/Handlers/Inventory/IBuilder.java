package com.fenixenforge.FenixenCore.Handlers.Inventory;

import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;

public class IBuilder {
    private String name;
    private int size;
    private String title;
    private Consumer<InventoryEvent> eventHandler;
    private Consumer<Inventory> itemsConsumer;
    private Inventory inventory;

    public static IBuilder inventario() {
        return new IBuilder();
    }

    public IBuilder name(String name) {
        this.name = name;
        return this;
    }

    public IBuilder size(int size) {
        this.size = size;
        return this;
    }

    public IBuilder title(String title) {
        this.title = title;
        return this;
    }

    public IBuilder onevent(Consumer<InventoryEvent> handler) {
        this.eventHandler = handler;
        return this;
    }

    public IBuilder items(Consumer<Inventory> itemsConsumer) {
        this.itemsConsumer = itemsConsumer;
        return this;
    }

    public void open(Player player) {
        this.inventory = Bukkit.createInventory(null, size, title);

        if (itemsConsumer!=null) {
            itemsConsumer.accept(inventory);
        }

        IManager.register(this.inventory, this);
        player.openInventory(inventory);
    }

    public Consumer<InventoryEvent> getEventHandler() {
        return eventHandler;
    }

    public String getName() {
        return name;
    }
}
