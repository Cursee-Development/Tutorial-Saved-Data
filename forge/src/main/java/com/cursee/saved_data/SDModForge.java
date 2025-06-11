package com.cursee.saved_data;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Constants.MOD_ID)
public class SDModForge {

    public static IEventBus EVENT_BUS;

    public SDModForge(FMLJavaModLoadingContext context) {
        SDMod.init();
        EVENT_BUS = context.getModEventBus();
        if (FMLEnvironment.dist == Dist.CLIENT) new SDModClientForge(EVENT_BUS);
    }

    @SuppressWarnings("removal")
    public SDModForge() {
        this(FMLJavaModLoadingContext.get());
    }
}