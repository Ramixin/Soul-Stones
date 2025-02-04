package net.ramixin.soulstones.client;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import net.minecraft.util.TriState;
import net.minecraft.util.Util;

import java.util.function.BiFunction;

import static net.minecraft.client.render.RenderPhase.*;

public class ModShaderPrograms {

    public static final RenderPhase.ShaderProgram SOUL_FIGURE_PROGRAM = new RenderPhase.ShaderProgram(ModShaderKeys.SOUL_FIGURE);

    public static final BiFunction<Identifier, Boolean, RenderLayer> SOUL_FIGURE = Util.memoize((texture, affectsOutline) -> {
        RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder().program(SOUL_FIGURE_PROGRAM).texture(new RenderPhase.Texture(texture, TriState.FALSE, false)).transparency(TRANSLUCENT_TRANSPARENCY).cull(DISABLE_CULLING).lightmap(ENABLE_LIGHTMAP).overlay(ENABLE_OVERLAY_COLOR).build(affectsOutline);
        return RenderLayer.of("soul_figure", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 1536, true, true, multiPhaseParameters);
    });

    public static RenderLayer getSoulFigure(Identifier texture) {
        return SOUL_FIGURE.apply(texture, true);
    }
}
