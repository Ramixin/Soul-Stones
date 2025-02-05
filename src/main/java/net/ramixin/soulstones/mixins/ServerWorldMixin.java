package net.ramixin.soulstones.mixins;

import net.minecraft.server.world.ServerWorld;
import net.ramixin.soulstones.SoulStoneManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {

    @Inject(method = "close", at = @At("HEAD"))
    private void resetSoulStoneManagerOnServerWorldClose(CallbackInfo ci) {
        SoulStoneManager.reset();
    }

}
