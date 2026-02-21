package com.jtine.tempered.crafting;

import com.jtine.tempered.registry.ModBlocks;
import com.jtine.tempered.registry.ModMenuTypes;
import com.jtine.tempered.registry.ModRecipeTypes;
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

    // Slot layout constants
    private static final int RESULT_SLOT = 0;
    private static final int CRAFT_START = 1;
    private static final int CRAFT_END = 7;      // exclusive (6 crafting slots: 2x3)
    private static final int INV_START = 7;       // player inventory (27 slots)
    private static final int INV_END = 34;        // exclusive
    private static final int HOTBAR_START = 34;
    private static final int HOTBAR_END = 43;     // exclusive (9 hotbar slots)

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

            if (index == RESULT_SLOT) {
                // Result slot -> player inventory
                if (!this.moveItemStackTo(rawStack, INV_START, HOTBAR_END, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(rawStack, quickMoved);
            } else if (index >= CRAFT_START && index < CRAFT_END) {
                // Crafting grid -> player inventory
                if (!this.moveItemStackTo(rawStack, INV_START, HOTBAR_END, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < INV_END) {
                // Player inventory -> crafting grid, then hotbar
                if (!this.moveItemStackTo(rawStack, CRAFT_START, CRAFT_END, false)) {
                    if (!this.moveItemStackTo(rawStack, HOTBAR_START, HOTBAR_END, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (index < HOTBAR_END) {
                // Hotbar -> crafting grid, then inventory
                if (!this.moveItemStackTo(rawStack, CRAFT_START, CRAFT_END, false)) {
                    if (!this.moveItemStackTo(rawStack, INV_START, INV_END, false)) {
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
