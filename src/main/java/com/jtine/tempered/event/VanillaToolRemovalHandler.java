package com.jtine.tempered.event;

import com.jtine.tempered.Tempered;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Tempered.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VanillaToolRemovalHandler {

    private static final Set<Item> DISABLED_TOOLS = Set.of(
            Items.WOODEN_SWORD, Items.WOODEN_AXE, Items.WOODEN_PICKAXE,
            Items.WOODEN_SHOVEL, Items.WOODEN_HOE,
            Items.STONE_SWORD, Items.STONE_AXE, Items.STONE_PICKAXE,
            Items.STONE_SHOVEL, Items.STONE_HOE
    );

    @SubscribeEvent
    public static void onBuildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        List<ItemStack> toRemove = new ArrayList<>();
        for (var entry : event.getEntries()) {
            if (DISABLED_TOOLS.contains(entry.getKey().getItem())) {
                toRemove.add(entry.getKey());
            }
        }
        toRemove.forEach(event.getEntries()::remove);
    }
}
