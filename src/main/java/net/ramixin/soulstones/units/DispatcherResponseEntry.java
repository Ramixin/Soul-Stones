package net.ramixin.soulstones.units;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.Uuids;

import java.util.Optional;
import java.util.UUID;

public record DispatcherResponseEntry(String name, Optional<UUID> dispatcherUUID) {

    public static PacketCodec<RegistryByteBuf, DispatcherResponseEntry> PACKET_CODEC = PacketCodec.of(DispatcherResponseEntry::write, DispatcherResponseEntry::new);

    private void write(RegistryByteBuf buf) {
        buf.writeString(name);
        buf.writeOptional(dispatcherUUID, Uuids.PACKET_CODEC);
    }

    private DispatcherResponseEntry(RegistryByteBuf buf) {
        this(buf.readString(), buf.readOptional(Uuids.PACKET_CODEC));
    }
}
