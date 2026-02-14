package com.jtine.tempered.item;

import com.jtine.tempered.entity.ObsidianArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ObsidianArrowItem extends ArrowItem {

    public ObsidianArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
        return new ObsidianArrow(level, shooter);
    }
}
