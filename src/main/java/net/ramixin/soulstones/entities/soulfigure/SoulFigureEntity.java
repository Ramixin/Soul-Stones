package net.ramixin.soulstones.entities.soulfigure;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.ramixin.soulstones.SoulStones;
import net.ramixin.soulstones.entities.ModEntityTypes;
import net.ramixin.soulstones.payloads.clientbound.SpawnSoulFigureParticlesS2CPayload;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

public class SoulFigureEntity extends LivingEntity {

    protected static final TrackedData<Optional<UUID>> PLAYER_UUID = DataTracker.registerData(SoulFigureEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    protected static final TrackedData<String> TEXTURE = DataTracker.registerData(SoulFigureEntity.class, TrackedDataHandlerRegistry.STRING);


    public SoulFigureEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    public SoulFigureEntity(World world, PlayerEntity player) {
        this(ModEntityTypes.SOUL_FIGURE, world);
        position(player);
    }

    public void assignToPlayer(@NotNull PlayerEntity player) {
        dataTracker.set(PLAYER_UUID, Optional.of(player.getUuid()));

        dataTracker.set(TEXTURE, player.getGameProfile().getProperties().get("textures").iterator().next().value());
    }

    public void position(PlayerEntity player) {
//        this.prevHeadYaw = player.getYaw();
//        this.headYaw = player.getYaw();
        this.prevPitch = player.getPitch();
        this.setPitch(player.getPitch()+180);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(PLAYER_UUID, Optional.empty());
        builder.add(TEXTURE, "");
    }

    public Optional<UUID> getPlayerUUID() {
        return this.dataTracker.get(PLAYER_UUID);
    }

    public Optional<String> getTexture() {
        String texture = this.dataTracker.get(TEXTURE);
        if (texture.isEmpty()) return Optional.empty();
        return Optional.of(texture);
    }

    @Override
    public boolean saveNbt(NbtCompound nbt) {
        this.dataTracker.get(PLAYER_UUID).ifPresent(uuid -> nbt.putUuid("playerUUID", uuid));
        getTexture().ifPresent(texture -> nbt.putString("texture", texture));
        return super.saveNbt(nbt);
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        this.discard();
        world.getPlayers().forEach(player -> {
            if(world.isPlayerInRange(getX(), getY(), getZ(), 32)) ServerPlayNetworking.send(player, new SpawnSoulFigureParticlesS2CPayload(getBlockPos()));
        });
        return true;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if(nbt.contains("playerUUID")) {
            UUID playerUUID = nbt.getUuid("playerUUID");
            if(playerUUID != null) this.dataTracker.set(PLAYER_UUID, Optional.of(playerUUID));
        } else this.dataTracker.set(PLAYER_UUID, Optional.empty());

        if(nbt.contains("texture")) {
            String texture = nbt.getString("texture");
            if(texture != null) this.dataTracker.set(TEXTURE, texture);
        } else this.dataTracker.set(TEXTURE, "");
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        SoulStones.LOGGER.info("attached player: {}", getPlayerUUID());
        return super.interact(player, hand);
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return new Iterable<>() {
            @NotNull
            @Override
            public Iterator<ItemStack> iterator() {
                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return false;
                    }

                    @Override
                    public ItemStack next() {
                        return ItemStack.EMPTY;
                    }
                };
            }
        };
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {

    }

    @Override
    public boolean canBeNameTagged() {
        return false;
    }

    @Override
    protected Text getDefaultName() {
        return Text.empty();
    }

    @Override
    public boolean shouldRenderName() {
        return false;
    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }
}
