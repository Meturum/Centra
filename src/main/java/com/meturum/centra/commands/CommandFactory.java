package com.meturum.centra.commands;

import com.meturum.centra.system.System;
import org.jetbrains.annotations.NotNull;

public interface CommandFactory extends System {

    /**
     * Registers a command to the server. If the command already exists, it will be overwritten.
     *
     * @param command the command to register
     * @return true if the command was successfully registered, false otherwise.
     * @apiNote Commands are registered under the handle centre.
     */
    boolean registerCommand(@NotNull CommandBuilder command) throws RuntimeException;

}
