package com.fenixenforge.FenixenCore.Handlers.Commands;

public class SubCommandBuilder extends AbstractCommandBuilder<SubCommandBuilder> {

    private String mainCommandChain;

    public SubCommandBuilder comandoprincipalname(String chain) {
        this.mainCommandChain = chain;
        return this;
    }

    public String getMainCommandChain() {
        return mainCommandChain;
    }
}
