package com.cursee.saved_data;

import com.cursee.saved_data.core.network.SDModNetworkForge;
import com.cursee.saved_data.core.network.packet.DataSyncS2CPacketForge;
import com.cursee.saved_data.core.registry.ModRegistryForge;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
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
        SDModNetworkForge.init();

        MinecraftForge.EVENT_BUS.addListener((Consumer<ServerStartedEvent>) event -> SDMod.onServerStarted(event.getServer()));
        MinecraftForge.EVENT_BUS.addListener((Consumer<EntityJoinLevelEvent>) event -> {
            if (!(event.getEntity() instanceof ServerPlayer player)) return;
            DataSyncS2CPacketForge.createAndSend(player);
        });
    }

    @SuppressWarnings("removal")
    public SDModForge() {
        this(FMLJavaModLoadingContext.get());
    }
}