package com.meturum.centra;

import com.meturum.centra.system.SystemManager;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public interface Centra {

    /**
     * Gets the official system manager.
     *
     * @return the system manager.
     */
    @NotNull SystemManager getSystemManager();

}