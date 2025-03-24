package com.fenixenforge.FenixenCore.Handlers.DataBase;

public class DOperations {

    private final DHandler handler;
    private final String tableName;

    public DOperations(DHandler handler, String tableName) {
        this.handler = handler;
        this.tableName = tableName;
    }

    public IQBuilder insert() {
        return new IQBuilder(tableName, handler.getConnection());
    }

    public UQBuilder update() {
        return new UQBuilder(tableName, handler.getConnection());
    }

    public DQBuilder delete() {
        return new DQBuilder(tableName, handler.getConnection());
    }

    public SQBuilder select() {
        return new SQBuilder(tableName, handler.getConnection());
    }

}
