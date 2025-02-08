package net.ramixin.soulstones.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.ramixin.soulstones.client.entities.ModEntityRenderLayers;
import net.ramixin.soulstones.client.entities.soulfigure.SoulFigureRenderer;
import net.ramixin.soulstones.client.screens.ModScreens;
import net.ramixin.soulstones.entities.ModEntityTypes;
import net.ramixin.soulstones.payloads.clientbound.DispatchersResponseS2CPayload;
import net.ramixin.soulstones.payloads.clientbound.SpawnSoulFigureParticlesS2CPayload;
import net.ramixin.soulstones.payloads.clientbound.TransferInitiationS2CPayload;
import net.ramixin.soulstones.payloads.clientbound.TransferStateChangeS2CPayload;

public class SoulStonesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntityTypes.SOUL_FIGURE, SoulFigureRenderer::new);
        ModEntityRenderLayers.onInitialize();
        ModScreens.onInitialize();

        ClientPlayNetworking.registerGlobalReceiver(DispatchersResponseS2CPayload.PACKET_ID, ClientSoulStoneManager::acceptEntries);
        ClientPlayNetworking.registerGlobalReceiver(TransferStateChangeS2CPayload.PACKET_ID, ClientSoulStoneManager::changeTransferState);
        ClientPlayNetworking.registerGlobalReceiver(SpawnSoulFigureParticlesS2CPayload.PACKET_ID, SoulStonesClient::spawnSoulFigureParticles);
        ClientPlayNetworking.registerGlobalReceiver(TransferInitiationS2CPayload.PACKET_ID, ClientSoulStoneManager::addActiveTransfer);
        ClientTickEvents.START_CLIENT_TICK.register(ClientSoulStoneManager::tick);
    }

    private static void spawnSoulFigureParticles(SpawnSoulFigureParticlesS2CPayload payload, ClientPlayNetworking.Context context) {
        World world = MinecraftClient.getInstance().world;
        if(world == null) throw new IllegalStateException("client world is null, but particles were attempted to be rendered");
        Vec3d center = payload.pos().toCenterPos();
        for(int i = 0; i < 10; ++i) {
            double d = world.random.nextGaussian() * 0.1;
            double e = world.random.nextGaussian() * 0.1;
            double f = world.random.nextGaussian() * 0.1;
            world.addParticle(ParticleTypes.SOUL, center.x - d + (0.9 * world.random.nextDouble() - 0.375) , center.y + (1.5 * world.random.nextDouble() - 0.25) - e, center.z - f + (0.9 * world.random.nextDouble() - 0.375), -d, -e, -f);
        }
        for(int i = 0; i < 32; i++) {
            double x = center.x + (0.9 * world.random.nextDouble() - 0.375);
            double y = center.y + (1.5 * world.random.nextDouble() - 0.25);
            double z = center.z + (0.9 * world.random.nextDouble() - 0.375);
            world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK_CRUMBLE, Blocks.STONE.getDefaultState()), x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }
}
