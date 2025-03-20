package com.fenixenforge.FenixenCore.Handlers.DataBase;

public class ColumnDefinition {
    private String name;
    private String type; // Ej. TEXT, INTEGER
    private boolean primaryKey = false;
    private String defaultValue = null;

    public static ColumnDefinition column(String name) {
        ColumnDefinition col = new ColumnDefinition();
        col.name = name;
        return col;
    }

    public ColumnDefinition text() {
        this.type = "TEXT";
        return this;
    }

    public ColumnDefinition integer() {
        this.type = "INTEGER";
        return this;
    }

    public ColumnDefinition primaryKey() {
        this.primaryKey = true;
        return this;
    }

    public ColumnDefinition defaultValue(Object value) {
        this.defaultValue = value.toString();
        return this;
    }

    public String buildSQL() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" ").append(type);
        if (primaryKey) {
            sb.append(" PRIMARY KEY");
        }
        if (defaultValue != null) {
            sb.append(" DEFAULT ").append(defaultValue);
        }
        return sb.toString();
    }
}
