package net.ramixin.soulstones.payloads.clientbound;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;
import net.ramixin.soulstones.SoulStones;

import java.util.UUID;

public record TransferInitiationS2CPayload(UUID figureUUID, BlockPos dispatcherPos) implements CustomPayload {

    public static final CustomPayload.Id<TransferInitiationS2CPayload> PACKET_ID = new CustomPayload.Id<>(SoulStones.id("transfer_initiation"));
    public static final PacketCodec<RegistryByteBuf, TransferInitiationS2CPayload> PACKET_CODEC = PacketCodec.of(TransferInitiationS2CPayload::write, TransferInitiationS2CPayload::new);

    private void write(RegistryByteBuf buf) {
        buf.writeUuid(figureUUID);
        buf.writeBlockPos(dispatcherPos);
    }

    private TransferInitiationS2CPayload(RegistryByteBuf buf) {
        this(buf.readUuid(), buf.readBlockPos());
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
