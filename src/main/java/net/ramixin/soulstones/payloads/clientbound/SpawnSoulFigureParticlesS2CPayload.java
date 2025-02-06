package net.ramixin.soulstones.payloads.clientbound;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;
import net.ramixin.soulstones.SoulStones;

public record SpawnSoulFigureParticlesS2CPayload(BlockPos pos) implements CustomPayload {

    public static final CustomPayload.Id<SpawnSoulFigureParticlesS2CPayload> PACKET_ID = new CustomPayload.Id<>(SoulStones.id("spawn_soul_figure_particles"));
    public static final PacketCodec<RegistryByteBuf, SpawnSoulFigureParticlesS2CPayload> PACKET_CODEC = PacketCodec.of(SpawnSoulFigureParticlesS2CPayload::write, SpawnSoulFigureParticlesS2CPayload::new);

    private SpawnSoulFigureParticlesS2CPayload(RegistryByteBuf buf) {
        this(buf.readBlockPos());
    }


    private void write(RegistryByteBuf buf) {
        buf.writeBlockPos(pos);
    }
    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
