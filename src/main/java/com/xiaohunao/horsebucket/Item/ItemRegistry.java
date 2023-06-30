package com.xiaohunao.horsebucket.Item;

import com.xiaohunao.horsebucket.HorseBucket;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HorseBucket.MOD_ID);

    public static RegistryObject<Item> HORSE_BUCKET = ITEMS.register("horse_bucket", () ->new HorseBucketItem(Fluids.WATER, new Item.Properties().maxStackSize(1).group(ItemGroup.MISC)));

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
