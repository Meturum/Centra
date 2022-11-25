package com.meturum.centra.commands.exceptions;

import com.meturum.centra.ColorList;
import com.meturum.centra.EmojiList;
import org.jetbrains.annotations.NotNull;

public final class CommandArgumentException extends Exception {

    public CommandArgumentException(@NotNull String message) {
        this(message, Level.ERROR);
    }

    public CommandArgumentException(@NotNull String message, @NotNull Level level) {
        super(level.icon+" "+level.color+message);
    }

    public enum Level {
        ERROR(EmojiList.ERROR_ICON, ColorList.RED),
        WARNING(EmojiList.WARNING_ICON, ColorList.YELLOW);

        public final EmojiList icon;
        public final ColorList color;

        Level(EmojiList icon, ColorList color) {
            this.icon = icon;
            this.color = color;
        }

    }

}