package com.cursee.saved_data;

import com.cursee.saved_data.core.data.ItemUseCountData;
import com.cursee.saved_data.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class SDMod {

    static MinecraftServer SERVER;

    public static void init() {}

    public static void onServerStarted(MinecraftServer server) {
        ItemUseCountData.fromServer(server); // to compute it for the first time if not created
        if (SERVER == null) SERVER = server;
        Constants.LOG.info("SERVER was assigned.");
    }

    public static void onPlayerJoinedServer(ServerPlayer player) {
        Services.PLATFORM.sendDataSyncPacket(player);
    }

    public static ItemUseCountData freshData() {
        if (SERVER == null) throw new IllegalStateException("Attempted to access data before SERVER was assigned.");
        return ItemUseCountData.fromServer(SERVER);
    }

    public static ItemUseCountData.PlayerItemUseCountData freshData(ServerPlayer player) {
        if (SERVER == null) throw new IllegalStateException("Attempted to access data before SERVER was assigned.");
        return ItemUseCountData.fromPlayer(SERVER, player);
    }

    public static ResourceLocation identifier(String path) {
        return new ResourceLocation(Constants.MOD_ID, path);
    }
}