package net.ramixin.soulstones.client.entities.soulfigure;

import net.minecraft.client.render.entity.state.BipedEntityRenderState;

import java.util.Optional;
import java.util.UUID;

public class SoulFigureEntityRenderState extends BipedEntityRenderState {

    boolean slim = false;
    Optional<UUID> uuid = Optional.empty();
    Optional<String> texture = Optional.empty();
}
