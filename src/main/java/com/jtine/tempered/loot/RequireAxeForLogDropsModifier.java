package com.jtine.tempered.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class RequireAxeForLogDropsModifier extends LootModifier {

    public static final Supplier<Codec<RequireAxeForLogDropsModifier>> CODEC =
            Suppliers.memoize(() -> RecordCodecBuilder.create(inst ->
                    codecStart(inst).apply(inst, RequireAxeForLogDropsModifier::new)));

    public RequireAxeForLogDropsModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot,
                                                          LootContext context) {
        BlockState blockState = context.getParamOrNull(LootContextParams.BLOCK_STATE);

        // Only affect log blocks
        if (blockState == null || !blockState.is(BlockTags.LOGS)) {
            return generatedLoot;
        }

        // Check if the tool used is an axe
        ItemStack tool = context.getParamOrNull(LootContextParams.TOOL);
        if (tool == null || tool.isEmpty() || !(tool.getItem() instanceof AxeItem)) {
            generatedLoot.clear();
        }

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
