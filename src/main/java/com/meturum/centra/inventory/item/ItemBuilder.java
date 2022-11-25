package com.meturum.centra.inventory.item;

import com.meturum.centra.inventory.item.nbt.NBTWrapper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ItemBuilder {

    private final ItemStack bukkitItem;

    private final ItemMeta itemMeta;
    private final NBTWrapper nbt;

    private @Nullable String customName;

    private @Nullable Position position;

    public ItemBuilder(@NotNull final ItemStack item) {
        this.bukkitItem = item;
        this.itemMeta = item.getItemMeta();

        if(itemMeta.hasDisplayName())
            this.customName = itemMeta.getDisplayName();

        this.nbt = new NBTWrapper(this, bukkitItem);
    }

    public ItemBuilder(@NotNull final Material material) {
        this(new ItemStack(material));
    }

    public @Nullable String getCustomName() {
        return customName;
    }

    public @NotNull ItemBuilder setCustomName(@NotNull final String customName) {
        this.customName = customName;

        return this;
    }

    public @Nullable String getFID() {
        if(nbt.hasKey("centre:FID")) {
            return nbt.getString("centre:FID");
        }

        return "";
    }

    public @NotNull ItemBuilder setFID(@NotNull final String FID) {
        nbt.setString("centre:FID", FID);

        return this;
    }

    public boolean isLocked() {
        if(nbt.hasKey("centre:locked")) {
            return nbt.getBoolean("centre:isLocked");
        }

        return false;
    }

    public @NotNull ItemBuilder setLocked(final boolean locked) {
        nbt.setBoolean("centre:isLocked", locked);

        return this;
    }

    public @Nullable Position getPosition() {
        return position;
    }

    public @NotNull ItemBuilder setPosition(@Nullable final Position position) {
        this.position = position;

        return this;
    }

    public @NotNull ItemMeta getMeta() {
        return itemMeta;
    }

    public @NotNull NBTWrapper getNBT() {
        return nbt;
    }

    public @NotNull ItemStack build() {
        nbt.save();

        ItemMeta meta = bukkitItem.getItemMeta();

        if(customName != null)
            meta.setDisplayName(customName);

        bukkitItem.setItemMeta(meta);
        return bukkitItem;
    }

}
