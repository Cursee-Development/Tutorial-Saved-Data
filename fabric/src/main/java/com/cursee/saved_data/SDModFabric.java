package com.cursee.saved_data;

import com.cursee.saved_data.core.registry.ModRegistryFabric;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class SDModFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        SDMod.init();
        ModRegistryFabric.register();

        ServerLifecycleEvents.SERVER_STARTED.register(SDMod::onServerStarted);
    }
}
