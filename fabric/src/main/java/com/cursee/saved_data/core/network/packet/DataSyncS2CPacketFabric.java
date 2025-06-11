package com.cursee.saved_data.core.network.packet;

import com.cursee.saved_data.SDMod;
import com.cursee.saved_data.core.network.SDModNetworkFabric;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class DataSyncS2CPacketFabric {

    public static void createAndSend(ServerPlayer player) {
        FriendlyByteBuf data = new FriendlyByteBuf(Unpooled.buffer());
        data.writeInt(SDMod.freshData().getCount());
        SDModNetworkFabric.sendToPlayer(data, player, SDModNetworkFabric.Packets.DATA_SYNC_S2C);
    }
}
