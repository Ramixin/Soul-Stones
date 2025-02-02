package net.ramixin.soulstones.client.entities.soulfigure;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.ramixin.soulstones.SoulStones;

public class ModEntityRenderLayers {

    public static final EntityModelLayer DEFAULT_ENTITY_MODEL_LAYER =
            new EntityModelLayer(SoulStones.id("default"), "main");
    public static final EntityModelLayer SLIM_ENTITY_MODEL_LAYER =
            new EntityModelLayer(SoulStones.id("slim"), "main");

    public static void onInitialize() {



        EntityModelLayerRegistry.registerModelLayer(DEFAULT_ENTITY_MODEL_LAYER, () -> SoulFigureModel.getTexturedModelData(false));
        EntityModelLayerRegistry.registerModelLayer(SLIM_ENTITY_MODEL_LAYER, () -> SoulFigureModel.getTexturedModelData(true));
    }


}
