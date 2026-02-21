package com.jtine.tempered.entity;

import com.jtine.tempered.config.TemperedConfig;
import com.jtine.tempered.registry.ModEntities;
import com.jtine.tempered.registry.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * Projectile entity for the primitive sling. Behaves like a snowball:
 * renders as the pebble item in flight, deals damage on hit, then disappears.
 * Damage: 4.0 (2 hearts) — enough to kill a chicken in one hit.
 */
public class PebbleProjectile extends ThrowableItemProjectile {

    public PebbleProjectile(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
    }

    public PebbleProjectile(Level level, LivingEntity shooter) {
        super(ModEntities.PEBBLE_PROJECTILE.get(), shooter, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.PEBBLE.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        result.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()),
                TemperedConfig.PEBBLE_PROJECTILE_DAMAGE.get().floatValue());
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide()) {
            if (result.getType() == HitResult.Type.BLOCK) {
                ItemEntity drop = new ItemEntity(this.level(),
                        this.getX(), this.getY(), this.getZ(),
                        new ItemStack(ModItems.PEBBLE.get()));
                drop.setPickUpDelay(10);
                this.level().addFreshEntity(drop);
            }
            this.discard();
        }
    }
}
