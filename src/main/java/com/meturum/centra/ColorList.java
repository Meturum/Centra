package com.meturum.centra;

import com.meturum.centra.conversions.annotations.DocumentableMethod;
import net.md_5.bungee.api.ChatColor;

import java.awt.*;

public final class ColorList {

    public static final ColorList BLACK = new ColorList(ChatColor.BLACK);
    public static final ColorList DARK_BLUE = new ColorList(ChatColor.DARK_BLUE);
    public static final ColorList DARK_GREEN = new ColorList(ChatColor.DARK_GREEN);
    public static final ColorList DARK_AQUA = new ColorList(ChatColor.DARK_AQUA);
    public static final ColorList DARK_RED = new ColorList(ChatColor.DARK_RED);
    public static final ColorList DARK_PURPLE = new ColorList(ChatColor.DARK_PURPLE);
    public static final ColorList GOLD = new ColorList(ChatColor.GOLD);
    public static final ColorList GRAY = new ColorList(ChatColor.GRAY);
    public static final ColorList DARK_GRAY = new ColorList(ChatColor.DARK_GRAY);
    public static final ColorList BLUE = new ColorList(ChatColor.BLUE);
    public static final ColorList GREEN = new ColorList(ChatColor.GREEN);
    public static final ColorList AQUA = new ColorList(ChatColor.AQUA);
    public static final ColorList RED = new ColorList(ChatColor.RED);
    public static final ColorList LIGHT_PURPLE = new ColorList(ChatColor.LIGHT_PURPLE);
    public static final ColorList YELLOW = new ColorList(ChatColor.YELLOW);
    public static final ColorList WHITE = new ColorList(ChatColor.WHITE);
    public static final ColorList PINK = ColorList.of(Color.PINK);

    private final Color color;
    public final ChatColor v;

    ColorList(int r, int g, int b) {
        color = new Color(r, g, b);
        v = ChatColor.of(color);
    }

    ColorList(String hex) {
        color = Color.decode(hex);
        v = ChatColor.of(color);
    }

    ColorList(Color color) {
        this.color = color;
        v = ChatColor.of(color);
    }

    ColorList(ChatColor color) {
        this.color = color.getColor();
        v = color;
    }

    /**
     * Generates a new color list from a color.
     *
     * @param color The color.
     * @return The color list.
     */
    public static ColorList of(ChatColor color) {
        return new ColorList(color);
    }

    /**
     * Generates a new color list from a color.
     *
     * @param color The color.
     * @return The color list.
     */
    public static ColorList of(Color color) {
        return new ColorList(color);
    }

    /**
     * Generates a new color list from a rgb color.
     *
     * @param r The red value.
     * @param g The green value.
     * @param b The blue value.
     * @return The color list.
     */
    public static ColorList of(int r, int g, int b) {
        return new ColorList(r, g, b);
    }

    /**
     * Generates a new color list from a hex color.
     *
     * @param hex The hex color.
     * @return The color list.
     */
    public static ColorList of(String hex) {
        return new ColorList(Color.decode(hex));
    }

    @Override
    public String toString() {
        return v.toString();
    }

    @DocumentableMethod
    private String _toString() {
        return "#" + Integer.toHexString(color.getRed()) + Integer.toHexString(color.getGreen()) + Integer.toHexString(color.getBlue());
    }

    @DocumentableMethod
    private static ColorList _fromString(String string) {
        return ColorList.of(string);
    }

}