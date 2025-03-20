package com.fenixenforge.FenixenCore.Handlers.DataBase;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseBuilder {
    private String name;
    private DBType type;
    private String path;

    public static DatabaseBuilder database() {
        return new DatabaseBuilder();
    }

    public DatabaseBuilder name(String name) {
        this.name = name;
        return this;
    }

    public DatabaseBuilder type(DBType type) {
        this.type = type;
        return this;
    }

    public DatabaseBuilder path(String path, JavaPlugin plugin) {
        this.path = "plugins/" + plugin.getName() + "/" + path;
        return this;
    }

    public DatabaseHandler connect() {
        Connection connection = null;
        try {

            if (type == DBType.SQLITE) {
                connection = DriverManager.getConnection("jdbc:sqlite:" + path);
            }
            DatabaseHandler.setInstance(new DatabaseHandler(name, connection, type));
            return DatabaseHandler.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (DatabaseHandler) connection;
    }
}
