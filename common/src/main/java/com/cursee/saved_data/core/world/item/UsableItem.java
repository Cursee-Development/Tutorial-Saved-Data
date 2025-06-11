package com.cursee.saved_data.core.world.item;

import com.cursee.saved_data.SDMod;
import com.cursee.saved_data.SDModClient;
import com.cursee.saved_data.core.data.ItemUseCountData;
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
            player.sendSystemMessage(Component.literal("Client Count Prediction: " + String.valueOf(SDModClient.synced_count + 1)));
        }

        if (!level.isClientSide()) {

            // saved data operations must occur server side, then be synced to the client
            SDMod.freshData().increment();
            player.sendSystemMessage(Component.literal("Server Count: " + SDMod.freshData().getCount()));

            Services.PLATFORM.sendDataSyncPacket((ServerPlayer) player);
        }

        return super.use(level, player, usedHand);
    }
}
