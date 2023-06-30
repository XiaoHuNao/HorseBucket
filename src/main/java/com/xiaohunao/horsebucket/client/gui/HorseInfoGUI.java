package com.xiaohunao.horsebucket.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.xiaohunao.horsebucket.HorseBucket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Optional;

public class HorseInfoGUI extends Screen {
    private Minecraft mc = Minecraft.getInstance();
    private int xSize = 128;
    private int ySize = 128;

    private AbstractHorseEntity horse;
    private CompoundNBT horseNBT;

    private float health = 0;
    private float maxHealth = 20;

    private static final ResourceLocation TEXTURE = new ResourceLocation(HorseBucket.MOD_ID, "textures/gui/showhorseinfo.png");

    public HorseInfoGUI(CompoundNBT horseNBT) {
        super(new TranslationTextComponent("horsebucket.gui.horseinfo"));
        this.horseNBT = horseNBT;


    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        if (mc.world ==null){
           return;
        }

        this.renderBackground(stack);

        this.mc.getTextureManager().bindTexture(TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        blit(stack, i, j, 0, 0, this.xSize, this.ySize, 128, 128);

        super.render(stack, mouseX, mouseY, partialTicks);

        Optional<EntityType<?>> entity = EntityType.readEntityType(horseNBT);
        if (entity.isPresent()){
            EntityType<?> entityType = entity.get();
            if (entityType == EntityType.HORSE){
                horse = (AbstractHorseEntity) entityType.create(mc.world);
                if (horse != null){
                    horse.readAdditional(horseNBT);
                    health = horse.getHealth();
                    maxHealth = horse.getMaxHealth();
                }
            }
        }



        InventoryScreen.drawEntityOnScreen(i + 43, j + 93, 15, (float) (i + 51) - mouseX, (float) (j + 75 - 50) - mouseY, horse);


        FontRenderer fontRenderer = mc.fontRenderer;
        float fontScale = 0.5f; // 缩小到原来的一半

        MatrixStack matrixStack = new MatrixStack();
        matrixStack.scale(fontScale, fontScale, fontScale); // 设置缩放比例





        //name
        fontRenderer.drawStringWithShadow(matrixStack, horse.getName().getString(), i / fontScale + 185, j / fontScale + 50, DyeColor.BLACK.getColorValue()); // 在缩放后的坐标上绘制字符串

        //movement_speed
        fontRenderer.drawStringWithShadow(matrixStack, new TranslationTextComponent("gui.horsebucket.movement_speed").getString(), i / fontScale + 170, j / fontScale + 70, DyeColor.LIGHT_GRAY.getColorValue());
        fontRenderer.drawStringWithShadow(matrixStack, getAttribute(horseNBT,"minecraft:generic.movement_speed"), i / fontScale + 210, j / fontScale + 70, DyeColor.WHITE.getColorValue());
        //jump_strength
        fontRenderer.drawStringWithShadow(matrixStack, new TranslationTextComponent("gui.horsebucket.jump_strength").getString(), i / fontScale + 170, j / fontScale + 80, DyeColor.LIGHT_GRAY.getColorValue());
        fontRenderer.drawStringWithShadow(matrixStack, getAttribute(horseNBT,"minecraft:horse.jump_strength"), i / fontScale + 210, j / fontScale + 80, DyeColor.WHITE.getColorValue());
        //max_health
        fontRenderer.drawStringWithShadow(matrixStack, new TranslationTextComponent("gui.horsebucket.health").getString(), i / fontScale + 170, j / fontScale + 90, DyeColor.LIGHT_GRAY.getColorValue());
        fontRenderer.drawStringWithShadow(matrixStack, String.valueOf(health), i / fontScale + 210, j / fontScale + 90, DyeColor.WHITE.getColorValue());


        matrixStack.scale(1 / fontScale, 1 / fontScale, 1 / fontScale); // 恢复缩放比例
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc()
    {
        return true;
    }


    private String getAttribute(CompoundNBT horseNBT, String attributeName){
        if (horseNBT.contains("Attributes")) {
            ListNBT listnbt = horseNBT.getList("Attributes", 10);
            for (int i = 0; i < listnbt.size(); ++i) {
                CompoundNBT compoundnbt = listnbt.getCompound(i);
                if (compoundnbt.getString("Name").equals(attributeName)) {
                    return String.format("%.2f", compoundnbt.getDouble("Base"));
                }
            }
        }
        return "未正确获取";
    }
}
