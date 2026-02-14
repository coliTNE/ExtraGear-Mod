package com.jtine.tempered.registry;

import com.jtine.tempered.Tempered;
import com.jtine.tempered.crafting.PrimitiveCraftingMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Tempered.MODID);

    public static final RegistryObject<MenuType<PrimitiveCraftingMenu>> PRIMITIVE_CRAFTING =
            MENUS.register("primitive_crafting",
                    () -> IForgeMenuType.create(PrimitiveCraftingMenu::new));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
