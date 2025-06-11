package com.cursee.saved_data;

import com.cursee.saved_data.client.network.packet.DataSyncClientHandlerFabric;
import com.cursee.saved_data.core.network.SDModNetworkFabric;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class SDModClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(SDModNetworkFabric.Packets.DATA_SYNC_S2C, DataSyncClientHandlerFabric::receiveOnClient);
    }
}
