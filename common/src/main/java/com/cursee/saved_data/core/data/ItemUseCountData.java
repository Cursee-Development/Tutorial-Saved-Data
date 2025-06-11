package com.cursee.saved_data.core.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraft.world.level.saveddata.SavedData;

public class ItemUseCountData extends SavedData {

    int count = 0;

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }

    private static ItemUseCountData createFunction() {
        ItemUseCountData data = new ItemUseCountData();
        data.count = 0;
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {

        tag.putInt("count", this.count);

        return tag;
    }

    private static ItemUseCountData loadFunction(CompoundTag tag) {
        ItemUseCountData data = new ItemUseCountData();

        data.count = tag.getInt("count");

        return data;
    }

    public static ItemUseCountData fromServer(MinecraftServer server) {
        ServerLevel level = server.getLevel(Level.OVERWORLD);

        ItemUseCountData data = level.getDataStorage().computeIfAbsent(ItemUseCountData::loadFunction, ItemUseCountData::createFunction, "customdata");
        data.setDirty();
        return data;
    }
}
