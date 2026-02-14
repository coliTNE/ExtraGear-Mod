package com.jtine.tempered.loot;

import com.google.common.base.Suppliers;
import com.jtine.tempered.registry.ModItems;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ReplaceStickWithBranchModifier extends LootModifier {

    public static final Supplier<Codec<ReplaceStickWithBranchModifier>> CODEC =
            Suppliers.memoize(() -> RecordCodecBuilder.create(inst ->
                    codecStart(inst).apply(inst, ReplaceStickWithBranchModifier::new)));

    public ReplaceStickWithBranchModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot,
                                                          LootContext context) {
        BlockState blockState = context.getParamOrNull(LootContextParams.BLOCK_STATE);

        // Only affect leaf blocks
        if (blockState == null || !blockState.is(BlockTags.LEAVES)) {
            return generatedLoot;
        }

        // Replace sticks with branches (75%) or strange branches (25%)
        for (int i = 0; i < generatedLoot.size(); i++) {
            if (generatedLoot.get(i).is(Items.STICK)) {
                int count = generatedLoot.get(i).getCount();
                if (context.getRandom().nextFloat() < 0.25f) {
                    generatedLoot.set(i, new ItemStack(ModItems.STRANGE_BRANCH.get(), count));
                } else {
                    generatedLoot.set(i, new ItemStack(ModItems.BRANCH.get(), count));
                }
            }
        }

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
