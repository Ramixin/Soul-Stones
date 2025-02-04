package net.ramixin.soulstones.client.entities.soulfigure;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.ramixin.soulstones.client.entities.ModEntityRenderLayers;
import net.ramixin.soulstones.entities.soulfigure.SoulFigureEntity;

import java.util.UUID;

public class SoulFigureRenderer extends LivingEntityRenderer<SoulFigureEntity, SoulFigureEntityRenderState, SoulFigureModel>  {

    private final SoulFigureModel defaultModel;
    private final SoulFigureModel slimModel;

    private static final Identifier MISSINGNO = Identifier.of("soulstones:textures/entity/soul_figure_empty.png");
    private static final Identifier EMPTY = Identifier.of("soulstones:textures/entity/soul_figure_empty.png");

    public SoulFigureRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SoulFigureModel(ctx.getPart(ModEntityRenderLayers.DEFAULT_ENTITY_MODEL_LAYER)), 0.5F);
        this.defaultModel = new SoulFigureModel(ctx.getPart(ModEntityRenderLayers.DEFAULT_ENTITY_MODEL_LAYER));
        this.slimModel = new SoulFigureModel(ctx.getPart(ModEntityRenderLayers.SLIM_ENTITY_MODEL_LAYER));
    }

    @Override
    public Identifier getTexture(SoulFigureEntityRenderState state) {
        if(state.uuid.isEmpty()) return EMPTY;
        if(state.texture.isEmpty()) return MISSINGNO;

        GameProfile profile = new GameProfile(state.uuid.get(), "");
        profile.getProperties().put("textures", new Property("textures", state.texture.get()));
        return MinecraftClient.getInstance().getSkinProvider().getSkinTextures(profile).texture();
    }

    @Override
    public SoulFigureEntityRenderState createRenderState() {
        SoulFigureEntityRenderState state = new SoulFigureEntityRenderState();
        state.displayName = null;
        return state;
    }

    @Override
    public void render(SoulFigureEntityRenderState state, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        this.model = state.slim ? this.slimModel : this.defaultModel;
        super.render(state, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public void updateRenderState(SoulFigureEntity entity, SoulFigureEntityRenderState state, float f) {
        UUID uuid = entity.getPlayerUUID().orElse(entity.getUuid());
        SkinTextures profile = MinecraftClient.getInstance().getSkinProvider().getSkinTextures(new GameProfile(uuid, entity.getName().getString()));
        state.slim = profile.model() == SkinTextures.Model.SLIM;
        state.uuid = entity.getPlayerUUID();
        state.texture = entity.getTexture();
        super.updateRenderState(entity, state, f);
        state.limbFrequency = 0;
        state.limbAmplitudeMultiplier = 0;
    }

    @Override
    protected int getMixColor(SoulFigureEntityRenderState state) {
        return 0xFF_00_00_00;
    }

    @Override
    protected void renderLabelIfPresent(SoulFigureEntityRenderState state, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {

    }
}
