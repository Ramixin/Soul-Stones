package net.ramixin.soulstones.entities;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.ramixin.soulstones.SoulStones;
import net.ramixin.soulstones.entities.soulfigure.SoulFigureEntity;

public class ModEntityTypes {

    public static final EntityType<SoulFigureEntity> SOUL_FIGURE = registerSoulFigureEntity("soul_figure");

    public static EntityType<SoulFigureEntity> registerSoulFigureEntity(String entityId) {
        Identifier entityIdentifier = SoulStones.id(entityId);
        EntityType<SoulFigureEntity> entityType = Registry.register(Registries.ENTITY_TYPE,
                entityIdentifier,
                EntityType.Builder.create((EntityType.EntityFactory<SoulFigureEntity>) SoulFigureEntity::new, SpawnGroup.MISC).dimensions(0.5f,2).build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, entityIdentifier))
        );
        FabricDefaultAttributeRegistry.register(entityType, SoulFigureEntity.createLivingAttributes());
        return entityType;
    }

    public static void onInitialize() { }
}
