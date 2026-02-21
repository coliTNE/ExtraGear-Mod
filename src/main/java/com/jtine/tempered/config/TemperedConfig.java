package com.jtine.tempered.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class TemperedConfig {

    public static final ForgeConfigSpec SPEC;

    // Drop rates
    public static final ForgeConfigSpec.DoubleValue GRASS_FIBER_DROP_CHANCE;
    public static final ForgeConfigSpec.DoubleValue LEAF_FIBER_DROP_CHANCE;
    public static final ForgeConfigSpec.DoubleValue STRANGE_BRANCH_CHANCE;

    // Tool stats
    public static final ForgeConfigSpec.IntValue PRIMITIVE_TOOL_DURABILITY;
    public static final ForgeConfigSpec.IntValue SLING_DURABILITY;

    // Damage
    public static final ForgeConfigSpec.DoubleValue PEBBLE_PROJECTILE_DAMAGE;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("Tempered Mod Configuration").push("drops");

        GRASS_FIBER_DROP_CHANCE = builder
                .comment("Chance (0.0-1.0) for grass/fern to drop Plant Fiber")
                .defineInRange("grassFiberDropChance", 0.4, 0.0, 1.0);

        LEAF_FIBER_DROP_CHANCE = builder
                .comment("Chance (0.0-1.0) for leaves to drop Plant Fiber")
                .defineInRange("leafFiberDropChance", 0.15, 0.0, 1.0);

        STRANGE_BRANCH_CHANCE = builder
                .comment("Chance (0.0-1.0) for leaf stick drops to be Strange Branch instead of Branch")
                .defineInRange("strangeBranchChance", 0.25, 0.0, 1.0);

        builder.pop().push("tools");

        PRIMITIVE_TOOL_DURABILITY = builder
                .comment("Durability of primitive tools (knife, axe)")
                .defineInRange("primitiveToolDurability", 32, 1, 1000);

        SLING_DURABILITY = builder
                .comment("Durability of the primitive sling")
                .defineInRange("slingDurability", 64, 1, 1000);

        builder.pop().push("combat");

        PEBBLE_PROJECTILE_DAMAGE = builder
                .comment("Damage dealt by pebble projectiles (in half-hearts)")
                .defineInRange("pebbleProjectileDamage", 4.0, 0.0, 100.0);

        builder.pop();

        SPEC = builder.build();
    }
}
