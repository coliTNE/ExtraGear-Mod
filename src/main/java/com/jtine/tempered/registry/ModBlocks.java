package com.jtine.tempered.registry;

import com.jtine.tempered.Tempered;
import com.jtine.tempered.block.BranchBlock;
import com.jtine.tempered.block.PebbleBlock;
import com.jtine.tempered.block.PrimitiveCraftingTableBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Tempered.MODID);

    public static final DeferredRegister<Item> BLOCK_ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Tempered.MODID);

    // Pebble: small flat stone on the ground (stone_type 0-2 for visual variants)
    public static final RegistryObject<Block> PEBBLE = BLOCKS.register("pebble_block",
            () -> new PebbleBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .instabreak()
                    .noCollission()
                    .noOcclusion()
                    .sound(SoundType.STONE)
                    .pushReaction(PushReaction.DESTROY)));

    // Branch: flat branch on the ground (wood_type 0-5 for biome-specific variants)
    public static final RegistryObject<Block> BRANCH = BLOCKS.register("branch_block",
            () -> new BranchBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .instabreak()
                    .noCollission()
                    .noOcclusion()
                    .sound(SoundType.WOOD)
                    .pushReaction(PushReaction.DESTROY)));

    // Primitive crafting table: 3x2 crafting grid
    public static final RegistryObject<Block> PRIMITIVE_CRAFTING_TABLE = BLOCKS.register("primitive_crafting_table",
            () -> new PrimitiveCraftingTableBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .strength(2.0f)
                    .sound(SoundType.WOOD)
                    .noOcclusion()));

    // Block items (for creative tab)
    public static final RegistryObject<Item> PRIMITIVE_CRAFTING_TABLE_ITEM = BLOCK_ITEMS.register("primitive_crafting_table",
            () -> new BlockItem(PRIMITIVE_CRAFTING_TABLE.get(), new Item.Properties()));

    public static final RegistryObject<Item> PEBBLE_ITEM = BLOCK_ITEMS.register("pebble_block",
            () -> new BlockItem(PEBBLE.get(), new Item.Properties()));

    public static final RegistryObject<Item> BRANCH_ITEM = BLOCK_ITEMS.register("branch_block",
            () -> new BlockItem(BRANCH.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        BLOCK_ITEMS.register(eventBus);
    }
}
