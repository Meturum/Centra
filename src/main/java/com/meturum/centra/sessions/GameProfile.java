package com.meturum.centra.sessions;

import com.meturum.centra.conversions.IDynamicTag;
import com.meturum.centra.conversions.annotations.DocumentableMethod;
import com.meturum.centra.system.SystemManager;
import org.jetbrains.annotations.NotNull;

public interface GameProfile extends IDynamicTag {

    /**
     * Gets the nickname associated with this profile.
     * @return the nickname of the profile.
     */
    @NotNull String getNickname();

}
