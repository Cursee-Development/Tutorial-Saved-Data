package com.cursee.saved_data.core.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.UUID;

public class ItemUseCountData extends SavedData {

    int globalUseCount = 0;
    public HashMap<UUID, PlayerItemUseCountData> playerDataFromUUIDMap = new HashMap<>();

    private static ItemUseCountData createFunction() {
        ItemUseCountData data = new ItemUseCountData();
        data.globalUseCount = 0;
        data.playerDataFromUUIDMap = new HashMap<>();
        return data;
    }

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

    public static ItemUseCountData fromServer(MinecraftServer server) {
        ServerLevel level = server.getLevel(Level.OVERWORLD);
        ItemUseCountData data = level.getDataStorage().computeIfAbsent(ItemUseCountData::loadFunction, ItemUseCountData::createFunction, "saved_data_mod_data");
        data.setDirty();
        return data;
    }

    public static PlayerItemUseCountData fromPlayer(MinecraftServer server, ServerPlayer player) {

        // get the player's data by their uuid, or make new data attached to their uuid
        return fromServer(server).playerDataFromUUIDMap.computeIfAbsent(player.getUUID(), uuid -> new PlayerItemUseCountData());
    }

    public void incrementGlobalUseCount() {
        globalUseCount++;
    }

    public int getGlobalUseCount() {
        return globalUseCount;
    }

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
