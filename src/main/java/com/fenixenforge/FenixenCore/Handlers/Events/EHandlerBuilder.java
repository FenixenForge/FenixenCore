package com.fenixenforge.FenixenCore.Handlers.Events;

import java.util.function.Consumer;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class EHandlerBuilder {

    private String name;
    private Class<? extends Event> eventClass;
    private Consumer<Event> executor;
    private EventPriority priority = EventPriority.NORMAL;
    private boolean ignoreCancelled = false;

    public <T extends Event> EHandlerBuilder event(Class<T> eventClass) {
        this.eventClass = eventClass;
        return this;
    }

    @SuppressWarnings("unchecked") public <E extends Event> EHandlerBuilder execute(Consumer<E> executor) {
        this.executor = (Consumer<Event>) executor;
        return this;
    }

    public EHandlerBuilder priority(EventPriority priority) {
        this.priority = priority;
        return this;
    }

    public EHandlerBuilder ignoreCancelled(boolean ignoreCancelled) {
        this.ignoreCancelled = ignoreCancelled;
        return this;
    }

    public void register(JavaPlugin plugin) {
        if (eventClass==null || !Event.class.isAssignableFrom(eventClass)) {

            throw new IllegalStateException("No se ha definido una clase de evento válida.");
        }
        if (executor==null) {
            throw new IllegalStateException("No se ha definido un ejecutor para el evento.");
        }

        Class<? extends Event> specificEventClass = eventClass;

        plugin.getServer().getPluginManager().registerEvent(specificEventClass, new Listener() {
            // Implementamos el método de evento de manera genérica
            @SuppressWarnings("unchecked") public void onEvent(Event event) {
                if (specificEventClass.isInstance(event)) {
                    executor.accept(event);
                }
            }
        }, priority, (listener, event) -> {
            if (specificEventClass.isInstance(event)) {
                executor.accept(event);
            }
        }, plugin, ignoreCancelled);
    }


}
