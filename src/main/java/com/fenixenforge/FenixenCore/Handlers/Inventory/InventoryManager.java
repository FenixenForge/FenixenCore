package com.fenixenforge.FenixenCore.Handlers.Inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class InventoryManager
        implements Listener {
    private static final Map<Inventory, InventoryBuilder> inventoryMap = new HashMap<>();

    public static void register(Inventory inventory, InventoryBuilder builder) {
        inventoryMap.put(inventory, builder);
    }

    public static void unregister(Inventory inventory) {
        inventoryMap.remove(inventory);
    }

    @EventHandler public void onInventoryClick(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        if (inventoryMap.containsKey(inv)) {
            InventoryBuilder builder = inventoryMap.get(inv);
            if (builder.getClickHandler() != null) {
                builder.getClickHandler().accept(event);
            }
        }
    }

    @EventHandler public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inv = event.getInventory();
        if (inventoryMap.containsKey(inv)) {
            InventoryBuilder builder = inventoryMap.get(inv);
            if (builder.getCloseHandler() != null) {
                builder.getCloseHandler().accept(event);
            }
            unregister(inv);
        }
    }

    public static void registerEvents(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new InventoryManager(), plugin);
    }

    public static void registerAll(JavaPlugin plugin, String packageName) {
        try {
            ClassLoader classLoader = plugin.getClass().getClassLoader();
            String path = packageName.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                if (resource.getProtocol().equals("jar")) {
                    processJar(resource, packageName, classLoader, plugin);
                } else {
                    processDirectory(new File(resource.getFile()), packageName, classLoader, plugin);
                }
            }
        } catch (IOException | ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void processJar(URL resource, String packageName, ClassLoader classLoader, JavaPlugin plugin)
            throws IOException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
        JarFile jarFile = ((JarURLConnection) resource.openConnection()).getJarFile();
        Enumeration<JarEntry> entries = jarFile.entries();
        String path = packageName.replace('.', '/');

        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String entryName = entry.getName();
            if (entryName.startsWith(path) && entryName.endsWith(".class")) {
                String className = entryName.replace('/', '.').substring(0, entryName.length() - 6);
                processClass(className, classLoader, plugin);
            }
        }
    }

    private static void processDirectory(File directory, String packageName, ClassLoader classLoader, JavaPlugin plugin)
            throws ClassNotFoundException, IllegalAccessException, InvocationTargetException {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            String fileName = file.getName();
            if (file.isDirectory()) {
                processDirectory(file, packageName + "." + fileName, classLoader, plugin);
            } else if (fileName.endsWith(".class")) {
                String className = packageName + '.' + fileName.substring(0, fileName.length() - 6);
                processClass(className, classLoader, plugin);
            }
        }
    }

    private static void processClass(String className, ClassLoader classLoader, JavaPlugin plugin)
            throws ClassNotFoundException, IllegalAccessException, InvocationTargetException {
        Class<?> clazz = Class.forName(className, true, classLoader);
        try {
            Method registerMethod = clazz.getMethod("register", JavaPlugin.class);
            registerMethod.invoke(null, plugin);
        } catch (NoSuchMethodException e) {
        }
    }
}