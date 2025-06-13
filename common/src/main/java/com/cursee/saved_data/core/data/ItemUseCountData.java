package com.cursee.saved_data.core.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.UUID;

/**
 * Stores global and per-player item use counts using Minecraft's SavedData system.
 * This data is persisted per world and synchronized from the server side.
 */
public class ItemUseCountData extends SavedData {

    /** The total number of times the tracked item has been used globally. */
    int globalUseCount = 0;

    /** A map of a player's UUID to their use data. */
    public HashMap<UUID, PlayerItemUseCountData> playerDataFromUUIDMap = new HashMap<>();

    /**
     * Creates a new, empty instance of ItemUseCountData.
     * <p></p>
     * This could also be a canonical constructor, we can verify our beginning values here.
     */
    private static ItemUseCountData createFunction() {
        ItemUseCountData data = new ItemUseCountData();
        data.globalUseCount = 0;
        data.playerDataFromUUIDMap = new HashMap<>();
        return data;
    }

    /**
     * Serialize this data into an NBT tag.
     */
    @Override
    public CompoundTag save(CompoundTag tag) {

        tag.putInt("globalCount", this.globalUseCount);

        CompoundTag playersNbt = new CompoundTag();
        playerDataFromUUIDMap.forEach((uuid, playerData) -> {
            CompoundTag playerNbt = new CompoundTag();
            playerNbt.putInt("playerCount", playerData.playerUseCount);
            playersNbt.put(uuid.toString(), playerNbt);
        });
        tag.put("playerCounts", playersNbt);

        return tag;
    }

    /**
     * Deserialize this data from the given NBT tag.
     */
    private static ItemUseCountData loadFunction(CompoundTag tag) {
        ItemUseCountData data = new ItemUseCountData();

        data.globalUseCount = tag.getInt("globalCount");

        CompoundTag playersNbt = tag.getCompound("playerCounts");
        playersNbt.getAllKeys().forEach(key -> {
            PlayerItemUseCountData playerData = new PlayerItemUseCountData();
            playerData.playerUseCount = playersNbt.getCompound(key).getInt("playerCount");
            UUID uuid = UUID.fromString(key);
            data.playerDataFromUUIDMap.put(uuid, playerData);
        });

        return data;
    }

    /**
     * Get or create data for a server.
     * <p> </p>
     * Adapted from Fabric's PersistentState tutorial:
     * <p> </p>
     * "If the data is not marked dirty before Minecraft closes, 'save' won't be called and therefore nothing will be saved.
     * There is a 'cost' is when the file is written to the disk and no actual change to any part of the mod data was present (which is rare)."
     */
    public static ItemUseCountData fromServer(MinecraftServer server) {
        ServerLevel level = server.getLevel(Level.OVERWORLD);
        ItemUseCountData data = level.getDataStorage().computeIfAbsent(ItemUseCountData::loadFunction, ItemUseCountData::createFunction, "saved_data_mod_data");
        data.setDirty();
        return data;
    }

    /**
     * Get or create data for a player from their UUID
     */
    public static PlayerItemUseCountData fromPlayer(MinecraftServer server, ServerPlayer player) {
        return fromServer(server).playerDataFromUUIDMap.computeIfAbsent(player.getUUID(), uuid -> new PlayerItemUseCountData());
    }

    public void incrementGlobalUseCount() {
        globalUseCount++;
    }

    public int getGlobalUseCount() {
        return globalUseCount;
    }

    /**
     * For the data specific to player instances.
     */
    public static class PlayerItemUseCountData {

        int playerUseCount = 0;

        public void incrementPlayerUseCount() {
            playerUseCount++;
        }

        public int getPlayerUseCount() {
            return playerUseCount;
        }
    }
}
