package com.cursee.saved_data;

import com.cursee.saved_data.core.registry.ModRegistryFabric;
import net.fabricmc.api.ModInitializer;

public class SDModFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        SDMod.init();
        ModRegistryFabric.register();
    }
}
