package net.ramixin.soulstones.client.mixins;

import net.minecraft.client.gl.ShaderProgramKey;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.ramixin.soulstones.client.ModShaderKeys;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ShaderProgramKeys.class)
public class ShaderProgramKeysMixin {

    @Shadow @Final private static List<ShaderProgramKey> ALL;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void addModShaderKeysOnInit(CallbackInfo ci) {
        ALL.addAll(ModShaderKeys.getModKeys());
    }

}
