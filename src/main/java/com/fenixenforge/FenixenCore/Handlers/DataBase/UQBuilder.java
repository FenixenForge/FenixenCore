package com.fenixenforge.FenixenCore.Handlers.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class UQBuilder {

    private final String tableName;
    private final Connection connection;
    private final Map<String, Object> setValues = new LinkedHashMap<>();
    private String condition;
    private Object conditionValue;

    public UQBuilder(String tableName, Connection connection) {
        this.tableName = tableName;
        this.connection = connection;
    }

    public UQBuilder set(String column, Object value) {
        setValues.put(column, value);
        return this;
    }

    public UQBuilder where(String column, Object value) {
        this.condition = column + " = ?";
        this.conditionValue = value;
        return this;
    }

    public void execute() throws SQLException {
        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
        boolean first = true;
        for (String col : setValues.keySet()) {
            if (!first) {
                sql.append(", ");
            }
            sql.append(col).append(" = ?");
            first = false;
        }
        if (condition!=null) {
            sql.append(" WHERE ").append(condition);
        }
        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int index = 1;
            for (Object value : setValues.values()) {
                ps.setObject(index++, value);
            }
            if (condition!=null) {
                ps.setObject(index, conditionValue);
            }
            ps.executeUpdate();
        }
    }

}
