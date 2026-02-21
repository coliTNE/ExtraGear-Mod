package com.jtine.tempered.loot;

import com.google.common.base.Suppliers;
import com.jtine.tempered.config.TemperedConfig;
import com.jtine.tempered.registry.ModItems;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class GrassDropsFiberModifier extends LootModifier {

    public static final Supplier<Codec<GrassDropsFiberModifier>> CODEC =
            Suppliers.memoize(() -> RecordCodecBuilder.create(inst ->
                    codecStart(inst).apply(inst, GrassDropsFiberModifier::new)));

    public GrassDropsFiberModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot,
                                                          LootContext context) {
        BlockState blockState = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        if (blockState == null) {
            return generatedLoot;
        }

        // Check if the block is grass or fern
        boolean isGrassType = blockState.is(Blocks.GRASS)
                || blockState.is(Blocks.TALL_GRASS)
                || blockState.is(Blocks.FERN)
                || blockState.is(Blocks.LARGE_FERN);

        if (isGrassType && context.getRandom().nextFloat() < TemperedConfig.GRASS_FIBER_DROP_CHANCE.get().floatValue()) {
            generatedLoot.add(new ItemStack(ModItems.PLANT_FIBER.get()));
        }

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
