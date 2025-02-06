package net.ramixin.soulstones.payloads.clientbound;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.ramixin.soulstones.SoulStones;
import net.ramixin.soulstones.units.DispatcherResponseEntry;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public record DispatchersResponseS2CPayload(DispatcherResponseEntry[] responseEntries) implements CustomPayload {

    public static final CustomPayload.Id<DispatchersResponseS2CPayload> PACKET_ID = new CustomPayload.Id<>(SoulStones.id("dispatchers_response"));
    public static final PacketCodec<RegistryByteBuf, DispatchersResponseS2CPayload> PACKET_CODEC = PacketCodec.of(DispatchersResponseS2CPayload::write, DispatchersResponseS2CPayload::read);

    private static DispatchersResponseS2CPayload read(RegistryByteBuf buf) {
        DispatcherResponseEntry[] entries = new DispatcherResponseEntry[buf.readInt()];
        for (int i = 0; i < entries.length; i++) entries[i] = DispatcherResponseEntry.PACKET_CODEC.decode(buf);
        return new DispatchersResponseS2CPayload(entries);
    }


    private void write(RegistryByteBuf buf) {
        buf.writeInt(responseEntries.length);
        for (DispatcherResponseEntry entry : responseEntries) DispatcherResponseEntry.PACKET_CODEC.encode(buf, entry);
    }

    public static DispatchersResponseS2CPayload build(HashMap<String, Optional<UUID>> dispatchers) {
        DispatcherResponseEntry[] entries = new DispatcherResponseEntry[dispatchers.size()];
        int i = 0;
        for(Map.Entry<String, Optional<UUID>> entry : dispatchers.entrySet()) entries[i++] = new DispatcherResponseEntry(entry.getKey(), entry.getValue());
        return new DispatchersResponseS2CPayload(entries);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
