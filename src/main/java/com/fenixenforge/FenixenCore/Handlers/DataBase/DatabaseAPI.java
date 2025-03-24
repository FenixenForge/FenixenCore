package com.fenixenforge.FenixenCore.Handlers.DataBase;

public class DatabaseAPI {

    private static DHandler handler;

    /**
     * Inicializa la API con el DatabaseHandler.
     * Se debe llamar en el onEnable() del plugin.
     */
    public static void initialize(DHandler dbHandler) {
        handler = dbHandler;
    }

    /**
     * Devuelve un objeto para operar sobre la tabla especificada.
     */
    public static DOperations table(String tableName) {
        if (handler==null) {
            throw new IllegalStateException("DatabaseHandler no está inicializado. Llama a DatabaseAPI.initialize()");
        }
        return new DOperations(handler, tableName);
    }

    /**
     * Para definir las tablas, devuelve un TableBuilder fluido.
     */
    public static TBuilder tables() {
        return TBuilder.table();
    }

}
