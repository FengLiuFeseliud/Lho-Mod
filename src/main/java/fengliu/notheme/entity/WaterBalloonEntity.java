package fengliu.notheme.entity;

import fengliu.notheme.item.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static fengliu.notheme.entity.ColorWaterBalloonEntity.HIT_BLOCK_SPRAY_SIZE;
import static fengliu.notheme.entity.ColorWaterBalloonEntity.HIT_ENTITY_SPRAY_SIZE;

public class WaterBalloonEntity extends ThrownItemEntity {
    public WaterBalloonEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public WaterBalloonEntity(PlayerEntity player, World world) {
        super(ModEntity.WATER_BALLOON_ENTITY_TYPE, player, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.WATER_BALLOON;
    }

    public void sprayBlock(BlockPos pos){
        this.world.setBlockState(pos.up(), Blocks.WATER.getDefaultState().with(FluidBlock.LEVEL, 3));
    }

    private void sprayRow(BlockPos pos, int index, Direction ldirection, Direction rdirection){
        BlockPos lpos = pos;
        BlockPos rpos = pos;

        int offset = (index - 1) / 2;
        for(int set_index = 1; set_index <= offset; set_index++){
            lpos = lpos.offset(ldirection);
            this.sprayBlock(lpos);
        }

        for(int set_index = 1; set_index <= offset; set_index++){
            rpos = rpos.offset(rdirection);
            this.sprayBlock(rpos);
        }
    }

    private BlockPos nextRow(BlockPos pos, Direction nextDirection, boolean wallSpaceIn){
        if (!wallSpaceIn){
            return pos.offset(nextDirection);
        }
        return pos.down();
    }

    private void sprayHitBlock(int size, Vec3d pos){
        this.sprayHitBlock(size, new BlockPos((int) pos.getX(), (int) pos.getY(), (int) pos.getZ()));
    }

    private void sprayHitBlock(int size, BlockPos pos){
        Direction[] sprayDirections = new Direction[4];
        if (this.getMovementDirection() == Direction.NORTH || this.getMovementDirection() == Direction.SOUTH){
            sprayDirections = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
        } else if (this.getMovementDirection() == Direction.EAST){
            sprayDirections = new Direction[]{Direction.WEST, Direction.EAST, Direction.SOUTH, Direction.NORTH};
        } else if (this.getMovementDirection() == Direction.WEST){
            sprayDirections = new Direction[]{Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH};
        }

        int soffset = Math.round((float) size / 2);
        boolean wallSpaceIn = false;

        BlockPos spos =  new BlockPos(pos.getX(), pos.getY(), pos.getZ()).down();
        for(int s_index = 0; s_index < soffset; s_index++){
            if (!this.world.getBlockState(spos.offset(sprayDirections[0])).isAir()){
                spos = spos.offset(sprayDirections[0]);
            } else {
                spos = spos.up();
                wallSpaceIn = true;
            }
        }

        for (int index = 1; index <= size; index = index + 2){
            this.sprayBlock(spos);
            if (index == 1){
                spos = this.nextRow(spos, sprayDirections[1], wallSpaceIn);
                continue;
            }

            this.sprayRow(spos, index, sprayDirections[2], sprayDirections[3]);
            spos = this.nextRow(spos, sprayDirections[1], wallSpaceIn);
        }

        for (int index = size - 2; index >= 0; index = index - 2){
            this.sprayBlock(spos);

            this.sprayRow(spos, index, sprayDirections[2], sprayDirections[3]);
            spos = this.nextRow(spos, sprayDirections[1], wallSpaceIn);

            if (index == 1){
                break;
            }
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (this.world.isClient || hitResult.getType() == HitResult.Type.ENTITY){
            return;
        }

        this.sprayHitBlock(HIT_BLOCK_SPRAY_SIZE, (hitResult.getPos()));
        this.kill();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        this.sprayHitBlock(HIT_ENTITY_SPRAY_SIZE, entityHitResult.getEntity().getBlockPos());
        if (entityHitResult.getEntity() instanceof LivingEntity livingEntity){
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 80, 1), this.getOwner());
        }
        this.kill();
    }
}
