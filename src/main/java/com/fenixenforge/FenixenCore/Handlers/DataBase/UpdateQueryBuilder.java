package com.fenixenforge.FenixenCore.Handlers.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class UpdateQueryBuilder {
    private final String tableName;
    private final Map<String, Object> setValues = new LinkedHashMap<>();
    private String condition;
    private Object conditionValue;

    public UpdateQueryBuilder(String tableName) {
        this.tableName = tableName;
    }

    public UpdateQueryBuilder set(String column, Object value) {
        setValues.put(column, value);
        return this;
    }

    public UpdateQueryBuilder where(String column, Object value) {
        this.condition = column + " = ?";
        this.conditionValue = value;
        return this;
    }

    public void execute()
            throws SQLException {
        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
        boolean first = true;
        for (String col : setValues.keySet()) {
            if (!first) {
                sql.append(", ");
            }
            sql.append(col).append(" = ?");
            first = false;
        }
        if (condition != null) {
            sql.append(" WHERE ").append(condition);
        }
        Connection connection = DatabaseHandler.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int index = 1;
            for (Object value : setValues.values()) {
                ps.setObject(index++, value);
            }
            if (condition != null) {
                ps.setObject(index, conditionValue);
            }
            ps.executeUpdate();
        }
    }
}