package net.ramixin.soulstones.payloads.clientbound;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.ramixin.soulstones.SoulStones;
import net.ramixin.soulstones.units.State;

public record TransferStateChangeS2CPayload(State state) implements CustomPayload {

    public static final CustomPayload.Id<TransferStateChangeS2CPayload> PACKET_ID = new CustomPayload.Id<>(SoulStones.id("transfer_state_change"));
    public static final PacketCodec<RegistryByteBuf, TransferStateChangeS2CPayload> PACKET_CODEC = PacketCodec.of(TransferStateChangeS2CPayload::write, TransferStateChangeS2CPayload::new);

    private TransferStateChangeS2CPayload(RegistryByteBuf buf) {
        this(State.fromOrdinal(buf.readInt()));
    }


    private void write(RegistryByteBuf buf) {
        buf.writeInt(this.state.ordinal());
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }

}
