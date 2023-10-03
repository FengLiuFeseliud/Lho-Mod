package fengliu.notheme.entity;

import fengliu.notheme.util.IdUtil;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEntity {
    public static final EntityType<ThrownItemEntity> COLOR_WATER_BALLOON_ENTITY_TYPE = Registry.register(
            Registries.ENTITY_TYPE,
            IdUtil.get("color_water_balloon"),
            FabricEntityTypeBuilder.<ThrownItemEntity>create(SpawnGroup.MISC, ColorWaterBalloonEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build());

    public static final EntityType<ThrownItemEntity> WATER_BALLOON_ENTITY_TYPE = Registry.register(
            Registries.ENTITY_TYPE,
            IdUtil.get("water_balloon"),
            FabricEntityTypeBuilder.<ThrownItemEntity>create(SpawnGroup.MISC, WaterBalloonEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build());
    public static void registerAllEntity(){}
}
