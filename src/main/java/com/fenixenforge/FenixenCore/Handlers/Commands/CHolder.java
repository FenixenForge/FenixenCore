package com.fenixenforge.Core.Handlers.Commands;

import java.util.HashMap;
import java.util.Map;

public class CHolder {

    private static final Map<String, MCBuilder> mCommands = new HashMap<>();

    public static void rMCommand(MCBuilder mCommand) {
        if (mCommand == null || mCommand.getName() == null) {
            throw new IllegalArgumentException("El commando Principal debe tener un nombre valido");
        }
        mCommands.put(mCommand.getName(), mCommand);
    }

    public static MCBuilder getMCommand(String name) {
        return mCommands.get(name);
    }
}
