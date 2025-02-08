package net.ramixin.soulstones.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.ramixin.soulstones.SoulStones;
import net.ramixin.soulstones.payloads.clientbound.DispatchersResponseS2CPayload;
import net.ramixin.soulstones.payloads.clientbound.TransferInitiationS2CPayload;
import net.ramixin.soulstones.payloads.clientbound.TransferStateChangeS2CPayload;
import net.ramixin.soulstones.units.DispatcherResponseEntry;
import net.ramixin.soulstones.units.State;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ClientSoulStoneManager {

    private static @Nullable DispatcherResponseEntry[] storedDispatcherEntries = null;
    private static final Map<UUID, ActiveTransferEntry> activeTransfers = Collections.synchronizedMap(new HashMap<>());


    public static Optional<DispatcherResponseEntry[]> consumeDispatcherResponseEntries() {
        if(storedDispatcherEntries == null) return Optional.empty();
        DispatcherResponseEntry[] result = storedDispatcherEntries;
        storedDispatcherEntries = null;
        return Optional.of(result);
    }

    public static void tick(MinecraftClient unused) {
        for(ActiveTransferEntry entry : activeTransfers.values())
            entry.tick();
    }

    public static void changeTransferState(TransferStateChangeS2CPayload transferStateChangeS2CPayload, ClientPlayNetworking.Context unused) {
        State state = transferStateChangeS2CPayload.state();
        SoulStones.LOGGER.info("state for figure {} was updated to {}", transferStateChangeS2CPayload.figureUUID(), state);
        activeTransfers.remove(transferStateChangeS2CPayload.figureUUID());
    }

    protected static void acceptEntries(DispatchersResponseS2CPayload payload, ClientPlayNetworking.Context unused) {
        storedDispatcherEntries = payload.responseEntries();
    }

    protected static void addActiveTransfer(TransferInitiationS2CPayload payload, ClientPlayNetworking.Context unused) {
        activeTransfers.put(payload.figureUUID(), new ActiveTransferEntry(payload.figureUUID(), payload.dispatcherPos(), 0));
    }

    public static float getCompletionProgress(UUID figureUUID) {
        ActiveTransferEntry entry = activeTransfers.get(figureUUID);
        if(entry == null) return 1f;
        return (100 - entry.transferTicks()) / 100f;
    }
}
