package com.jtine.tempered.registry;

import com.jtine.tempered.Tempered;
import com.jtine.tempered.crafting.PrimitiveCraftingRecipe;
import com.jtine.tempered.crafting.PrimitiveCraftingRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeTypes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Tempered.MODID);

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Tempered.MODID);

    @SuppressWarnings("unchecked")
    public static final RegistryObject<RecipeType<PrimitiveCraftingRecipe>> PRIMITIVE_CRAFTING =
            RECIPE_TYPES.register("primitive_crafting",
                    () -> (RecipeType<PrimitiveCraftingRecipe>) (RecipeType<?>) new RecipeType<PrimitiveCraftingRecipe>() {
                        @Override
                        public String toString() {
                            return "tempered:primitive_crafting";
                        }
                    });

    public static final RegistryObject<RecipeSerializer<PrimitiveCraftingRecipe>> PRIMITIVE_CRAFTING_SERIALIZER =
            RECIPE_SERIALIZERS.register("primitive_crafting",
                    PrimitiveCraftingRecipeSerializer::new);

    public static void register(IEventBus eventBus) {
        RECIPE_TYPES.register(eventBus);
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
