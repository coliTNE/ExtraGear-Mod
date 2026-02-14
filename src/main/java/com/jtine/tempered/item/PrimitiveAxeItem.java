package com.jtine.tempered.item;

import net.minecraft.world.item.AxeItem;

/**
 * A primitive axe for chopping wood. The first real tool the player crafts.
 * Slow but effective against wood blocks.
 */
public class PrimitiveAxeItem extends AxeItem {

    public PrimitiveAxeItem(Properties properties) {
        super(ModToolTiers.PRIMITIVE, 5.0f, -3.2f, properties);
        // attackDamage = 1 (base) + 5.0 + tier bonus (0) = 6.0 total (3 hearts)
        // attackSpeed = -3.2 (slow, like vanilla axe)
        // Mines MINEABLE_WITH_AXE blocks (inherited from AxeItem → DiggerItem)
    }
}
