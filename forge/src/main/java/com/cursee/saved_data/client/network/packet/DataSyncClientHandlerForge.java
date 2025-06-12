package com.cursee.saved_data.client.network.packet;

import com.cursee.saved_data.SDModClient;
import com.cursee.saved_data.core.network.packet.DataSyncS2CPacketForge;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DataSyncClientHandlerForge {

    public static void registerS2CPacketHandler(DataSyncS2CPacketForge packet, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            SDModClient.synced_use_count = packet.globalCount;
            SDModClient.synced_player_use_count = packet.playerCount;
        });
    }
}
