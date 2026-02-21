package com.jtine.tempered.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Set;

/**
 * Abstract base for small, flat blocks that sit on the ground surface.
 * - No collision (players walk through it)
 * - Breaks instantly
 * - Can sit on any block with a solid top face (except invalid surfaces)
 * - Drops are defined by loot tables
 *
 * Subclasses define their own block state property for visual variants:
 * - PebbleBlock uses STONE_TYPE (0-2)
 * - BranchBlock uses WOOD_TYPE (0-5)
 */
public abstract class LooseItemBlock extends BushBlock {

    private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 2, 14);

    private static final Set<Block> INVALID_SURFACES = Set.of(
            Blocks.SAND, Blocks.RED_SAND, Blocks.GRAVEL,
            Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW,
            Blocks.ICE, Blocks.PACKED_ICE, Blocks.BLUE_ICE,
            Blocks.SOUL_SAND, Blocks.SOUL_SOIL
    );

    public LooseItemBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        BlockState belowState = level.getBlockState(below);
        if (INVALID_SURFACES.contains(belowState.getBlock())) return false;
        return belowState.isFaceSturdy(level, below, Direction.UP);
    }
}
