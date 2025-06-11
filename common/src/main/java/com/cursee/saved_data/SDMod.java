package com.cursee.saved_data;

import com.cursee.saved_data.core.data.ItemUseCountData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;

public class SDMod {

    static MinecraftServer SERVER;

    public static void init() {}

    public static void onServerStarted(MinecraftServer server) {
        ItemUseCountData.fromServer(server); // to compute it for the first time if not created
        if (SERVER == null) SERVER = server;
    }

    public static ItemUseCountData freshData() {
        if (SERVER == null) throw new IllegalStateException("Attempted to access data before SERVER was assigned.");
        return ItemUseCountData.fromServer(SERVER);
    }

    public static ResourceLocation identifier(String path) {
        return new ResourceLocation(Constants.MOD_ID, path);
    }
}