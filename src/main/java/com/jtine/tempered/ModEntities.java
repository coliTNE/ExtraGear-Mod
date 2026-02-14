package com.jtine.tempered;

import com.jtine.tempered.entity.ObsidianArrow;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Tempered.MODID);

    public static final RegistryObject<EntityType<ObsidianArrow>> OBSIDIAN_ARROW =
            ENTITIES.register("obsidian_arrow",
                    () -> EntityType.Builder.<ObsidianArrow>of(ObsidianArrow::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f)
                            .clientTrackingRange(4)
                            .updateInterval(20)
                            .build("obsidian_arrow"));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
