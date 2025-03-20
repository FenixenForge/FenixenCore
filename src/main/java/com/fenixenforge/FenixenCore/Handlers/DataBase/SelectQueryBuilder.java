package com.fenixenforge.FenixenCore.Handlers.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectQueryBuilder {
    private final String tableName;
    private String columns = "*";
    private String condition;
    private Object conditionValue;

    public SelectQueryBuilder(String tableName) {
        this.tableName = tableName;
    }

    public SelectQueryBuilder columns(String... cols) {
        if (cols != null && cols.length > 0) {
            columns = String.join(", ", cols);
        }
        return this;
    }

    public SelectQueryBuilder where(String column, Object value) {
        this.condition = column + " = ?";
        this.conditionValue = value;
        return this;
    }

    public ResultSet executeQuery()
            throws SQLException {
        String sql = "SELECT " + columns + " FROM " + tableName;
        if (condition != null) {
            sql += " WHERE " + condition;
        }
        Connection connection = DatabaseHandler.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        if (condition != null) {
            ps.setObject(1, conditionValue);
        }
        return ps.executeQuery();
    }
}