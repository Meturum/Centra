package com.meturum.centra.inventory.container;

import com.meturum.centra.inventory.actions.Actionable;
import com.meturum.centra.inventory.actions.GeneralAction;
import com.meturum.centra.inventory.item.ItemBuilder;
import com.meturum.centra.inventory.item.Position;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public interface Container extends Actionable {

    /**
     * Get the item at the specified position.
     *
     * @param slot the slot.
     * @return the item at the specified position. if no item is found, null is returned.
     * @throws IndexOutOfBoundsException if the slot is out of range.
     */
    @Nullable ItemBuilder getItem(int slot) throws IndexOutOfBoundsException;

    /**
     * Get the item at the specified position.
     *
     * @param position the position.
     * @return the item at the specified position. if no item is found, null is returned.
     * @throws IndexOutOfBoundsException if the position is out of range.
     */
    @Nullable ItemBuilder getItem(@NotNull Position position) throws IndexOutOfBoundsException;

    /**
     * Checks if the container contains an item.
     *
     * @param item the item to check.
     * @return true if the container contains the item, false otherwise.
     */
    boolean containsItem(@NotNull ItemBuilder item);

    /**
     * Checks if the container contains an item at the specified position.
     *
     * @param slot the slot.
     * @return true if the container contains the item, false otherwise.
     */
    boolean containsItem(int slot);

    /**
     * Checks if the container contains an item at the specified position.
     *
     * @param position the position.
     * @return true if the container contains the item, false otherwise.
     */
    boolean containsItem(@NotNull Position position);

    /**
     * Sets the item at the specified position.
     *
     * @param item the item.
     * @param position the position.
     * @throws IndexOutOfBoundsException if the position is out of range.
     */
    Container setItem(@NotNull ItemBuilder item, @NotNull Position position) throws IndexOutOfBoundsException;

    /**
     * Sets the item at the specified position.
     *
     * @param item the item.
     * @param slot the position.
     * @throws IndexOutOfBoundsException if the position is out of range.
     */
    Container setItem(@NotNull ItemBuilder item, int slot) throws IndexOutOfBoundsException;

    /**
     * Adds the item to the container.
     *
     * @param item the item.
     */
    Container addItem(@NotNull ItemBuilder item);

    /**
     * Adds the items to the container.
     *
     * @param items the items.
     */
    List<ItemBuilder> addItems(@NotNull ItemBuilder... items);

    /**
     * Fills the container with the specified material.
     *
     * @param material the material.
     * @return the container.
     */
    Container fill(@NotNull Material material);

    /**
     * Removes the item at the specified position.
     *
     * @param slot the slot.
     */
    void clear(int slot);

    /**
     * Removes the item at the specified position.
     *
     * @param position the position.
     */
    void clear(@NotNull Position position);

    /**
     * Clears the container.
     */
    void clear();

    /**
     * Gets the slots of the container.
     *
     * @return the slots of the container.
     */
    @NotNull LinkedList<Integer> getSlots();

    /**
     * Gets the items of the container.
     *
     * @return the items of the container.
     */
    @NotNull LinkedHashMap<Integer, ItemBuilder> getContents();

    // -- Actionable Method Overrides -- // (For return types)

    @NotNull Container setAllowedActions(@NotNull InventoryAction... actions);

    @NotNull Container setAllowedActions(@NotNull GeneralAction... actions);

    Container setAllowDragging(boolean allowDragging);

    Container interacts(@NotNull ActionLambda lambda);

    Container interacts(@NotNull ActionLambda lambda, InventoryAction... applicableActions);

    Container interacts(@NotNull ActionLambda lambda, GeneralAction... applicableActions);

    // --- End Actionable Method Overrides --- //

}
