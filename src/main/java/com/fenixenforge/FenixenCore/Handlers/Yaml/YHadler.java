package com.fenixenforge.Core.Handlers.Yaml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

public class YHadler {
    private static JavaPlugin plugin;
    private static final Map<Class<? extends YFile>, YDataHolder> loadedYFiles = new HashMap<>();

    public static void RegisterAll(JavaPlugin pluginInstance, String basePackage) {
        plugin = pluginInstance;

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        String path = plugin.getClass().getPackage().getName() + ".";
        Reflections reflections = new Reflections(path + basePackage);
        Set<Class<? extends YFile>> classes = reflections.getSubTypesOf(YFile.class);

        for (Class<? extends YFile> clazz : classes) {
            try {

                YData annotation = clazz.getAnnotation(YData.class);
                if (annotation==null) {
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
                    String resourcePath = (!folder.isEmpty() ? folder.replace(File.separatorChar, '/') + "/":"") + fileName;
                    plugin.saveResource(resourcePath, false);
                }

                FileConfiguration config = YamlConfiguration.loadConfiguration(file);

                Method initMethod = clazz.getMethod("init", FileConfiguration.class);
                initMethod.invoke(null, config);

                loadedYFiles.put(clazz, new YDataHolder(file, config));
            } catch (NoSuchMethodException ignored) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void SaveAll() {
        for (YDataHolder data : loadedYFiles.values()) {
            try {
                ((YamlConfiguration) data.getConfig()).save(data.getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void ReloadAll() {
        for (Map.Entry<Class<? extends YFile>, YDataHolder> entry : loadedYFiles.entrySet()) {
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

    private static class YDataHolder {
        private final File file;
        private FileConfiguration config;

        public YDataHolder(File file, FileConfiguration config) {
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
