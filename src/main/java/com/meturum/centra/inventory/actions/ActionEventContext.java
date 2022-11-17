package com.meturum.centra.inventory.actions;

import com.meturum.centra.inventory.CustomInventory;
import com.meturum.centra.inventory.container.Container;
import com.meturum.centra.inventory.item.ItemBuilder;
import com.meturum.centra.inventory.item.Position;
import com.meturum.centra.sessions.Session;
import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public interface ActionEventContext extends Cancellable {

    /**
     * @return the interface linked to the action context.
     */
    @NotNull CustomInventory getInventory();

    /**
     * @return the container linked to the action context.
     */
    @Nullable
    Container getContainer();

    /**
     * @return the session linked to the action context.
     */
    @NotNull Session getPlayer();

    /**
     * @return the item clicked in the action context.
     */
    @Nullable
    ItemBuilder getCurrentItem();

    /**
     * @return the position of the item clicked in the action context.
     */
    @NotNull Position getPosition();

    /**
     * @return the event linked to the action context.
     */
    @NotNull InventoryClickEvent getBukkitContext();

    /**
     * @return the result of the action context.
     */
    @NotNull ActionResult getResult();

    /**
     * Sets the result of the action context.
     *
     * @param result the result to set.
     */
    void setResult(@NotNull ActionResult result);

    enum ActionResult {

        ALLOW(false), DENY(true), DEFAULT();

        private boolean value = false;

        ActionResult() { }

        ActionResult(boolean value) {
            this.value = value;
        }

        public boolean getValue() {
            return value;
        }

    }

}
