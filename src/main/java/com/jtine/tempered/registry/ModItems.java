package com.jtine.tempered.registry;

import com.jtine.tempered.Tempered;
import com.jtine.tempered.item.ObsidianArrowItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Tempered.MODID);

    public static final RegistryObject<Item> OBSIDIAN_ARROW =
            ITEMS.register("obsidian_arrow", () -> new ObsidianArrowItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
