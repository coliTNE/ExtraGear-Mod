package com.jtine.tempered.item;

import net.minecraft.world.item.SwordItem;

/**
 * A fast, low-damage melee weapon for the earliest stage of progression.
 * Faster attack speed than a sword but deals less damage.
 */
public class PrimitiveKnifeItem extends SwordItem {

    public PrimitiveKnifeItem(Properties properties) {
        super(ModToolTiers.PRIMITIVE, 2, -1.5f, properties);
        // attackDamage = 2 + tier bonus (0) = 2 total (1 heart)
        // attackSpeed = -1.5 (faster than sword's -2.4)
    }
}
