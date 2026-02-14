package com.jtine.tempered.crafting;

import com.jtine.tempered.registry.ModBlocks;
import com.jtine.tempered.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Menu (container) for the primitive crafting table.
 *
 * Think of this as a React component's state + event handlers:
 * - The slots are like form inputs (each has a position and validation)
 * - slotsChanged() is the onChange handler
 * - quickMoveStack() handles shift-click (like drag-and-drop logic)
 * - removed() is the cleanup/unmount (drops items when closed)
 */
public class PrimitiveCraftingMenu extends AbstractContainerMenu {

    private final CraftingContainer craftSlots;
    private final ResultContainer resultSlots;
    private final ContainerLevelAccess access;
    private final Player player;

    // Server constructor (from block interaction)
    public PrimitiveCraftingMenu(int containerId, Inventory playerInv,
                                  ContainerLevelAccess access) {
        super(ModMenuTypes.PRIMITIVE_CRAFTING.get(), containerId);
        this.access = access;
        this.player = playerInv.player;

        this.craftSlots = new TransientCraftingContainer(this, 2, 3);
        this.resultSlots = new ResultContainer();

        // Slot 0: result output (vertically centered with 3-row grid)
        this.addSlot(new ResultSlot(playerInv.player, this.craftSlots,
                this.resultSlots, 0, 124, 35));

        // Slots 1-6: crafting grid (2 cols x 3 rows)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 2; col++) {
                this.addSlot(new Slot(this.craftSlots, col + row * 2,
                        30 + col * 18, 17 + row * 18));
            }
        }

        // Slots 7-33: player inventory (3x9)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9,
                        8 + col * 18, 84 + row * 18));
            }
        }

        // Slots 34-42: hotbar (9)
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 142));
        }
    }

    // Client constructor (from network packet)
    public PrimitiveCraftingMenu(int containerId, Inventory playerInv,
                                  FriendlyByteBuf extraData) {
        this(containerId, playerInv, ContainerLevelAccess.NULL);
    }

    @Override
    public void slotsChanged(net.minecraft.world.Container container) {
        this.access.execute((level, pos) -> slotChangedCraftingGrid(level));
    }

    private void slotChangedCraftingGrid(Level level) {
        var recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipeTypes.PRIMITIVE_CRAFTING.get(),
                        this.craftSlots, level);

        if (recipe.isPresent()) {
            ItemStack result = recipe.get().assemble(this.craftSlots,
                    level.registryAccess());
            this.resultSlots.setItem(0, result);
        } else {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        }

        this.broadcastChanges();
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player,
                ModBlocks.PRIMITIVE_CRAFTING_TABLE.get());
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute((level, pos) -> this.clearContainer(player, this.craftSlots));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack quickMoved = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack rawStack = slot.getItem();
            quickMoved = rawStack.copy();

            if (index == 0) {
                // Result slot -> player inventory
                if (!this.moveItemStackTo(rawStack, 7, 43, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(rawStack, quickMoved);
            } else if (index >= 1 && index < 7) {
                // Crafting grid -> player inventory
                if (!this.moveItemStackTo(rawStack, 7, 43, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 34) {
                // Player inventory -> crafting grid, then hotbar
                if (!this.moveItemStackTo(rawStack, 1, 7, false)) {
                    if (!this.moveItemStackTo(rawStack, 34, 43, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (index < 43) {
                // Hotbar -> crafting grid, then inventory
                if (!this.moveItemStackTo(rawStack, 1, 7, false)) {
                    if (!this.moveItemStackTo(rawStack, 7, 34, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (rawStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (rawStack.getCount() == quickMoved.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, rawStack);
        }

        return quickMoved;
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultSlots && super.canTakeItemForPickAll(stack, slot);
    }
}
