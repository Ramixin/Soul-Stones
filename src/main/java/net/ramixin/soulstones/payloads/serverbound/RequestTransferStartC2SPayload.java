package net.ramixin.soulstones.payloads.serverbound;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.ramixin.soulstones.SoulStones;

import java.util.UUID;

public record RequestTransferStartC2SPayload(UUID playerUUID, UUID dispatcherUUID) implements CustomPayload {

    public static final CustomPayload.Id<RequestTransferStartC2SPayload> PACKET_ID = new CustomPayload.Id<>(SoulStones.id("request_transfer"));
    public static final PacketCodec<RegistryByteBuf, RequestTransferStartC2SPayload> PACKET_CODEC = PacketCodec.of(RequestTransferStartC2SPayload::write, RequestTransferStartC2SPayload::new);

    private RequestTransferStartC2SPayload(RegistryByteBuf buf) {
        this(buf.readUuid(), buf.readUuid());
    }


    private void write(RegistryByteBuf buf) {
        buf.writeUuid(this.playerUUID);
        buf.writeUuid(this.dispatcherUUID);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
