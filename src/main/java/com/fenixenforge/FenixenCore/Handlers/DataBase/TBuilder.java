package com.fenixenforge.FenixenCore.Handlers.DataBase;

import java.util.ArrayList;
import java.util.List;

public class TBuilder {

    private String tableName;
    private final List<CDefinition> columns = new ArrayList<>();

    public static TBuilder table() {
        return new TBuilder();
    }

    public TBuilder name(String name) {
        this.tableName = name;
        return this;
    }

    public TBuilder addColumn(CDefinition col) {
        columns.add(col);
        return this;
    }

    public String buildCreateTableSQL() {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        sql.append(tableName).append(" (");
        List<String> defs = new ArrayList<>();
        for (CDefinition col : columns) {
            defs.add(col.buildSQL());
        }
        sql.append(String.join(", ", defs));
        sql.append(");");
        return sql.toString();
    }

}
