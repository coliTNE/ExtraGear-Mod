package com.jtine.tempered.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * A small, flat block that sits on the ground surface (pebble or branch).
 * - No collision (players walk through it)
 * - Breaks instantly
 * - Can sit on any block with a solid top face
 * - BIOME_TYPE property (0-3) controls visual variant per biome group
 * - Drops are defined by loot tables
 */
public class LooseItemBlock extends BushBlock {

    public static final IntegerProperty BIOME_TYPE = IntegerProperty.create("biome_type", 0, 2);

    private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 2, 14);

    public LooseItemBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(BIOME_TYPE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BIOME_TYPE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        return level.getBlockState(below).isFaceSturdy(level, below, Direction.UP);
    }
}
