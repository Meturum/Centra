package com.meturum.centra.sessions;

import com.meturum.centra.conversions.IDynamicTag;
import com.meturum.centra.input.SignTextInput;
import com.meturum.centra.sessions.ranks.Rank;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;

public interface Session extends IDynamicTag {

    /**
     * Gets the player associated with this session.
     *
     * @return the bukkit player object.
     */
    @NotNull Player getPlayer();

    /**
     * Gets the name associated with the owner of this session.
     *
     * @return the name.
     */
    @NotNull String getName();

    /**
     * Gets the settings associated with this session.
     *
     * @return the settings of the session.
     */
    @NotNull Document getSettings();

    /**
     * Gets the rank associated with this session.
     *
     * @return the rank of the session.
     */
    @NotNull Rank getRank();

    /**
     * Sets the rank associated with this session.
     *
     * @param rank the rank to set.
     */
    void setRank(@NotNull Rank rank);

    /**
     * Gets the profiles associated with this session.
     *
     * @return the profiles of the session.
     */
    @NotNull GameProfile[] getProfiles();

    /**
     * Gets the current profile associated with this session.
     *
     * @return the current profile of the session.
     */
    @NotNull GameProfile getCurrentProfile();

    /**
     * Sets the current profile associated with this session.
     *
     * @param index the index of the profile to set.
     * @throws IllegalArgumentException if the profile at the specified index does not exist.
     */
    void setCurrentProfile(int index) throws IllegalArgumentException;

    /**
     * Gets the profile associated with this session at the specified index.
     *
     * @param index the index of the profile to get.
     * @return the profile at the specified index.
     */
    @Nullable GameProfile getProfile(int index);

    /**
     * Gets the profile associated with this session at the specified name.
     *
     * @param uuid the uuid of the profile to get.
     * @return the profile at the specified name.
     */
    @Nullable GameProfile getProfile(@NotNull UUID uuid);

    /**
     * Gets the profile associated with this session at the specified name.
     *
     * @param nickname the name of the profile to get.
     * @return the profile at the specified name.
     */
    @Nullable GameProfile getProfile(@NotNull String nickname);

    /**
     * Adds the specified profile to this session.
     *
     * @param profile the profile to add.
     * @return true if the profile was added, false otherwise.
     */
    boolean addProfile(@NotNull GameProfile profile);

    /**
     * Removes the specified profile from this session.
     *
     * @param index the index of the profile to remove.
     * @return true if the profile was removed, false otherwise.
     */
    boolean removeProfile(int index);

    /**
     * Removes the specified profile from this session.
     *
     * @param profile the uuid of the profile to remove.
     * @return true if the profile was removed, false otherwise.
     */
    boolean removeProfile(@NotNull GameProfile profile);

    /**
     * Gets the currently active sign text input.
     *
     * @return the active sign text input.
     */
    @Nullable SignTextInput getTextInput();

    /**
     * Creates a new sign text input for this session.
     *
     * @return the new sign text input.
     */
    @Nullable SignTextInput createTextInput();

}
