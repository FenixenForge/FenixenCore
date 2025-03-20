package com.fenixenforge.FenixenCore.Handlers.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class InsertQueryBuilder {
    private final String tableName;
    private final Map<String, Object> values = new LinkedHashMap<>();

    public InsertQueryBuilder(String tableName) {
        this.tableName = tableName;
    }

    public InsertQueryBuilder value(String column, Object value) {
        values.put(column, value);
        return this;
    }

    public void execute()
            throws SQLException {
        StringBuilder columns = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();
        boolean first = true;
        for (String col : values.keySet()) {
            if (!first) {
                columns.append(", ");
                placeholders.append(", ");
            }
            columns.append(col);
            placeholders.append("?");
            first = false;
        }
        String sql = "INSERT OR IGNORE INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";
        Connection connection = DatabaseHandler.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int index = 1;
            for (Object val : values.values()) {
                ps.setObject(index++, val);
            }
            ps.executeUpdate();
        }
    }
}