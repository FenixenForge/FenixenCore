package com.fenixenforge.Core.Handlers.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class DBuilder {

    private String name;
    private DBTypes type;
    private String path;
    private String host;
    private String port;
    private String user;
    private String pass;
    private String databaseName;

    public static DBuilder database() {
        return new DBuilder();
    }

    public DBuilder name(String name) {
        this.name = name;
        return this;
    }

    public DBuilder type(DBTypes type) {
        this.type = type;
        return this;
    }

    public DBuilder path(String path) {
        this.path = path;
        return this;
    }

    public DBuilder host(String host) {
        this.host = host;
        return this;
    }

    public DBuilder port(String port) {
        this.port = port;
        return this;
    }

    public DBuilder user(String user) {
        this.user = user;
        return this;
    }

    public DBuilder pass(String pass) {
        this.pass = pass;
        return this;
    }

    public DBuilder databaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public DHandler connect() {
        try {
            Connection connection;
            if (Objects.requireNonNull(type)==DBTypes.SQLITE) {
                connection = DriverManager.getConnection("jdbc:sqlite:" + path);
            } else if (type==DBTypes.MYSQL) {
                String mysqlUrl = "jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?sslMode=REQUIRED";
                connection = DriverManager.getConnection(mysqlUrl, user, pass);
            } else {
                throw new IllegalArgumentException("Tipo de base de datos desconocido");
            }
            return new DHandler(name, connection, type);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
