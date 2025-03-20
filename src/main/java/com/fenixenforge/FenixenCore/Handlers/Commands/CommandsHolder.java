package com.fenixenforge.FenixenCore.Handlers.Commands;

import java.util.HashMap;
import java.util.Map;

public class CommandsHolder {
    private static final Map<String, MainCommandBuilder> mainCommands = new HashMap<>();

    public static void registerMainCommand(MainCommandBuilder mainCommand) {
        if (mainCommand == null || mainCommand.getName() == null) {
            throw new IllegalArgumentException("El comando principal debe tener un nombre válido.");
        }
        mainCommands.put(mainCommand.getName(), mainCommand);
    }

    public static MainCommandBuilder getMainCommand(String name) {
        return mainCommands.get(name);
    }
}