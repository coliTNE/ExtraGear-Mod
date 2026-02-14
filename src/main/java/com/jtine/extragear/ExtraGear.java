package com.jtine.extragear;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ExtraGear.MODID)
public class ExtraGear {

    public static final String MODID = "extragear";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ExtraGear() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Registrar items y bloques
        ModItems.register(modEventBus);
        ModCreativeTabs.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        LOGGER.info("Extra Gear loaded!");
    }
}
