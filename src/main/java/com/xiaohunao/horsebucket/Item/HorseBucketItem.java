package com.xiaohunao.horsebucket.Item;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;


import java.util.Optional;

public class HorseBucketItem extends BucketItem {

    @Deprecated
    public HorseBucketItem(Fluid fluid, Item.Properties builder) {
        super(fluid, builder);
    }


    public void onLiquidPlaced(World worldIn, ItemStack stack, BlockPos pos) {
        if (worldIn instanceof ServerWorld) {
            CompoundNBT compoundNBT = stack.getTag();
            if (compoundNBT == null) {
                return;
            }
            compoundNBT.remove("UUID");
            Optional<EntityType<?>> entityType = EntityType.readEntityType(compoundNBT);
            if (entityType.isPresent()) {
                Entity entity = entityType.get().spawn((ServerWorld) worldIn, stack, null, pos, SpawnReason.BUCKET, true, false);
                if (entity != null) {
                    entity.read(compoundNBT);
                    entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
                }
            }
        }

    }
}
