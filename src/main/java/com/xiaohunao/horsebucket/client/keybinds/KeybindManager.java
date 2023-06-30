package com.xiaohunao.horsebucket.client.keybinds;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class KeybindManager {


    public static KeyBinding showHorseInfo;

    public static void register() {
        showHorseInfo = new KeyBinding("key.horsebucket.showhorseinfo", GLFW.GLFW_KEY_KP_SUBTRACT, "key.horsebucket.category");

        ClientRegistry.registerKeyBinding(showHorseInfo);
    }
}
