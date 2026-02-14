package com.jtine.tempered;

import com.jtine.tempered.crafting.ModRecipeTypes;
import com.jtine.tempered.loot.ModLootModifiers;
import com.jtine.tempered.registry.ModBlocks;
import com.jtine.tempered.registry.ModCreativeTabs;
import com.jtine.tempered.registry.ModEntities;
import com.jtine.tempered.registry.ModItems;
import com.jtine.tempered.registry.ModMenuTypes;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Tempered.MODID)
public class Tempered {

    public static final String MODID = "tempered";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Tempered() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModLootModifiers.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipeTypes.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        LOGGER.info("Tempered loaded!");
    }
}
