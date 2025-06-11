package com.cursee.saved_data.client.network.packet;

import com.cursee.saved_data.SDModClient;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;

public class DataSyncClientHandlerFabric {

    public static void receiveOnClient(Minecraft client, ClientPacketListener handler, FriendlyByteBuf data, PacketSender responseSender) {
        SDModClient.synced_count = data.readInt();
    }
}
