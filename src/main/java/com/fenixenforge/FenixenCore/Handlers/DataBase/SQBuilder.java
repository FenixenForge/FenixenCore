package com.fenixenforge.FenixenCore.Handlers.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQBuilder {

    private final String tableName;
    private final Connection connection;
    private String columns = "*";
    private String condition;
    private Object conditionValue;

    public SQBuilder(String tableName, Connection connection) {
        this.tableName = tableName;
        this.connection = connection;
    }

    /**
     * Permite especificar columnas. Si no se invoca, se seleccionan todas (*).
     */
    public SQBuilder columns(String... cols) {
        if (cols!=null && cols.length > 0) {
            columns = String.join(", ", cols);
        }
        return this;
    }

    public SQBuilder where(String column, Object value) {
        this.condition = column + " = ?";
        this.conditionValue = value;
        return this;
    }

    public ResultSet executeQuery() throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT " + columns + " FROM " + tableName);
        if (condition!=null) {
            sql.append(" WHERE ").append(condition);
        }
        PreparedStatement ps = connection.prepareStatement(sql.toString());
        if (condition!=null) {
            ps.setObject(1, conditionValue);
        }
        return ps.executeQuery();
    }

}
