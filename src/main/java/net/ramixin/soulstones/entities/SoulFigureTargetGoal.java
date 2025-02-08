package net.ramixin.soulstones.entities;

import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.ramixin.soulstones.entities.soulfigure.SoulFigureEntity;

public class SoulFigureTargetGoal extends ActiveTargetGoal<SoulFigureEntity> {

    public SoulFigureTargetGoal(MobEntity mob, boolean checkVisibility) {
        super(mob, SoulFigureEntity.class, checkVisibility);
    }

    @Override
    public boolean canStart() {
        if(!super.canStart()) return false;
        if(this.targetEntity == null) return false;
        if(!(this.targetEntity instanceof SoulFigureEntity soulFigure)) return false;
        return soulFigure.getPlayerUUID().isPresent();
    }
}
