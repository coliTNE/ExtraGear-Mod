package com.jtine.tempered.registry;

import com.jtine.tempered.Tempered;
import com.jtine.tempered.config.TemperedConfig;
import com.jtine.tempered.item.PrimitiveAxeItem;
import com.jtine.tempered.item.PrimitiveKnifeItem;
import com.jtine.tempered.item.PrimitiveSlingItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Tempered.MODID);

    // Primitive materials (Fase 1)
    public static final RegistryObject<Item> BRANCH =
            ITEMS.register("branch", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PEBBLE =
            ITEMS.register("pebble", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PLANT_FIBER =
            ITEMS.register("plant_fiber", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LOG_CHUNK =
            ITEMS.register("log_chunk", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STRANGE_BRANCH =
            ITEMS.register("strange_branch", () -> new Item(new Item.Properties()));

    // Primitive tools (Fase 1)
    public static final RegistryObject<Item> PRIMITIVE_KNIFE =
            ITEMS.register("primitive_knife", () -> new PrimitiveKnifeItem(new Item.Properties()));

    public static final RegistryObject<Item> PRIMITIVE_AXE =
            ITEMS.register("primitive_axe", () -> new PrimitiveAxeItem(new Item.Properties()));

    public static final RegistryObject<Item> PRIMITIVE_SLING =
            ITEMS.register("primitive_sling", () -> new PrimitiveSlingItem(
                    new Item.Properties().durability(TemperedConfig.SLING_DURABILITY.get())));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
