package com.fenixenforge.FenixenCore.Handlers.Placeholders;

public class PBuilder {
    private String identifier;
    private PFunction function;

    public PBuilder() {
    }

    public static PBuilder placeholder() {
        return new PBuilder();
    }

    public PBuilder identificator(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public PBuilder funcion(PFunction function) {
        this.function = function;
        return this;
    }

    public void register() {
        Placeholder.registerPlaceholder(this.identifier, this.function);
    }
}
