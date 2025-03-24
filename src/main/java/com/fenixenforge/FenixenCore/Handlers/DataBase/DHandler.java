package com.fenixenforge.FenixenCore.Handlers.DataBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DHandler {

    private final String name;
    private final Connection connection;
    private final DBTypes type;

    public DHandler(String name, Connection connection, DBTypes type) {
        this.name = name;
        this.connection = connection;
        this.type = type;
    }

    public Connection getConnection() {
        return connection;
    }

    public DBTypes getType() {
        return type;
    }

    public void executeUpdate(String sql) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

}
