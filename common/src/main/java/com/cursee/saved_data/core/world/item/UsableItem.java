package com.cursee.saved_data.core.world.item;

import com.cursee.saved_data.core.data.ItemUseCountData;
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
        if (!level.isClientSide() && level.getServer() != null) {
            ItemUseCountData.fromServer(level.getServer()).increment();
            System.out.println("Count: " + ItemUseCountData.fromServer(level.getServer()).getCount());
        }
        return super.use(level, player, usedHand);
    }
}
