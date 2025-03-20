package com.fenixenforge.FenixenCore.Handlers.Inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.function.Consumer;

public class InventoryBuilder {
    private String name;
    private int size;
    private String title;
    private Consumer<InventoryClickEvent> clickHandler;
    private Consumer<InventoryCloseEvent> closeHandler;
    private Consumer<Inventory> itemsConsumer; // Para configurar items
    private Inventory inventory;

    // Método estático para iniciar el builder
    public static InventoryBuilder inventario() {
        return new InventoryBuilder();
    }

    public InventoryBuilder name(String name) {
        this.name = name;
        return this;
    }

    public InventoryBuilder size(int size) {
        this.size = size;
        return this;
    }

    public InventoryBuilder title(String title) {
        this.title = title;
        return this;
    }
    public InventoryBuilder onclick(Consumer<InventoryClickEvent> handler) {
        this.clickHandler = handler;
        return this;
    }

    public InventoryBuilder onclick(InventoryClickHandler handler) {
        this.clickHandler = handler::handle;
        return this;
    }

    public InventoryBuilder onclose(Consumer<InventoryCloseEvent> handler) {
        this.closeHandler = handler;
        return this;
    }

    public InventoryBuilder onclose(InventoryCloseHandler handler) {
        this.closeHandler = handler::handle;
        return this;
    }

    // Método para configurar items; se le pasa una lambda que recibe el Inventory y lo configura
    public InventoryBuilder items(Consumer<Inventory> itemsConsumer) {
        this.itemsConsumer = itemsConsumer;
        return this;
    }

    public void open(Player player) {
        this.inventory = Bukkit.createInventory(null, size, title);

        if (itemsConsumer != null) {
            itemsConsumer.accept(inventory);
        }

        InventoryManager.register(this.inventory, this);

        player.openInventory(this.inventory);
    }

    public Consumer<InventoryClickEvent> getClickHandler() {
        return clickHandler;
    }

    public Consumer<InventoryCloseEvent> getCloseHandler() {
        return closeHandler;
    }

    public String getName() {
        return name;
    }
}
