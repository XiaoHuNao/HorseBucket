package com.xiaohunao.horsebucket;


import com.xiaohunao.horsebucket.Item.ItemRegistry;
import com.xiaohunao.horsebucket.client.keybinds.KeybindManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(HorseBucket.MOD_ID)
public class HorseBucket {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "horsebucket";

    public HorseBucket() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemRegistry.register(modEventBus);
        modEventBus.addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }


    public void setup(FMLClientSetupEvent event) {
        KeybindManager.register();
    }


}
