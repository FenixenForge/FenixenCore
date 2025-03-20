package com.fenixenforge.FenixenCore.Handlers.Placeholders;

public class PlaceholderBuilder {
    private String identifier;
    private PlaceholderFunction function;

    // Método estático para iniciar el builder
    public static PlaceholderBuilder placeholder() {
        return new PlaceholderBuilder();
    }

    // Define el identificador (sin % o llaves, por ejemplo "balance")
    public PlaceholderBuilder identificator(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public PlaceholderBuilder funcion(PlaceholderFunction function) {
        this.function = function;
        return this;
    }

    public void register() {
        PlaceholderManager.registerPlaceholder(identifier, function);
    }
}