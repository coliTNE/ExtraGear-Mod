package com.jtine.tempered.client;

import com.jtine.tempered.Tempered;
import com.jtine.tempered.entity.ObsidianArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ObsidianArrowRenderer extends ArrowRenderer<ObsidianArrow> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Tempered.MODID, "textures/entity/obsidian_arrow.png");

    public ObsidianArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(ObsidianArrow entity) {
        return TEXTURE;
    }
}
