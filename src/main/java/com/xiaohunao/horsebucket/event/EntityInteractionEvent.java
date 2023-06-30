package com.xiaohunao.horsebucket.event;

import com.xiaohunao.horsebucket.Item.HorseBucketItem;
import com.xiaohunao.horsebucket.Item.ItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntityInteractionEvent {
    @SubscribeEvent
    public static void onPlayerInteractEntityInteract(PlayerInteractEvent.EntityInteract event) {
        World world = event.getWorld();
        Hand hand = event.getHand();
        if (world.isRemote || hand != Hand.MAIN_HAND) {
            return;
        }
        Entity entity = event.getTarget();
        ItemStack itemStack = event.getPlayer().getHeldItem(hand);
        if (entity instanceof AbstractHorseEntity && itemStack.getItem() == Items.WATER_BUCKET) {
            AbstractHorseEntity horse = (AbstractHorseEntity) entity;

            if (horse.isTame() && !horse.isBeingRidden()) {
                itemStack.shrink(1);
                HorseBucketItem item = (HorseBucketItem)ItemRegistry.HORSE_BUCKET.get();
                ItemStack horseBucket = new ItemStack(item, 1);
                horseBucket.setTag(horse.serializeNBT());
                event.getPlayer().setHeldItem(hand, horseBucket);
                horse.remove();
            }
        }
    }
}
