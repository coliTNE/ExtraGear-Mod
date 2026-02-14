package com.jtine.tempered.client;

import com.jtine.tempered.Tempered;
import com.jtine.tempered.client.renderer.ObsidianArrowRenderer;
import com.jtine.tempered.registry.ModEntities;
import com.jtine.tempered.registry.ModItems;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Tempered.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.OBSIDIAN_ARROW.get(), ObsidianArrowRenderer::new);
        event.registerEntityRenderer(ModEntities.PEBBLE_PROJECTILE.get(), ThrownItemRenderer::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // "pulling" = 1.0 when the player is actively charging the sling
            ItemProperties.register(ModItems.PRIMITIVE_SLING.get(),
                    new ResourceLocation("pulling"),
                    (stack, level, entity, seed) ->
                            entity != null && entity.isUsingItem()
                                    && entity.getUseItem() == stack ? 1.0f : 0.0f);

            // "pull" = 0.0-1.0 based on how long the player has been charging
            ItemProperties.register(ModItems.PRIMITIVE_SLING.get(),
                    new ResourceLocation("pull"),
                    (stack, level, entity, seed) -> {
                        if (entity == null || entity.getUseItem() != stack) return 0.0f;
                        return (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0f;
                    });
        });
    }
}
