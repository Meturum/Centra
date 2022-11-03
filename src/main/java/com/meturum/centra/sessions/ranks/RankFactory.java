package com.meturum.centra.sessions.ranks;

import org.jetbrains.annotations.NotNull;
import com.meturum.centra.system.System;

import javax.annotation.Nullable;
import java.util.UUID;

public interface RankFactory extends System {

    /**
     * Searches for a rank by the specified UUID. If the variable {@code deep} is true, it will search for the rank in the database.
     *
     * @param name the name of the rank.
     * @param deep whether to search for the rank in the database.
     * @return the rank.
     */
    @Nullable Rank search(@NotNull UUID name, boolean deep);

    /**
     * Searches for a rank by the specified UUID. If the variable {@code deep} is true, it will search for the rank in the database.
     *
     * @param name the name of the rank.
     * @return the rank.
     */
    @Nullable Rank search(@NotNull UUID name);

    /**
     * Searches for a rank by the specified name.
     *
     * @param name the name of the rank.
     * @param deep whether to search for the rank in the database.
     * @return the rank.
     */
    @Nullable Rank search(@NotNull String name, boolean deep);

    /**
     * Searches for a rank by the specified name.
     *
     * @param name the name of the rank.
     * @return the rank.
     */
    @Nullable Rank search(@NotNull String name);

    /**
     * Searches for a rank by the specified name. If the variable {@code deep} is true, it will search for the rank in the database.
     *
     * @param uuid the uuid of the rank.
     * @param deep whether to search for the rank in the database.
     * @return true if the rank exists, false otherwise.
     */
    boolean contains(@NotNull UUID uuid, boolean deep);

    /**
     * Searches for a rank by the specified name.
     *
     * @param uuid the uuid of the rank.
     * @return true if the rank exists, false otherwise.
     */
    boolean contains(@NotNull UUID uuid);

    /**
     * Searches for a rank by the specified name. If the variable {@code deep} is true, it will search for the rank in the database.
     *
     * @param name the name of the rank.
     * @param deep whether to search for the rank in the database.
     * @return true if the rank exists, false otherwise.
     */
    boolean contains(@NotNull String name, boolean deep);

    /**
     * Searches for a rank by the specified name.
     *
     * @param name the name of the rank.
     * @return true if the rank exists, false otherwise.
     */
    boolean contains(@NotNull String name);

}