package com.meturum.centra.inventory;

import com.google.common.collect.ImmutableList;
import com.meturum.centra.inventory.container.Container;
import com.meturum.centra.inventory.item.Position;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public interface CustomInventory {

    int MINIMUM_HEIGHT = 1; int MAXIMUM_HEIGHT = 6;
    int MINIMUM_WIDTH = 1; int MAXIMUM_WIDTH = 9;

    /**
     * Gets the height of the interface.
     *
     * @return the height of the interface.
     */
    int getHeight();

    /**
     * Gets the container at the specified position.
     *
     * @param position the position.
     * @return the container at the specified position. if no container is found, null is returned.
     */
    @Nullable Container getContainer(int position);

    /**
     * Gets the container at the specified position.
     *
     * @param position the position.
     * @return the container at the specified position. if no container is found, null is returned.
     */
    @Nullable Container getContainer(@NotNull Position position);

    /**
     * Checks if the interface contains a container.
     *
     * @param container The container to check.
     * @return true if the interface contains the container, false otherwise.
     */
    boolean containsContainer(@NotNull Container container);

    /**
     * Removes the specified container from the interface.
     *
     * @param container the specified container.
     * @return true if the container was removed, false otherwise.
     */
    boolean removeContainer(@NotNull Container container);

    /**
     * Gets all the containers of the interface.
     *
     * @return all the containers of the interface.
     */
    @NotNull ImmutableList<Container> getContainers();

    /**
     * @return the contents of the interface.
     */
    @NotNull ItemStack[] getContents();

    /**
     * Checks if the interface contains the specified item.
     *
     * @param item the specified item.
     * @return true if the interface contains the specified item, false otherwise.
     */
    boolean contains(@NotNull ItemStack item);

    /**
     * Checks if the interface contains an item at the specified position.
     *
     * @param position the specified position.
     * @return true if the interface contains an item at the specified position, false otherwise.
     */
    boolean isEmpty(int position);

    /**
     * Checks if the interface contains any items.
     *
     * @return true if the interface contains any items, false otherwise.
     */
    boolean isEmpty();

    /**
     * Gets the title of the interface.
     *
     * @return the title of the interface.
     */
    @NotNull String getTitle();

    /**
     * Sets the title of the interface.
     *
     * @param title the title of the interface.
     */
    CustomInventory setTitle(@Nullable String title);

    /**
     * Gets the overlay of the interface.
     *
     * @return the overlay of the interface.
     */
    @Nullable String getOverlay();

    /**
     * Checks if the interface contains an overlay.
     *
     * @return true if the interface contains an overlay, false otherwise.
     */
    boolean hasOverlay();

    /**
     * Sets the overlay of the interface.
     *
     * @param overlay the overlay of the interface.
     */
    CustomInventory setOverlay(@Nullable String overlay);

    /**
     * Checks if the specified player is viewing the interface.
     *
     * @param player the specified player.
     * @return true if the specified player is viewing the interface, false otherwise.
     */
    boolean isViewing(@NotNull Player player);

    /**
     * Opens the interface for the specified player.
     *
     * @param player the specified player.
     * @param force whether to close the player's current interface and force viewing
     * @throws IllegalStateException if the interface is already open for the specified player.
     */
    CustomInventory view(@NotNull Player player, boolean force) throws IllegalStateException;

    /**
     * Opens the interface for the specified player.
     *
     * @param player the specified player.
     * @throws IllegalStateException if the interface is already open for the specified player.
     */
    CustomInventory view(@NotNull Player player) throws IllegalStateException;

    /**
     * Closes the interface for the specified player.
     *
     * @param player the specified player.
     * @param force whether to force close the interface
     * @throws IllegalStateException if the interface is not open for the specified player.
     */
    void close(@NotNull Player player, boolean force);

    /**
     * Closes the interface for the specified player.
     *
     * @param player the specified player.
     * @throws IllegalStateException if the interface is not open for the specified player.
     */
    void close(@NotNull Player player);

    /**
     * @return the current viewers of the interface.
     */
    @NotNull List<Player> getViewers();

    /**
     * Maximizes the inventory. (Uses the player's inventory)
     */
    @NotNull CustomInventory setMaximized();

    /**
     * Minimizes the inventory. (Removes the use of the player's inventory)
     */
    @NotNull CustomInventory setMinimized();

    /**
     * @return true if the inventory is maximized, false otherwise.
     */
    boolean isMaximized();

    /**
     * Creates a container and adds it to the interface.
     *
     * @param slots the slots of the container.
     * @return the container created.
     * @throws IllegalArgumentException if the container could not be created.
     */
    @NotNull Container createContainer(@NotNull List<Integer> slots) throws IllegalArgumentException;


    /**
     * Creates a container and adds it to the interface.
     *
     * @param x the x position of the container.
     * @param y the y position of the container.
     * @param height the height of the container.
     * @param width the width of the container.
     * @return the container created.
     * @throws IllegalArgumentException if the container could not be created.
     */
    @NotNull Container createContainer(int x, int y, int height, int width) throws IllegalArgumentException;

    /**
     * Creates a container and adds it to the interface.
     *
     * @param x the x position of the container.
     * @param y the y position of the container.
     * @param height the height of the container.
     * @return the container created.
     * @throws IllegalArgumentException if the container could not be created.
     * @apiNote the width of the container is defaulted to 9.
     */
    @NotNull Container createContainer(int x, int y, int height) throws IllegalArgumentException;

    /**
     * Destroys the interface. This will close all viewers and remove the interface from the interface manager.
     */
    void destroy();

}
