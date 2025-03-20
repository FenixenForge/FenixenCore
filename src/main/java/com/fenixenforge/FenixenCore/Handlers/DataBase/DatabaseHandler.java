package com.fenixenforge.FenixenCore.Handlers.DataBase;

import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Set;

public class DatabaseHandler {
    private static DatabaseHandler instance;
    private final String name;
    private final Connection connection;
    private final DBType type;

    public DatabaseHandler(String name, Connection connection, DBType type) {
        this.name = name;
        this.connection = connection;
        this.type = type;
    }

    public static void setInstance(DatabaseHandler handler) {
        instance = handler;
    }

    public static DatabaseHandler getInstance() {
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public DBType getType() {
        return type;
    }

    public void createAll(JavaPlugin plugin, String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<? extends TableDefinition>> classes = reflections.getSubTypesOf(TableDefinition.class);
        for (Class<? extends TableDefinition> clazz : classes) {
            try {
                TableDefinition tableDef = clazz.getDeclaredConstructor().newInstance();
                String sql = tableDef.getCreateTableSQL();
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
                plugin.getLogger().info("Tabla creada: " + tableDef.getTableName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}