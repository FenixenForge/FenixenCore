package com.fenixenforge.Core.Handlers.Commands;

public class SCBuilder extends CBuilder<SCBuilder> {

    private String mCChain;

    public SCBuilder cPrincipalName(String chain) {
        this.mCChain = chain;
        return this;
    }

    public String getmCChain() {
        return mCChain;
    }
}
