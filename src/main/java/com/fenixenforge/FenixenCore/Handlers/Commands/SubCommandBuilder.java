package com.fenixenforge.FenixenCore.Handlers.Commands;

public class SubCommandBuilder extends AbstractCommandBuilder<SubCommandBuilder> {
    // Cadena que identifica al comando principal o cadena de padres (ej. "main", "main sub1", etc.)
    private String mainCommandChain;

    public SubCommandBuilder comandoprincipalname(String chain) {
        this.mainCommandChain = chain;
        return this;
    }

    public String getMainCommandChain() {
        return mainCommandChain;
    }
}
