package com.meturum.centra.sessions.ranks;

import com.meturum.centra.ColorList;
import com.meturum.centra.conversions.IDynamicTag;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;

public interface Rank extends IDynamicTag {

    /**
     * Gets the name of this rank.
     *
     * @return the name of this rank.
     */
    @NotNull String getName();

    /**
     * Sets the name of this rank.
     *
     * @param name the name to set.
     */
    void setName(@NotNull String name);

    /**
     * Gets the color of this rank.
     *
     * @return the color of this rank.
     */
    @NotNull ColorList getColor();

    /**
     * Sets the color of this rank.
     *
     * @param color the color to set.
     */
    void setColor(@NotNull ColorList color);

    /**
     * Gets the permissions of this rank.
     *
     * @return the permissions of this rank.
     */
    List<String> getPermissions();

    /**
     * Adds a permission to this rank.
     *
     * @param permission the permission to add.
     */
    void addPermission(@NotNull String permission);

    /**
     * Removes a permission from this rank.
     *
     * @param permission the permission to remove.
     */
    void removePermission(@NotNull String permission);

    /**
     * Sets the permissions of this rank.
     *
     * @param permissions the permissions to set.
     */
    void setPermissions(@NotNull List<String> permissions);

    /**
     * Gets the icon of this rank.
     *
     * @return the icon of this rank.
     */
    @NotNull String getIcon();

    /**
     * Checks if this rank has an icon.
     *
     * @return true if this rank has an icon.
     */
    boolean hasIcon();

    /**
     * Sets the icon of this rank.
     *
     * @param icon the icon to set.
     */
    void setIcon(@NotNull String icon);

    /**
     * Brightens the specified color.
     *
     * @param color the color to brighten.
     */
    static ColorList brighten(Color color) {
        int red = (int) Math.round(Math.min(255, color.getRed() + 255 * 0.55));
        int green = (int) Math.round(Math.min(255, color.getGreen() + 255 * 0.55));
        int blue = (int) Math.round(Math.min(255, color.getBlue() + 255 * 0.55));

        int alpha = color.getAlpha();

        return ColorList.of(new Color(red, green, blue, alpha));

    }

}
