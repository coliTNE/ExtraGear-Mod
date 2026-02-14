package com.jtine.extragear;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ExtraGear.MODID);

    // --- Tu primera arma: una espada de obsidiana ---
    // Daño base 7 (como la espada de diamante=7), velocidad de ataque -2.4
    public static final RegistryObject<Item> OBSIDIAN_SWORD = ITEMS.register("obsidian_sword",
            () -> new SwordItem(Tiers.DIAMOND, 3, -2.4f,
                    new Item.Properties().durability(2048)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
