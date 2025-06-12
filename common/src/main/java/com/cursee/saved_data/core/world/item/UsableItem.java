package com.cursee.saved_data.core.world.item;

import com.cursee.saved_data.SDMod;
import com.cursee.saved_data.SDModClient;
import com.cursee.saved_data.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class UsableItem extends Item {

    public UsableItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {


        if (level.isClientSide()) {
            // the client runs faster than the player receives data from the server,
            // so we predict the value to fake perfect synchronization
            player.sendSystemMessage(Component.literal("Client Use Count Prediction: " + String.valueOf(SDModClient.synced_use_count + 1)));
            player.sendSystemMessage(Component.literal("Client Player Use Count Prediction: " + String.valueOf(SDModClient.synced_player_use_count + (player.isCrouching() ? 1 : 0))));
        }

        if (!level.isClientSide()) {

            ServerPlayer sPlayer = (ServerPlayer) player;

            // saved data operations must occur server side, then be synced to the client
            SDMod.freshData().incrementGlobalUseCount();
            if (player.isCrouching()) SDMod.freshData(sPlayer).incrementPlayerUseCount();

            player.sendSystemMessage(Component.literal("Server Use Count: " + SDMod.freshData().getGlobalUseCount()));
            player.sendSystemMessage(Component.literal("Server Player Use Count: " + SDMod.freshData(sPlayer).getPlayerUseCount()));

            Services.PLATFORM.sendDataSyncPacket(sPlayer);
        }

        return super.use(level, player, usedHand);
    }
}
