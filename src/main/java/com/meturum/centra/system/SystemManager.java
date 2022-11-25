package com.meturum.centra.system;

import javax.annotation.Nullable;

public interface SystemManager {

    /**
     * Searches for a system in the system manager.
     *
     * @param clazz The class of the system
     * @param <T> The type of the system
     * @return the system if found, null otherwise.
     */
    @Nullable <T extends System> T search(Class<T> clazz);

    /**
     * Checks if a system is registered in the system manager.
     *
     * @param clazz The class of the system
     * @return true if the system is registered, false otherwise.
     */
    boolean contains(Class<? extends System> clazz);

    /**
     * Starts all systems in the system manager.
     *
     * @throws Exception if an error occurs while starting a system.
     */
    void start() throws Exception;

    /**
     * Stops all systems in the system manager.
     *
     * @throws Exception if an error occurs while stopping a system.
     */
    void stop() throws Exception;

}
