package com.fenixenforge.FenixenCore.Handlers.DataBase;

public class CDefinition {

    private String name;
    private String type; // Ej: TEXT, INTEGER, etc.
    private boolean primaryKey = false;
    private boolean unique = false;
    private String defaultValue = null;

    public static CDefinition column(String name) {
        CDefinition col = new CDefinition();
        col.name = name;
        return col;
    }

    public CDefinition text() {
        this.type = "TEXT";
        return this;
    }

    public CDefinition integer() {
        this.type = "INTEGER";
        return this;
    }

    public CDefinition primaryKey() {
        this.primaryKey = true;
        return this;
    }

    public CDefinition unique() {
        this.unique = true;
        return this;
    }

    public CDefinition defaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public String buildSQL() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" ").append(type);
        if (primaryKey) {
            // Para SQLite se puede usar AUTOINCREMENT si es INTEGER.
            sb.append(" PRIMARY KEY");
        }
        if (unique) {
            sb.append(" UNIQUE");
        }
        if (defaultValue!=null) {
            sb.append(" DEFAULT ").append(defaultValue);
        }
        return sb.toString();
    }

}
