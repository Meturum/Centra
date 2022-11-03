package com.meturum.centra.sessions.events;

import com.meturum.centra.sessions.GameProfile;
import com.meturum.centra.sessions.Session;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public final class ProfileSwitchEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Session session;

    private final GameProfile oldProfile;
    private final GameProfile newProfile;

    private boolean canceled = false;

    public ProfileSwitchEvent(Session session, GameProfile oldProfile, GameProfile newProfile) {
        this.session = session;
        this.oldProfile = oldProfile;
        this.newProfile = newProfile;
    }

    /**
     * @return the session that switched profiles.
     */
    public Session getSession() {
        return session;
    }

    /**
     * @return the old profile.
     */
    public GameProfile getOldProfile() {
        return oldProfile;
    }

    /**
     * @return the new profile.
     */
    public GameProfile getNewProfile() {
        return newProfile;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * @return if the event is cancelled.
     */
    @Override
    public boolean isCancelled() {
        return canceled;
    }

    /**
     * Define if the event is cancelled.
     *
     * @param cancel true if you wish to cancel this event, false otherwise.
     */
    @Override
    public void setCancelled(boolean cancel) {
        canceled = cancel;
    }
}
