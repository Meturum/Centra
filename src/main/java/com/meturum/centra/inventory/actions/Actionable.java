package com.meturum.centra.inventory.actions;

import org.bukkit.event.inventory.InventoryAction;
import org.jetbrains.annotations.NotNull;

public interface Actionable {

    /**
     * Sets the allowed actions for the actionable.
     *
     * @param actions the actions to set.
     */
    @NotNull Actionable setAllowedActions(@NotNull GeneralAction... actions);

    /**
     * Sets the allowed actions for the actionable.
     *
     * @param actions the actions to set.
     */
    @NotNull Actionable setAllowedActions(@NotNull InventoryAction... actions);

    /**
     * @return the allowed actions for the actionable.
     */
    @NotNull InventoryAction[] getAllowedActions();

    /**
     * Sets the lambda to be executed when the item is clicked.
     *
     * @param lambda the lambda to be executed.
     */
    Actionable interacts(@NotNull ActionLambda lambda);

    /**
     * Sets the lambda to be executed when the item is clicked.
     *
     * @param lambda the lambda to be executed.
     * @param applicableActions the actions that will trigger the lambda.
     */
    Actionable interacts(@NotNull ActionLambda lambda, InventoryAction... applicableActions);

    /**
     * Sets the lambda to be executed when the item is clicked.
     *
     * @param lambda the lambda to be executed.
     * @param applicableActions the actions that will trigger the lambda.
     */
    Actionable interacts(@NotNull ActionLambda lambda, GeneralAction... applicableActions);

    /**
     * @return true if dragging is allowed, false otherwise.
     */
    boolean isAllowDragging();

    /**
     * Sets if dragging is allowed.
     *
     * @param allowDragging true if dragging is allowed, false otherwise.
     */
    Actionable setAllowDragging(boolean allowDragging);

    interface ActionLambda {
        void run(@NotNull ActionEventContext context);
    }

}
