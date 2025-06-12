package com.cursee.saved_data;

import com.cursee.saved_data.core.network.SDModNetworkFabric;
import com.cursee.saved_data.core.network.packet.DataSyncS2CPacketFabric;
import com.cursee.saved_data.core.registry.ModRegistryFabric;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.level.ServerPlayer;

public class SDModFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        SDMod.init();
        ModRegistryFabric.register();
        SDModNetworkFabric.init();

        ServerLifecycleEvents.SERVER_STARTED.register(SDMod::onServerStarted);
        ServerEntityEvents.ENTITY_LOAD.register((entity, serverLevel) -> {
            if (!(entity instanceof ServerPlayer player)) return;
            SDMod.onPlayerJoinedServer(player);
        });
    }
}
