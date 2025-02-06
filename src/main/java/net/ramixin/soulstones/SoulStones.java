package net.ramixin.soulstones;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.ramixin.soulstones.blocks.ModBlocks;
import net.ramixin.soulstones.entities.ModEntityTypes;
import net.ramixin.soulstones.items.ModItems;
import net.ramixin.soulstones.payloads.clientbound.DispatchersResponseS2CPayload;
import net.ramixin.soulstones.payloads.clientbound.SpawnSoulFigureParticlesS2CPayload;
import net.ramixin.soulstones.payloads.clientbound.TransferStateChangeS2CPayload;
import net.ramixin.soulstones.payloads.serverbound.RequestTransferStartC2SPayload;
import net.ramixin.soulstones.screenhandlers.ModScreenHandlers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoulStones implements ModInitializer {

    public static final String MOD_ID = "soulstones";

    public static final Logger LOGGER = LoggerFactory.getLogger("SoulStones");

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing SoulStones (1/1)");
        ModBlocks.onInitialize();
        ModEntityTypes.onInitialize();
        ModItems.onInitialize();
        ModScreenHandlers.onInitialize();

        PayloadTypeRegistry.playS2C().register(DispatchersResponseS2CPayload.PACKET_ID, DispatchersResponseS2CPayload.PACKET_CODEC);
        PayloadTypeRegistry.playS2C().register(TransferStateChangeS2CPayload.PACKET_ID, TransferStateChangeS2CPayload.PACKET_CODEC);
        PayloadTypeRegistry.playS2C().register(SpawnSoulFigureParticlesS2CPayload.PACKET_ID, SpawnSoulFigureParticlesS2CPayload.PACKET_CODEC);

        PayloadTypeRegistry.playC2S().register(RequestTransferStartC2SPayload.PACKET_ID, RequestTransferStartC2SPayload.PACKET_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(RequestTransferStartC2SPayload.PACKET_ID, (payload, context) -> {
            LOGGER.info("Received dispatch request from {} to {}", payload.playerUUID(), payload.dispatcherUUID());
        });
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
