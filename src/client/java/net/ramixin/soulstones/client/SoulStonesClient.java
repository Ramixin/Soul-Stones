package net.ramixin.soulstones.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.ramixin.soulstones.client.entities.soulfigure.ModEntityRenderLayers;
import net.ramixin.soulstones.client.entities.soulfigure.SoulFigureRenderer;
import net.ramixin.soulstones.entities.ModEntityTypes;

public class SoulStonesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntityTypes.SOUL_FIGURE, SoulFigureRenderer::new);
        ModEntityRenderLayers.onInitialize();
    }
}
