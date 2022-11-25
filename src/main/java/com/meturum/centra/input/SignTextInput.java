package com.meturum.centra.input;

import com.meturum.centra.sessions.Session;
import org.jetbrains.annotations.NotNull;

public interface SignTextInput {

    /**
     * @return The player who owns this text input.
     */
    @NotNull Session getOwner();

    /**
     * @return The current lines of the text input. (Editable)
     */
    @NotNull String[] getLines();

    /**
     * Sets the lines of the text input.
     *
     * @param lines The new lines.
     * @return The text input.
     */
    SignTextInput setLines(@NotNull String[] lines);

    /**
     * @return The result lines of the text input. (Read-only)
     */
    @NotNull String[] getResult();

    /**
     * @return Whether the text input is currently being edited.
     */
    boolean isEditing();

    /**
     * Sets the update lambda of the text input.
     *
     * @param updateLambda The new update lambda.
     * @return The text input.
     */
    @NotNull SignTextInput setUpdateLambda(@NotNull UpdateLambda updateLambda);

    /**
     * Opens the text input.
     *
     * @param force Whether to force the text input to open.
     */
    void open(boolean force);

    /**
     * Forcibly opens the text input.
     *
     * @apiNote This will close the player's current open inventory.
     */
    @NotNull SignTextInput open();

    interface UpdateLambda {
        void run(@NotNull String[] newLines, @NotNull String[] oldLines);
    }

}
