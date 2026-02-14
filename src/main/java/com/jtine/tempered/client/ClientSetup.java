package com.jtine.tempered.client;

import com.jtine.tempered.Tempered;
import com.jtine.tempered.client.renderer.ObsidianArrowRenderer;
import com.jtine.tempered.registry.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Tempered.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.OBSIDIAN_ARROW.get(), ObsidianArrowRenderer::new);
    }
}
