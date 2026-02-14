package com.jtine.tempered.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class PrimitiveCraftingRecipeSerializer implements RecipeSerializer<PrimitiveCraftingRecipe> {

    @Override
    public PrimitiveCraftingRecipe fromJson(ResourceLocation id, JsonObject json) {
        String group = GsonHelper.getAsString(json, "group", "");

        JsonArray patternArray = GsonHelper.getAsJsonArray(json, "pattern");
        String[] pattern = new String[patternArray.size()];
        for (int i = 0; i < pattern.length; i++) {
            pattern[i] = patternArray.get(i).getAsString();
        }

        int height = pattern.length;
        int width = pattern[0].length();
        if (width > 2) throw new JsonSyntaxException("Pattern width cannot exceed 2");
        if (height > 3) throw new JsonSyntaxException("Pattern height cannot exceed 3");

        Map<Character, Ingredient> keyMap = new HashMap<>();
        JsonObject keyJson = GsonHelper.getAsJsonObject(json, "key");
        for (var entry : keyJson.entrySet()) {
            if (entry.getKey().length() != 1) {
                throw new JsonSyntaxException("Key must be single character: " + entry.getKey());
            }
            char c = entry.getKey().charAt(0);
            if (c == ' ') {
                throw new JsonSyntaxException("Space is reserved for empty slots");
            }
            keyMap.put(c, Ingredient.fromJson(entry.getValue()));
        }

        NonNullList<Ingredient> ingredients =
                NonNullList.withSize(width * height, Ingredient.EMPTY);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                char c = pattern[row].charAt(col);
                if (c == ' ') {
                    ingredients.set(col + row * width, Ingredient.EMPTY);
                } else {
                    Ingredient ing = keyMap.get(c);
                    if (ing == null) {
                        throw new JsonSyntaxException("Unknown key: " + c);
                    }
                    ingredients.set(col + row * width, ing);
                }
            }
        }

        ItemStack result = ShapedRecipe.itemStackFromJson(
                GsonHelper.getAsJsonObject(json, "result"));

        return new PrimitiveCraftingRecipe(id, group, width, height, ingredients, result);
    }

    @Override
    public @Nullable PrimitiveCraftingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
        String group = buf.readUtf();
        int width = buf.readVarInt();
        int height = buf.readVarInt();

        NonNullList<Ingredient> ingredients =
                NonNullList.withSize(width * height, Ingredient.EMPTY);
        for (int i = 0; i < ingredients.size(); i++) {
            ingredients.set(i, Ingredient.fromNetwork(buf));
        }

        ItemStack result = buf.readItem();
        return new PrimitiveCraftingRecipe(id, group, width, height, ingredients, result);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, PrimitiveCraftingRecipe recipe) {
        buf.writeUtf(recipe.getGroup());
        buf.writeVarInt(recipe.getWidth());
        buf.writeVarInt(recipe.getHeight());

        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredient.toNetwork(buf);
        }

        buf.writeItem(recipe.getResult());
    }
}
