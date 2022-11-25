package com.meturum.centra.commands.arguments;

import com.meturum.centra.commands.exceptions.CommandArgumentException;
import com.meturum.centra.sessions.Session;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public interface CommandContext {

    /**
     * The sender that executed the command.
     *
     * @return The sender. (Console or Player)
     */
    @NotNull CommandSender getSender();

    /**
     * @return The session of the sender.
     *
     * @apiNote In the case of console execution, session will return null.
     */
    @Nullable Session getSession();

    /**
     * The arguments that were passed to the command.
     *
     * @return The arguments.
     */
    @NotNull String[] getTrueArguments();

    /**
     * Gets an argument from the context map.
     *
     * @param name The name of the argument.
     * @return The argument.
     */
    CommandArgument<?> get(@NotNull String name);

    /**
     * Gets an argument value from the context map. (Allows for no casting on your part)
     *
     * @param name The name of the argument.
     * @return The argument value.
     * @param <T> The type of the argument.
     */
    <T> T getArgument(@NotNull String name);

    /**
     * Gets an argument value from the context map. (Allows for no casting on your part)
     *
     * @param name The name of the argument.
     * @return The argument value.
     * @param <T> The type of the argument.
     * @throws CommandArgumentException If the argument is not present.
     */
    @NotNull <T> T getArgumentNonNull(@NotNull String name) throws CommandArgumentException;

    /**
     * Gets an argument value from the context map. (Allows for no casting on your part)
     * This method allows for a default value to be returned if the argument is not present.
     *
     * @param name The name of the argument.
     * @param defaultValue The default value to return if the argument is not present.
     * @return The argument value.
     * @param <T> The type of the argument.
     */
    <T> T getArgumentOrDefault(@NotNull String name, @NotNull T defaultValue);

    /**
     * @param name The name of the argument.
     * @return returns true if the argument exists in the context map, false otherwise.
     */
    boolean containsArgument(@NotNull String name);

}
