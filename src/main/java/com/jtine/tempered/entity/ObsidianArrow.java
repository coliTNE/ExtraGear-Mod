package com.jtine.tempered.entity;

import com.jtine.tempered.ModEntities;
import com.jtine.tempered.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ObsidianArrow extends AbstractArrow {

    // Daño normal de flecha = 2.0, nosotros +50% = 3.0
    private static final double OBSIDIAN_ARROW_DAMAGE = 3.0;

    public ObsidianArrow(EntityType<? extends ObsidianArrow> type, Level level) {
        super(type, level);
        this.setBaseDamage(OBSIDIAN_ARROW_DAMAGE);
    }

    public ObsidianArrow(Level level, LivingEntity shooter) {
        super(ModEntities.OBSIDIAN_ARROW.get(), shooter, level);
        this.setBaseDamage(OBSIDIAN_ARROW_DAMAGE);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.OBSIDIAN_ARROW.get());
    }
}
