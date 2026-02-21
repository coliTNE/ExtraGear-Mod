package com.jtine.tempered.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/**
 * A small pebble block on the ground surface.
 * STONE_TYPE controls the visual variant:
 *   0 = light (diorite), 1 = medium (stone), 2 = dark (andesite), 3 = deepslate
 */
public class PebbleBlock extends LooseItemBlock {

    public static final IntegerProperty STONE_TYPE = IntegerProperty.create("stone_type", 0, 3);

    public PebbleBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(STONE_TYPE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STONE_TYPE);
    }
}
