package com.jtine.tempered.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Primitive crafting table — a 3x2 crafting grid.
 * Works like vanilla crafting table: no BlockEntity, transient inventory.
 * The menu is opened via SimpleMenuProvider (wired once PrimitiveCraftingMenu exists).
 */
public class PrimitiveCraftingTableBlock extends Block {

    public static final Component CONTAINER_TITLE =
            Component.translatable("container.tempered.primitive_crafting");

    public PrimitiveCraftingTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                  Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        player.openMenu(getMenuProvider(state, level, pos));
        return InteractionResult.CONSUME;
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new net.minecraft.world.SimpleMenuProvider(
                (containerId, playerInv, p) ->
                        new com.jtine.tempered.crafting.PrimitiveCraftingMenu(
                                containerId, playerInv,
                                net.minecraft.world.inventory.ContainerLevelAccess.create(level, pos)),
                CONTAINER_TITLE);
    }
}
