package com.cursee.saved_data;

import com.cursee.saved_data.core.registry.ModRegistryForge;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.function.Consumer;

@Mod(Constants.MOD_ID)
public class SDModForge {

    public static IEventBus EVENT_BUS;

    public SDModForge(FMLJavaModLoadingContext context) {
        SDMod.init();
        EVENT_BUS = context.getModEventBus();
        if (FMLEnvironment.dist == Dist.CLIENT) new SDModClientForge(EVENT_BUS);
        ModRegistryForge.register(EVENT_BUS);

        MinecraftForge.EVENT_BUS.addListener((Consumer<ServerStartedEvent>) event -> SDMod.onServerStarted(event.getServer()));
    }

    @SuppressWarnings("removal")
    public SDModForge() {
        this(FMLJavaModLoadingContext.get());
    }
}