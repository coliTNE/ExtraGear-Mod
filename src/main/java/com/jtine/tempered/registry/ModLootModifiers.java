package com.jtine.tempered.registry;

import com.mojang.serialization.Codec;
import com.jtine.tempered.Tempered;
import com.jtine.tempered.loot.GrassDropsFiberModifier;
import com.jtine.tempered.loot.LeafDropsFiberModifier;
import com.jtine.tempered.loot.ReplaceStickWithBranchModifier;
import com.jtine.tempered.loot.RequireAxeForLogDropsModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Tempered.MODID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> REQUIRE_AXE_FOR_LOGS =
            LOOT_MODIFIER_SERIALIZERS.register("require_axe_for_logs",
                    RequireAxeForLogDropsModifier.CODEC);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> REPLACE_STICK_WITH_BRANCH =
            LOOT_MODIFIER_SERIALIZERS.register("replace_stick_with_branch",
                    ReplaceStickWithBranchModifier.CODEC);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> GRASS_DROPS_FIBER =
            LOOT_MODIFIER_SERIALIZERS.register("grass_drops_fiber",
                    GrassDropsFiberModifier.CODEC);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> LEAF_DROPS_FIBER =
            LOOT_MODIFIER_SERIALIZERS.register("leaf_drops_fiber",
                    LeafDropsFiberModifier.CODEC);

    public static void register(IEventBus eventBus) {
        LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }
}
