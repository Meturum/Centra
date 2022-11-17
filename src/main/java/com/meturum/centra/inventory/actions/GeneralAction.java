package com.meturum.centra.inventory.actions;

import org.bukkit.event.inventory.InventoryAction;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static org.bukkit.event.inventory.InventoryAction.*;

public enum GeneralAction {

    PICKUP(PICKUP_ALL, PICKUP_HALF, PICKUP_SOME, PICKUP_ONE),
    PLACE(PLACE_ALL, PLACE_SOME, PLACE_ONE),
    DROP_SLOT(DROP_ALL_SLOT, DROP_ONE_SLOT),
    DROP_CURSOR(DROP_ALL_CURSOR, DROP_ONE_CURSOR),
    ALL(PLACE_ALL, PLACE_SOME, PLACE_ONE, PICKUP_ALL, PICKUP_HALF, PICKUP_SOME, PICKUP_ONE, DROP_ALL_SLOT, DROP_ONE_SLOT, DROP_ALL_CURSOR, DROP_ONE_CURSOR);

    private final InventoryAction[] children;

    GeneralAction(InventoryAction... children) {
        this.children = children;
    }

    public boolean isApplicable(@NotNull InventoryAction action) {
        for (InventoryAction children : getChildren()) {
            if (children.equals(action)) return true;
        }

        return false;
    }

    public @NotNull InventoryAction[] getChildren() {
        return children;
    }

    public static @Nullable GeneralAction generalize(@NotNull InventoryAction action) {
        for(GeneralAction generalAction : values()) {
            if(generalAction.isApplicable(action)) return generalAction;
        }

        return null;
    }

}
