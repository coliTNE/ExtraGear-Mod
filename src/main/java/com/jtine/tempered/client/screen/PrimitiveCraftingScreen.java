package com.jtine.tempered.client.screen;

import com.jtine.tempered.Tempered;
import com.jtine.tempered.crafting.PrimitiveCraftingMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * GUI screen for the primitive crafting table.
 *
 * Like a React component that renders a form:
 * - renderBg() draws the background image (the "template")
 * - render() is the main render method (calls super which draws slots on top)
 * - renderTooltip() shows item tooltips on hover
 */
public class PrimitiveCraftingScreen extends AbstractContainerScreen<PrimitiveCraftingMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Tempered.MODID, "textures/gui/primitive_crafting.png");

    public PrimitiveCraftingScreen(PrimitiveCraftingMenu menu, Inventory playerInv,
                                    Component title) {
        super(menu, playerInv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick,
                             int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
