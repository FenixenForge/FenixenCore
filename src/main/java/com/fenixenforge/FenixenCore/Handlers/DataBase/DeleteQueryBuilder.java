package com.fenixenforge.FenixenCore.Handlers.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteQueryBuilder {
    private final String tableName;
    private String condition;
    private Object conditionValue;

    public DeleteQueryBuilder(String tableName) {
        this.tableName = tableName;
    }

    public DeleteQueryBuilder where(String column, Object value) {
        this.condition = column + " = ?";
        this.conditionValue = value;
        return this;
    }

    public void execute()
            throws SQLException {
        String sql = "DELETE FROM " + tableName;
        if (condition != null) {
            sql += " WHERE " + condition;
        }
        Connection connection = DatabaseHandler.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            if (condition != null) {
                ps.setObject(1, conditionValue);
            }
            ps.executeUpdate();
        }
    }
}