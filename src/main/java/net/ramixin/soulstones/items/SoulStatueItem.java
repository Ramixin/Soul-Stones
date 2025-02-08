package net.ramixin.soulstones.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.ramixin.soulstones.entities.ModEntityTypes;
import net.ramixin.soulstones.entities.soulfigure.SoulFigureEntity;

public class SoulStatueItem extends Item {

    public SoulStatueItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Direction direction = context.getSide();
        if (direction == Direction.DOWN) return ActionResult.FAIL;
        World world = context.getWorld();
        ItemPlacementContext itemPlacementContext = new ItemPlacementContext(context);
        BlockPos blockPos = itemPlacementContext.getBlockPos();
        ItemStack itemStack = context.getStack();
        Vec3d vec3d = Vec3d.ofBottomCenter(blockPos);
        Box box = ModEntityTypes.SOUL_FIGURE.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ());
        if (!world.isSpaceEmpty(null, box) || !world.getOtherEntities(null, box).isEmpty()) return ActionResult.FAIL;
        if (!(world instanceof ServerWorld serverWorld)) return ActionResult.SUCCESS;
        SoulFigureEntity soulFigure = new SoulFigureEntity(ModEntityTypes.SOUL_FIGURE, world);
        soulFigure.setPos(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
        float f = (MathHelper.wrapDegrees(context.getPlayerYaw() - 180.0F));
        soulFigure.refreshPositionAndAngles(soulFigure.getX(), soulFigure.getY(), soulFigure.getZ(), f, 0.0F);
        serverWorld.spawnEntity(soulFigure);
        world.playSound(null, soulFigure.getX(), soulFigure.getY(), soulFigure.getZ(), SoundEvents.ENTITY_ARMOR_STAND_PLACE, SoundCategory.BLOCKS, 0.75F, 0.8F);
        soulFigure.emitGameEvent(GameEvent.ENTITY_PLACE, context.getPlayer());
        itemStack.decrement(1);
        return ActionResult.SUCCESS;
    }
}
