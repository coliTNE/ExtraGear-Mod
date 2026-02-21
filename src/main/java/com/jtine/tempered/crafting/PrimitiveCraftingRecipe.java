package com.jtine.tempered.crafting;

import com.jtine.tempered.registry.ModBlocks;
import com.jtine.tempered.registry.ModRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

/**
 * Shaped recipe for the 2x3 primitive crafting table.
 *
 * Similar to vanilla ShapedRecipe but constrained to max 2 wide x 3 tall.
 * The pattern slides across the grid to find a match, just like vanilla.
 */
public class PrimitiveCraftingRecipe implements Recipe<CraftingContainer> {

    private final ResourceLocation id;
    private final String group;
    private final int width;
    private final int height;
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;

    public PrimitiveCraftingRecipe(ResourceLocation id, String group,
                                    int width, int height,
                                    NonNullList<Ingredient> ingredients,
                                    ItemStack result) {
        this.id = id;
        this.group = group;
        this.width = width;
        this.height = height;
        this.ingredients = ingredients;
        this.result = result;
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        for (int startX = 0; startX <= container.getWidth() - this.width; startX++) {
            for (int startY = 0; startY <= container.getHeight() - this.height; startY++) {
                if (this.matches(container, startX, startY)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean matches(CraftingContainer container, int startX, int startY) {
        for (int x = 0; x < container.getWidth(); x++) {
            for (int y = 0; y < container.getHeight(); y++) {
                int relX = x - startX;
                int relY = y - startY;

                Ingredient ingredient;
                if (relX >= 0 && relX < this.width && relY >= 0 && relY < this.height) {
                    ingredient = this.ingredients.get(relX + relY * this.width);
                } else {
                    ingredient = Ingredient.EMPTY;
                }

                if (!ingredient.test(container.getItem(x + y * container.getWidth()))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= this.width && height >= this.height;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.PRIMITIVE_CRAFTING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.PRIMITIVE_CRAFTING.get();
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.PRIMITIVE_CRAFTING_TABLE.get());
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public ItemStack getResult() { return result; }
}
