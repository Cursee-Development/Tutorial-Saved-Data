package com.cursee.saved_data.core.registry;

import com.cursee.saved_data.SDMod;
import com.cursee.saved_data.core.world.item.UsableItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.BiConsumer;

public class ModItems {

    public static final Item USABLE_ITEM = new UsableItem(new Item.Properties().stacksTo(1));

    public static void register(BiConsumer<Item, ResourceLocation> consumer) {
        consumer.accept(USABLE_ITEM, SDMod.identifier("usable_item"));
    }
}
