package com.jtine.tempered.item;

import com.jtine.tempered.entity.PebbleProjectile;
import com.jtine.tempered.registry.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

/**
 * A primitive ranged weapon that fires pebbles.
 * Mechanics: hold right-click to charge, release to fire.
 * Uses pebble items from inventory as ammo.
 * Max velocity: 1.5 (vs bow's 3.0) — shorter range, lower damage potential.
 */
public class PrimitiveSlingItem extends Item {

    private static final float MAX_VELOCITY = 1.5f;
    private static final int MAX_CHARGE_TICKS = 20;

    public PrimitiveSlingItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack sling = player.getItemInHand(hand);

        if (findPebble(player).isEmpty()) {
            return InteractionResultHolder.fail(sling);
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(sling);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (!(entity instanceof Player player)) return;

        int chargeTime = this.getUseDuration(stack) - timeLeft;
        float power = getPowerForTime(chargeTime);

        if (power < 0.1f) return;

        ItemStack pebble = findPebble(player);
        if (pebble.isEmpty()) return;

        if (!level.isClientSide()) {
            PebbleProjectile projectile = new PebbleProjectile(level, player);
            projectile.shootFromRotation(player, player.getXRot(), player.getYRot(),
                    0.0f, power * MAX_VELOCITY, 1.0f);
            level.addFreshEntity(projectile);
        }

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 1.0f, 0.4f);

        if (!player.getAbilities().instabuild) {
            pebble.shrink(1);
        }

        stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
        player.awardStat(Stats.ITEM_USED.get(this));
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    private static float getPowerForTime(int chargeTime) {
        float f = (float) chargeTime / MAX_CHARGE_TICKS;
        f = (f * f + f * 2.0f) / 3.0f;
        return Math.min(f, 1.0f);
    }

    private static ItemStack findPebble(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.is(ModItems.PEBBLE.get())) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}
