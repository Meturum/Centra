package com.meturum.centra.inventory.item.nbt;

import com.meturum.centra.inventory.item.ItemBuilder;
import de.tr7zw.nbtapi.*;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public final class NBTWrapper {

    private final ItemBuilder parent;
    private final ActualNBTWrapper nbt;

    public NBTWrapper(@NotNull ItemBuilder item, @NotNull ItemStack bukkitItem) {
        this.parent = item;
        this.nbt = new ActualNBTWrapper(bukkitItem);
    }

    public @NotNull String getName() {
        return nbt.getName();
    }

    public @NotNull Object getCompound() {
        return nbt.getCompound();
    }


    public @NotNull NBTCompound getParent() {
        return nbt.getParent();
    }

    public @NotNull NBTWrapper mergeCompound(@NotNull final NBTCompound comp) {
        nbt.mergeCompound(comp);

        return this;
    }

    public @NotNull NBTWrapper setString(@NotNull final String key, @NotNull final String value) {
        nbt.setString(key, value);

        return this;
    }

    public @Nullable String getString(@NotNull final String key) {
        return nbt.getString(key);
    }

    public @NotNull NBTWrapper setInteger(@NotNull final String key, final int value) {
        nbt.setInteger(key, value);

        return this;
    }

    public int getInteger(@NotNull final String key) {
        return nbt.getInteger(key);
    }

    public @NotNull NBTWrapper setDouble(@NotNull final String key, final double value) {
        nbt.setDouble(key, value);

        return this;
    }

    public double getDouble(@NotNull final String key) {
        return nbt.getDouble(key);
    }

    public @NotNull NBTWrapper setByte(@NotNull final String key, final byte value) {
        nbt.setByte(key, value);

        return this;
    }

    public byte getByte(@NotNull final String key) {
        return nbt.getByte(key);
    }

    public @NotNull NBTWrapper setShort(@NotNull final String key, final short value) {
        nbt.setShort(key, value);

        return this;
    }

    public short getShort(@NotNull final String key) {
        return nbt.getShort(key);
    }

    public @NotNull NBTWrapper setLong(@NotNull final String key, final long value) {
        nbt.setLong(key, value);

        return this;
    }

    public long getLong(@NotNull final String key) {
        return nbt.getLong(key);
    }

    public @NotNull NBTWrapper setFloat(@NotNull final String key, final float value) {
        nbt.setFloat(key, value);

        return this;
    }

    public float getFloat(@NotNull final String key) {
        return nbt.getFloat(key);
    }

    public @NotNull NBTWrapper setByteArray(@NotNull final String key, final byte[] value) {
        nbt.setByteArray(key, value);

        return this;
    }

    public byte[] getByteArray(@NotNull final String key) {
        return nbt.getByteArray(key);
    }

    public @NotNull NBTWrapper setIntArray(@NotNull final String key, final int[] value) {
        nbt.setIntArray(key, value);

        return this;
    }

    public int[] getIntArray(@NotNull final String key) {
        return nbt.getIntArray(key);
    }

    public @NotNull NBTWrapper setBoolean(@NotNull final String key, final boolean value) {
        nbt.setBoolean(key, value);

        return this;
    }

    public boolean getBoolean(@NotNull final String key) {
        return nbt.getBoolean(key);
    }

    public @NotNull NBTWrapper setObject(@NotNull final String key, @NotNull final Object value) {
        nbt.setObject(key, value);

        return this;
    }

    public @Nullable <T> T getObject(@NotNull final String key, @NotNull final Class<T> type) {
        return nbt.getObject(key, type);
    }

    public @NotNull NBTWrapper setItemStack(@NotNull final String key, @NotNull final ItemStack item) {
        nbt.setItemStack(key, item);

        return this;
    }

    public @Nullable ItemStack getItemStack(@NotNull final String key) {
        return nbt.getItemStack(key);
    }

    public @NotNull NBTWrapper setUUID(@NotNull final String key, @NotNull final UUID value) {
        nbt.setUUID(key, value);

        return this;
    }

    public @Nullable UUID getUUID(@NotNull final String key) {
        return nbt.getUUID(key);
    }

    public boolean hasKey(@NotNull final String key) {
        return nbt.hasKey(key);
    }

    public void removeKey(@NotNull final String key) {
        nbt.removeKey(key);
    }

    public @NotNull Set<String> getKeys() {
        return nbt.getKeys();
    }

    public NBTCompound addCompound(@NotNull final String name) {
        return nbt.addCompound(name);
    }

    public @Nullable NBTCompound getCompound(@NotNull final String name) {
        return nbt.getCompound(name);
    }

    public @NotNull NBTCompound getOrCreateCompound(@NotNull final String name) {
        return nbt.getOrCreateCompound(name);
    }

    public NBTList<String> getStringList(@NotNull final String name) {
        return nbt.getStringList(name);
    }

    public NBTList<Integer> getIntegerList(@NotNull final String name) {
        return nbt.getIntegerList(name);
    }

    public NBTList<int[]> getIntArrayList(@NotNull final String name) {
        return nbt.getIntArrayList(name);
    }

    public NBTList<UUID> getUUIDList(@NotNull final String name) {
        return nbt.getUUIDList(name);
    }

    public NBTList<Float> getFloatList(@NotNull final String name) {
        return nbt.getFloatList(name);
    }

    public NBTList<Double> getDoubleList(@NotNull final String name) {
        return nbt.getDoubleList(name);
    }

    public NBTList<Long> getLongList(@NotNull final String name) {
        return nbt.getLongList(name);
    }

    public NBTType getListType(@NotNull final String name) {
        return nbt.getListType(name);
    }

    public NBTCompoundList getCompoundList(@NotNull final String name) {
        return nbt.getCompoundList(name);
    }

    public NBTType getType(@NotNull final String name) {
        return nbt.getType(name);
    }

    public void writeCompound(@NotNull final OutputStream stream) {
        nbt.writeCompound(stream);
    }

    public @NotNull ItemBuilder save() {
        nbt.saveCompound();

        return parent;
    }

    protected static class ActualNBTWrapper extends NBTItem {

        public ActualNBTWrapper(@NotNull final ItemStack item) {
            super(item, true);
        }

        public void saveCompound() {
            super.saveCompound();
        }

    }

}
