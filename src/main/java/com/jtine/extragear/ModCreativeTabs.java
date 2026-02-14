package com.jtine.extragear;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExtraGear.MODID);

    public static final RegistryObject<CreativeModeTab> EXTRA_GEAR_TAB = CREATIVE_TABS.register("extra_gear_tab",
            () -> CreativeModeTab.builder()
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .title(Component.translatable("itemGroup." + ExtraGear.MODID))
                    .icon(() -> ModItems.OBSIDIAN_SWORD.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.OBSIDIAN_SWORD.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_TABS.register(eventBus);
    }
}
