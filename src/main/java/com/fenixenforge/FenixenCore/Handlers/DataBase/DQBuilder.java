package com.fenixenforge.FenixenCore.Handlers.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DQBuilder {

    private final String tableName;
    private final Connection connection;
    private String condition;
    private Object conditionValue;

    public DQBuilder(String tableName, Connection connection) {
        this.tableName = tableName;
        this.connection = connection;
    }

    public DQBuilder where(String column, Object value) {
        this.condition = column + " = ?";
        this.conditionValue = value;
        return this;
    }

    public void execute() throws SQLException {
        StringBuilder sql = new StringBuilder("DELETE FROM " + tableName);
        if (condition!=null) {
            sql.append(" WHERE ").append(condition);
        }
        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            if (condition!=null) {
                ps.setObject(1, conditionValue);
            }
            ps.executeUpdate();
        }
    }

}
