package net.ramixin.soulstones.client;

import net.minecraft.client.gl.Defines;
import net.minecraft.client.gl.ShaderProgramKey;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.ramixin.soulstones.SoulStones;

import java.util.HashSet;
import java.util.Set;

public class ModShaderKeys {

    private static final Set<ShaderProgramKey> keys = new HashSet<>();

    public static final ShaderProgramKey SOUL_FIGURE = register("rendertype_soul_figure", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL);

    private static ShaderProgramKey register(String id, VertexFormat format) {
        ShaderProgramKey key = new ShaderProgramKey(SoulStones.id("core/"+id), format, Defines.EMPTY);
        keys.add(key);
        return key;
    }

    public static Set<ShaderProgramKey> getModKeys() {
        return keys;
    }
}
