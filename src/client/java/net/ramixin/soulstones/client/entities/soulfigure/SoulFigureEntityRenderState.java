package net.ramixin.soulstones.client.entities.soulfigure;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;

import java.util.Optional;
import java.util.UUID;

public class SoulFigureEntityRenderState extends LivingEntityRenderState {

    boolean slim = false;
    Optional<UUID> uuid = Optional.empty();
    Optional<String> texture = Optional.empty();
    float completionProgress = 0.0f;
}
