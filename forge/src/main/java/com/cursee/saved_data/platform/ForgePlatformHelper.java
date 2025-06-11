package com.cursee.saved_data.platform;

import com.cursee.saved_data.core.data.ItemUseCountData;
import com.cursee.saved_data.core.network.packet.DataSyncS2CPacketForge;
import com.cursee.saved_data.platform.services.IPlatformHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public String getGameDirectory() {

        return FMLPaths.GAMEDIR.get().toString();
    }

    @Override
    public boolean isClientSide() {

        return FMLEnvironment.dist == Dist.CLIENT;
    }

    @Override
    public void sendDataSyncPacket(ServerPlayer player) {
        DataSyncS2CPacketForge.createAndSend(player);
    }
}