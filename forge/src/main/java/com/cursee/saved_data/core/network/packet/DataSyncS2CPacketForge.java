package com.cursee.saved_data.core.network.packet;

import com.cursee.saved_data.SDMod;
import com.cursee.saved_data.client.network.packet.DataSyncClientHandlerForge;
import com.cursee.saved_data.core.network.SDModNetworkForge;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DataSyncS2CPacketForge {

    public final int globalCount;
    public final int playerCount;

    public DataSyncS2CPacketForge(int globalCount, int playerCount) {
        this.globalCount = globalCount;
        this.playerCount = playerCount;
    }

    public void encode(FriendlyByteBuf data) {
        data.writeInt(this.globalCount);
        data.writeInt(this.playerCount);
    }

    public static DataSyncS2CPacketForge decode(FriendlyByteBuf data) {
        return new DataSyncS2CPacketForge(data.readInt(), data.readInt());
    }

    public static void handle(DataSyncS2CPacketForge packet, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> DataSyncClientHandlerForge.registerS2CPacketHandler(packet, contextSupplier))
        );
        contextSupplier.get().setPacketHandled(true);
    }

    public static void createAndSend(ServerPlayer player) {
        SDModNetworkForge.sendToPlayer(new DataSyncS2CPacketForge(SDMod.freshData().getGlobalUseCount(), SDMod.freshData(player).getPlayerUseCount()), player);
    }
}
