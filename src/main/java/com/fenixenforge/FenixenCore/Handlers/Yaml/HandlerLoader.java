package com.fenixenforge.FenixenCore.Handlers.Yaml;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HandlerLoader {
    private static JavaPlugin plugin;
    private static final Map<Class<? extends YamlFile>, YamlDataHolder> loadedYamlFiles = new HashMap<>();

    public static void registerAll(JavaPlugin pluginInstance, String basePackage) {
        plugin = pluginInstance;

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        Reflections reflections = new Reflections(basePackage);
        Set<Class<? extends YamlFile>> classes = reflections.getSubTypesOf(YamlFile.class);

        for (Class<? extends YamlFile> clazz : classes) {
            try {

                YamlData annotation = clazz.getAnnotation(YamlData.class);
                if (annotation == null) {
                    continue;
                }

                String fileName = annotation.name() + ".yml";
                String folder = annotation.folder();
                File file;
                if (!folder.isEmpty()) {
                    file = new File(plugin.getDataFolder(), folder + File.separator + fileName);
                } else {
                    file = new File(plugin.getDataFolder(), fileName);
                }
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                if (!file.exists()) {
                    String resourcePath = (!folder.isEmpty() ? folder.replace(File.separatorChar, '/') + "/" : "") + fileName;
                    plugin.saveResource(resourcePath, false);
                }

                FileConfiguration config = YamlConfiguration.loadConfiguration(file);

                Method initMethod = clazz.getMethod("init", FileConfiguration.class);
                initMethod.invoke(null, config);

                loadedYamlFiles.put(clazz, new YamlDataHolder(file, config));
            } catch (NoSuchMethodException ignored) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveAll() {
        for (YamlDataHolder data : loadedYamlFiles.values()) {
            try {
                ((YamlConfiguration) data.getConfig()).save(data.getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void reloadAll() {
        for (Map.Entry<Class<? extends YamlFile>, YamlDataHolder> entry : loadedYamlFiles.entrySet()) {
            try {
                FileConfiguration newConfig = YamlConfiguration.loadConfiguration(entry.getValue().getFile());
                Method initMethod = entry.getKey().getMethod("init", FileConfiguration.class);
                initMethod.invoke(null, newConfig);
                entry.getValue().setConfig(newConfig);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String processPlaceholder(Player player, String text) {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(player, text);
        }
        return text;
    }

    private static class YamlDataHolder {
        private final File file;
        private FileConfiguration config;

        public YamlDataHolder(File file, FileConfiguration config) {
            this.file = file;
            this.config = config;
        }

        public File getFile() {
            return file;
        }

        public FileConfiguration getConfig() {
            return config;
        }

        public void setConfig(FileConfiguration config) {
            this.config = config;
        }
    }
}