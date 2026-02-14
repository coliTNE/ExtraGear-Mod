package com.jtine.tempered.registry;

import com.jtine.tempered.Tempered;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Tempered.MODID);

    public static final RegistryObject<CreativeModeTab> TEMPERED_TAB = CREATIVE_TABS.register("tempered_tab",
            () -> CreativeModeTab.builder()
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .title(Component.translatable("itemGroup." + Tempered.MODID))
                    .icon(() -> ModItems.OBSIDIAN_ARROW.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.OBSIDIAN_ARROW.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_TABS.register(eventBus);
    }
}
