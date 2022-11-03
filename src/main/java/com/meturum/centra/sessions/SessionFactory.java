package com.meturum.centra.sessions;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import com.meturum.centra.system.System;

import javax.annotation.Nullable;
import java.util.UUID;

public interface SessionFactory extends System {

    /**
     * Searches for a session by the specified player's UUID.
     *
     * @param uuid the UUID of the player to search for.
     * @return the session that was found, or null if no session was found.
     */
    @Nullable Session search(@NotNull UUID uuid);

    /**
     * Searches for a session by the specified player.
     *
     * @param player the player to search for.
     * @return the session that was found, or null if no session was found.
     */
    @Nullable Session search(@NotNull Player player);

    /**
     * Checks if a session exists for the specified player's UUID.
     *
     * @param uuid the UUID of the player to check for.
     * @return true if a session exists, false otherwise.
     */
    boolean contains(@NotNull UUID uuid);

    /**
     * Checks if a session exists for the specified player.
     *
     * @param player the player to check for.
     * @return true if a session exists, false otherwise.
     */
    boolean contains(@NotNull Player player);

    /**
     * Checks if a session exists for the specified player.
     *
     * @param session the session to check for.
     * @return true if a session exists, false otherwise.
     */
    boolean contains(@NotNull Session session);

    /**
     * Closes the specified session.
     *
     * @param session the session to close.
     * @param async whether to close the session asynchronously.
     */
    boolean close(@NotNull Session session, boolean async);

    /**
     * Closes the specified session.
     *
     * @param session the session to close.
     */
    boolean close(@NotNull Session session);

}