package com.jtine.tempered.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/**
 * A fallen branch block on the ground surface.
 * WOOD_TYPE controls the visual variant matching the biome's dominant tree:
 *   0 = oak, 1 = birch, 2 = spruce, 3 = dark_oak, 4 = acacia, 5 = jungle
 */
public class BranchBlock extends LooseItemBlock {

    public static final IntegerProperty WOOD_TYPE = IntegerProperty.create("wood_type", 0, 5);

    public BranchBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WOOD_TYPE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WOOD_TYPE);
    }
}
