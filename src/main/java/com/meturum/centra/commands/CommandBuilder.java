package com.meturum.centra.commands;

import com.google.common.base.Preconditions;
import com.meturum.centra.commands.arguments.CommandArgument;
import com.meturum.centra.commands.arguments.CommandContext;
import com.meturum.centra.commands.exceptions.CommandArgumentException;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
public class CommandBuilder {

    private final String name;
    private @Nullable String description;
    private @NotNull String[] aliases;
    private @Nullable String permission;

    private @Nullable String usageMessage;

    private List<CommandArgument<?>> argumentList = new ArrayList<>();

    private CommandBuilder parent;
    private final List<CommandBuilder> nodeList = new ArrayList<>();

    private @Nullable ExecuteLambda executeLambda;

    private boolean allowConsoleExecution = false;

    public CommandBuilder(@NotNull final String name, @Nullable final String description, @Nullable final String permission, @NotNull final String... aliases) {
        this.name = name.toLowerCase();
        this.description = description;
        this.permission = permission;
        this.aliases = aliases;
    }

    public CommandBuilder(@NotNull final String name, @Nullable final String description, @Nullable final String permission) {
        this(name, description, permission, new String[0]);
    }

    public CommandBuilder(@NotNull final String name, @Nullable final String description) {
        this(name, description, null, new String[0]);
    }

    public CommandBuilder(@NotNull final String name) {
        this(name, null, null, new String[0]);
    }

    /**
     * @return The name of the command.
     */
    public final @NotNull String getName() {
        return name;
    }

    /**
     * @return The description of the command.
     */
    public final @NotNull String getDescription() {
        if(description == null)return "";

        return description;
    }

    /**
     * Sets the description of the command.
     *
     * @param description the description to set.
     */
    public final @NotNull CommandBuilder setDescription(@Nullable final String description) {
        this.description = description;

        return this;
    }

    /**
     * @return the permission required to execute this command.
     */
    public final @Nullable String getPermission() {
        return permission;
    }

    /**
     * Sets the permission required to execute this command.
     *
     * @param permission the permission.
     */
    public final @NotNull CommandBuilder setPermission(@NotNull String permission) {
        this.permission = permission;

        return this;
    }

    /**
     * @return The aliases for the command. (e.g. bal, gmc, tp)
     */
    public final @NotNull String[] getAliases() {
        return aliases;
    }

    /**
     * Sets the aliases for the command. (e.g. bal, gmc, tp)
     *
     * @param aliases the aliases.
     */
    public final @NotNull CommandBuilder setAliases(@NotNull final String... aliases) {
        Preconditions.checkNotNull(aliases, "Aliases cannot be null!");

        this.aliases = aliases;

        return this;
    }

    /**
     * Generates a usage message to be sent to the player. If you'd like a custom usage message please see
     * {@link CommandBuilder#setUsageMessage(String)}
     *
     * @return the usage message.
     */
    public final @NotNull String getUsageMessage() {
        if(usageMessage != null) return usageMessage; // If the usage message is already set, return it.

        StringBuilder builder = new StringBuilder().append("/");

        // include the hierarchy in the command usage.
        List<CommandBuilder> hierarchyList = hierarchy();
        for (CommandBuilder commandBuilder : hierarchyList) {
            builder.append(commandBuilder.getName()).append(" ");
        }

        builder.append(name);

        for(CommandArgument<?> argument : argumentList) {
            builder.append(argument.isRequired()
                    ? " <" + argument.getName() + ":" + argument.getType().getSimpleName() + ">"
                    : " [" + argument.getName() + ":" + argument.getType().getSimpleName() + "]"
            );
        }

        return builder.toString();
    }

    /**
     * Sets the custom usage message to be sent to the player.
     *
     * @param usageMessage the usage message.
     */
    public final void setUsageMessage(@Nullable final String usageMessage) {
        this.usageMessage = usageMessage;
    }

    /**
     * Gets an argument from the command's argument list.
     *
     * @param name the name of the argument
     * @return the argument if found, otherwise null.
     */
    public final @Nullable CommandArgument<?> getArgument(@NotNull final String name) {
        for(CommandArgument<?> argument : argumentList) {
            if(argument.getName().equalsIgnoreCase(name))
                return argument;
        }

        return null;
    }

    /**
     * Gets all the arguments of the command's argument list.
     *
     * @return the argument list.
     */
    public @NotNull List<CommandArgument<?>> getArguments() {
        return List.copyOf(argumentList);
    }

    /**
     * Adds an argument to the command.
     *
     * @param argument the argument to add
     * @throws IllegalArgumentException if an argument with the specified name already exist.
     */
    public final @NotNull CommandBuilder addArgument(@NotNull final CommandArgument<?> argument) throws IllegalArgumentException {
        if(!verifyArguments(List.of(argument)))
            throw new IllegalArgumentException("Argument " + argument.getName() + " is already present in this command tree");

        argumentList.add(argument);
        return this;
    }

    /**
     * Sets the arguments for the command.
     *
     * @param arguments The arguments
     * @throws IllegalArgumentException if an argument with the specified name already exist. (a duplicate in the array)
     */
    public final @NotNull CommandBuilder setArguments(@NotNull final CommandArgument<?>... arguments) throws IllegalArgumentException {
        this.argumentList.clear();

        for (CommandArgument<?> argument : arguments) {
            addArgument(argument);
        }

        return this;
    }

    /**
     * Verifies that all arguments are valid and ready to be executed.
     *
     * @return true if the arguments are secure, false otherwise.
     */
    private boolean verifyArguments(@NotNull final List<CommandArgument<?>> argumentList) {
        for (CommandArgument<?> argument : argumentList) {
            for(CommandArgument<?> matched : this.argumentList) {
                if (argument == matched) return false;
                if (argument.getName().equals(matched.getName())) return false;
            }
        }

        return true;
    }

    public final @Nullable CommandBuilder getNode(@NotNull final String name) {
        for(CommandBuilder node : nodeList) {
            if(!node.getName().equalsIgnoreCase(name)) continue;
            return node;
        }

        return null;
    }

    public @NotNull List<CommandBuilder> getNodeList() {
        return nodeList;
    }

    /**
     * @return returns true if the command tree has children nodes, false otherwise.
     */
    public final boolean containsNodes() {
        return !nodeList.isEmpty();
    }

    public final @NotNull CommandBuilder branch(@NotNull final CommandBuilder node) throws IllegalArgumentException {
        if(!verifyNodes(node))
            throw new IllegalArgumentException("Node " + node.getName() + " is already present in this command tree");

        if(node instanceof CommandBuilder) {
            node.parent = this;
            nodeList.add(node);
        }
        return this;
    }

    /**
     * @return the parent of the command tree.
     */
    public final @NotNull CommandBuilder getParent() {
        return parent;
    }

    /**
     * @return the root of the command tree.
     */
    public final @NotNull CommandBuilder root() {
        CommandBuilder parent = this.parent;

        while(parent.parent != null) {
            parent = parent.parent;
        }

        return parent;
    }

    /**
     * @return the list of all nodes in the command tree.
     */
    public final @NotNull List<CommandBuilder> hierarchy() {
        List<CommandBuilder> hierarchy = new ArrayList<>();
        CommandBuilder parent = this.parent;

        while(parent != null) {
            hierarchy.add(parent);
            parent = parent.parent;
        }

        return hierarchy;
    }

    /**
     * @return true if the console is allowed execute this command, false otherwise.
     */
    public final boolean isAllowConsoleExecution() {
        return allowConsoleExecution;
    }

    /**
     * Sets if the console is allowed to execute this command.
     *
     * @param allowConsoleExecution if the console is allowed to execute the command
     */
    public final @NotNull CommandBuilder setAllowConsoleExecution(final boolean allowConsoleExecution) {
        this.allowConsoleExecution = allowConsoleExecution;

        return this;
    }

    /**
     * Sets the execution lambda for the command.
     *
     * @param lambda the lambda
     * @throws RuntimeException if the command already has an execution lambda.
     */
    public final @NotNull CommandBuilder executes(@Nullable final ExecuteLambda lambda) throws RuntimeException {
        if(executeLambda != null)
            throw new RuntimeException("Command " + name + " already has an execute lambda");

        executeLambda = lambda;
        return this;
    }

    public @Nullable ExecuteLambda getExecuteLambda() {
        return executeLambda;
    }

    /**
     * Verifies that all nodes are valid and ready to be executed.
     *
     * @return true if the nodes are secure, false otherwise.
     */
    private boolean verifyNodes(@NotNull final CommandBuilder node) {
        for (CommandBuilder matched : nodeList) {
            if (node == matched) return false;
            if (node.getName().equals(matched.getName())) return false;
        }

        return true;
    }
    
    public interface ExecuteLambda {
        void run(@NotNull final CommandContext context) throws CommandArgumentException;
    }

}