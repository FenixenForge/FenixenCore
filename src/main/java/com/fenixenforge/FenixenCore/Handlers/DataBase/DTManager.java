package com.fenixenforge.FenixenCore.Handlers.DataBase;

import java.sql.SQLException;
import java.util.Set;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

public class DTManager {

    public static void registerAllTables(JavaPlugin plugin, String packagePath, DHandler dbHandler) {
        Reflections reflections = new Reflections(packagePath);
        Set<Class<? extends TDefinition>> classes = reflections.getSubTypesOf(TDefinition.class);
        for (Class<? extends TDefinition> clazz : classes) {
            try {
                TDefinition table = clazz.getDeclaredConstructor().newInstance();
                String sql = table.getCreateTableSQL();
                dbHandler.executeUpdate(sql);
                plugin.getLogger().info("Tabla creada/actualizada: " + table.getTableName());
            } catch (SQLException e) {
                plugin.getLogger().severe("Error al crear la tabla: " + clazz.getSimpleName());
                e.printStackTrace();
            } catch (Exception e) {
                plugin.getLogger().severe("Error al instanciar la definición de la tabla: " + clazz.getSimpleName());
                e.printStackTrace();
            }
        }
    }

}
