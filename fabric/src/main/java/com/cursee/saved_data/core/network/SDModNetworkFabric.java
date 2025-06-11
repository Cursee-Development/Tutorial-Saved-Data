package com.cursee.saved_data.core.network;

import com.cursee.saved_data.SDMod;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class SDModNetworkFabric {

    public static class Packets {
        public static final ResourceLocation DATA_SYNC_S2C = SDMod.identifier("data_sync");
    }

    public static void init() {}

    public static void sendToPlayer(FriendlyByteBuf data, ServerPlayer player, ResourceLocation packetID) {
        ServerPlayNetworking.send(player, packetID, data);
    }

    public static void sendToServer(FriendlyByteBuf data, ResourceLocation packetID) {
        ClientPlayNetworking.send(packetID, data);
    }
}
