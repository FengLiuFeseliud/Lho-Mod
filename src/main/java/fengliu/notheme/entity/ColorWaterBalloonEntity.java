package fengliu.notheme.entity;

import fengliu.notheme.item.ModItems;
import fengliu.notheme.item.tool.Brush;
import fengliu.notheme.util.ShapeUtil;
import fengliu.notheme.util.item.BaseItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ColorWaterBalloonEntity extends ColorItemThrownEntity {
    public static final int HIT_BLOCK_SPRAY_SIZE = 5;
    public static final int HIT_ENTITY_SPRAY_SIZE = 3;

    public ColorWaterBalloonEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public ColorWaterBalloonEntity(PlayerEntity player, World world) {
        super(ModEntity.COLOR_WATER_BALLOON_ENTITY_TYPE, player, world);
    }

    @Override
    public List<BaseItem> getColorItems() {
        return ModItems.COLOR_WATER_BALLOONS;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (this.world.isClient || hitResult.getType() == HitResult.Type.ENTITY){
            return;
        }

        ShapeUtil.rhombus(HIT_BLOCK_SPRAY_SIZE, BlockPos.ofFloored(hitResult.getPos()), this.getMovementDirection(), this.getWorld(),
                pos -> this.getWorld().setBlockState(pos, Brush.sprayBlock(this.getWorld().getBlockState(pos), this.getColor())));
        this.kill();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        ShapeUtil.rhombus(HIT_ENTITY_SPRAY_SIZE, entityHitResult.getEntity().getBlockPos(), this.getMovementDirection(), this.getWorld(),
                pos -> this.getWorld().setBlockState(pos, Brush.sprayBlock(this.getWorld().getBlockState(pos), this.getColor())));
        if (entityHitResult.getEntity() instanceof LivingEntity livingEntity){
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 80, 1), this.getOwner());
        }
        this.kill();
    }
}
