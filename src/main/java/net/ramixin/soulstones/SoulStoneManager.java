package net.ramixin.soulstones;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.ramixin.soulstones.blocks.ModBlocks;
import net.ramixin.soulstones.entities.soulfigure.SoulFigureEntity;

import java.util.*;

public class SoulStoneManager {

    private static final HashMap<BlockPos, SoulStoneDispatcher> dispatchers = new HashMap<>();


    public static void createDispatcher(World world, BlockPos pos) {
        SoulStoneDispatcher dispatcher = new SoulStoneDispatcher(pos);
        dispatcher.update(world);
        dispatchers.put(pos, dispatcher);
    }

    public static void discardDispatcher(BlockPos pos) {
        dispatchers.remove(pos);
    }

    public static void update(World world) {
        Set<BlockPos> toRemove = new HashSet<>();
        for(BlockPos pos : dispatchers.keySet()) {
            BlockState state = world.getBlockState(pos);
            if(state.getBlock() != ModBlocks.SOUL_STONE) toRemove.add(pos);
            else dispatchers.get(pos).update(world);
        }
        toRemove.forEach(SoulStoneManager::discardDispatcher);
    }

    public static String getDispatcherName(BlockPos pos) {
        SoulStoneDispatcher dispatcher = dispatchers.get(pos);
        return dispatcher.getName();
    }

    public static void reset() {
        dispatchers.clear();
    }

    public static HashMap<BlockPos, Optional<UUID>> gatherViableLocations(UUID playerUUID, World world) {
        update(world);
        HashMap<BlockPos, Optional<UUID>> gathered = new HashMap<>();
        for(BlockPos pos : dispatchers.keySet()) {
            SoulStoneDispatcher dispatcher = dispatchers.get(pos);
            gathered.put(dispatcher.getPos(), dispatcher.getSuitableFigure(playerUUID));
        }
        return gathered;
    }

    public static void save(NbtCompound root) {
        NbtList dispatchersList = new NbtList();
        for(BlockPos pos : dispatchers.keySet()) {
            NbtCompound dispatcherRoot = new NbtCompound();
            dispatchers.get(pos).save(dispatcherRoot);
            dispatchersList.add(dispatcherRoot);
        }
        root.put("dispatchers", dispatchersList);
        SoulStones.LOGGER.info("saving: {}", root);
    }

    public static void load(NbtCompound root) {
        SoulStones.LOGGER.info("loading: {}", root);
        if(!root.contains("dispatchers")) return;
        NbtList dispatchersList = root.getList("dispatchers", NbtElement.COMPOUND_TYPE);
        for(NbtElement dispatcherElement : dispatchersList) {
            if(!(dispatcherElement instanceof NbtCompound compound)) throw new IllegalStateException("Invalid dispatcher compound: " + dispatcherElement);
            SoulStoneDispatcher dispatcher = SoulStoneDispatcher.load(compound);
            dispatchers.put(dispatcher.getPos(), dispatcher);
        }
    }


    private static class SoulStoneDispatcher {

        private final BlockPos pos;
        private String name;
        private final Set<UUID> genericFigures = new HashSet<>();
        private final HashMap<UUID, UUID> assignedFigures = new HashMap<>();

        private SoulStoneDispatcher(BlockPos pos) {
            this.pos = pos;
            this.name = String.format("(%d, %d, %d)", pos.getX(), pos.getY(), pos.getZ());
        }

        private void update(World world) {
            genericFigures.clear();
            assignedFigures.clear();
            List<SoulFigureEntity> figures = world.getEntitiesByType(
                    TypeFilter.instanceOf(SoulFigureEntity.class),
                    Box.from(pos.toCenterPos()).expand(4),
                    soulFigureEntity -> true
            );
            figures.forEach(soulFigureEntity -> {
                Optional<UUID> playerUUID = soulFigureEntity.getPlayerUUID();
                if(playerUUID.isPresent()) assignedFigures.put(playerUUID.get(), soulFigureEntity.getUuid());
                else genericFigures.add(soulFigureEntity.getUuid());
            });
        }

        private void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public BlockPos getPos() {
            return pos;
        }

        private Optional<UUID> getSuitableFigure(UUID playerUUID) {
            if(assignedFigures.containsKey(playerUUID)) return Optional.of(assignedFigures.get(playerUUID));
            for(UUID genericFigure : genericFigures) return Optional.of(genericFigure);
            return Optional.empty();
        }

        private void save(NbtCompound root) {
            root.putString("name", name);
            root.putInt("x", pos.getX());
            root.putInt("y", pos.getY());
            root.putInt("z", pos.getZ());
        }

        private static SoulStoneDispatcher load(NbtCompound root) {
            BlockPos readPos = new BlockPos(root.getInt("x"), root.getInt("y"), root.getInt("z"));
            SoulStoneDispatcher dispatcher =  new SoulStoneDispatcher(readPos);
            dispatcher.setName(root.getString("name"));
            return dispatcher;
        }
    }
}
