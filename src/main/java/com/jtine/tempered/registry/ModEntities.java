package com.jtine.tempered.registry;

import com.jtine.tempered.Tempered;
import com.jtine.tempered.entity.PebbleProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Tempered.MODID);

    public static final RegistryObject<EntityType<PebbleProjectile>> PEBBLE_PROJECTILE =
            ENTITIES.register("pebble_projectile",
                    () -> EntityType.Builder.<PebbleProjectile>of(PebbleProjectile::new, MobCategory.MISC)
                            .sized(0.25f, 0.25f)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("pebble_projectile"));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
