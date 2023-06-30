package com.xiaohunao.horsebucket.event;

import com.xiaohunao.horsebucket.HorseBucket;
import com.xiaohunao.horsebucket.Item.ItemRegistry;
import com.xiaohunao.horsebucket.client.gui.HorseInfoGUI;
import com.xiaohunao.horsebucket.client.keybinds.KeybindManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = HorseBucket.MOD_ID)
public class KeybindEvents {
	private static long lastPressTime = 0;

	@SubscribeEvent
	public static void onKeyInput(InputEvent.KeyInputEvent event) {
		if (KeybindManager.showHorseInfo.isPressed()) {
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastPressTime > 100) {
				lastPressTime = currentTime;

				PlayerEntity player = Minecraft.getInstance().player;
				if (player == null) {
					return;
				}
				ItemStack stack = player.getHeldItemMainhand();
				CompoundNBT tag = stack.getTag();
				if (stack.getItem() != ItemRegistry.HORSE_BUCKET.get() && tag == null) {
					return;
				}
				Minecraft.getInstance().displayGuiScreen(new HorseInfoGUI(tag));

			}
		}
	}

}
