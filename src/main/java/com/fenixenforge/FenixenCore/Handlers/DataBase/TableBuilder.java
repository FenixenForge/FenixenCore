package com.fenixenforge.FenixenCore.Handlers.DataBase;

import java.util.ArrayList;
import java.util.List;

public class TableBuilder {
    private String tableName;
    private final List<ColumnDefinition> columns = new ArrayList<>();

    public static TableBuilder table() {
        return new TableBuilder();
    }

    public TableBuilder tableName(String name) {
        this.tableName = name;
        return this;
    }

    public TableBuilder addColumn(ColumnDefinition col) {
        columns.add(col);
        return this;
    }

    public String buildCreateTableSQL() {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        sql.append(tableName).append(" (");
        List<String> defs = new ArrayList<>();
        for (ColumnDefinition col : columns) {
            defs.add(col.buildSQL());
        }
        sql.append(String.join(", ", defs));
        sql.append(");");
        return sql.toString();
    }
}