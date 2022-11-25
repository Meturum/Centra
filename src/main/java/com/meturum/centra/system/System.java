package com.meturum.centra.system;

import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public interface System extends Listener {

    @NotNull SystemState getState();

    @NotNull SystemManager getSystemManager();

    void init();

    void stop();

    enum SystemState {
        ACTIVE, DISABLED
    }

}