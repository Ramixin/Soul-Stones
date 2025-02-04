package net.ramixin.soulstones.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.ramixin.soulstones.entities.soulfigure.SoulFigureEntity;

public class SoulStatueItem extends Item {

    public SoulStatueItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        World world = context.getWorld();
        if(context.getPlayer() == null) return ActionResult.PASS;
        if(!(world instanceof ServerWorld serverWorld)) return ActionResult.PASS;
        SoulFigureEntity figure = new SoulFigureEntity(world, context.getPlayer());
        figure.setPos(context.getHitPos().getX(), context.getHitPos().getY(), context.getHitPos().getZ());
        serverWorld.spawnEntity(figure);
        return super.useOnBlock(context);
    }
}
