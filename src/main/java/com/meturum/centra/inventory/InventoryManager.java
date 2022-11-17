package com.meturum.centra.inventory;

import com.meturum.centra.system.System;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public interface InventoryManager extends System {

    /**
     * Creates a new interface.
     *
     * @param inventory the inventory to link to the interface.
     * @return the interface created.
     */
    @NotNull CustomInventory createInventory(@NotNull Inventory inventory);

    /**
     * Creates a new interface.
     *
     * @param height the height of the interface.
     * @return the interface created.
     */
    @NotNull CustomInventory createInventory(int height);

}
