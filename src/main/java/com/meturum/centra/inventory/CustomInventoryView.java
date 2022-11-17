package com.meturum.centra.inventory;

import com.meturum.centra.inventory.container.Container;
import com.meturum.centra.inventory.item.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public interface CustomInventoryView {

    /**
     * @return the player linked to the viewer cursor.
     */
    @NotNull Player getOwner();

    /**
     * @return the current item in the viewer cursor.
     */
    @Nullable
    ItemBuilder getCursor();

    /**
     * @return the interface linked to the viewer cursor.
     */
    @NotNull CustomInventory getInventory();

    /**
     * @return the current container linked to the viewer cursor.
     */
    @Nullable Container getContainer();

    /**
     * @return The contents of the player's inventory.
     * @apiNote Should only be used for when the player's inventory is currently linked to a {@link CustomInventory}.
     */
    @Nullable ItemStack[] getBottomContents();

}
