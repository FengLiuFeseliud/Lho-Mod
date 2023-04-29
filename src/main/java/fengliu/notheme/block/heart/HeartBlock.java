package fengliu.notheme.block.heart;

import fengliu.notheme.util.block.BaseBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public abstract class HeartBlock extends BaseBlock {
    protected static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0);

    public HeartBlock(Settings settings, String name) {
        super(settings, name);
    }

    public void playerCollision(BlockState state, World world, BlockPos pos, PlayerEntity player){

    }

    public void livingEntityCollision(BlockState state, World world, BlockPos pos, LivingEntity livingEntity){

    }

    public void entityCollision(BlockState state, World world, BlockPos pos, Entity entity){

    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world.isClient()){
            super.onEntityCollision(state, world, pos, entity);
            return;
        }

        if (entity.isPlayer()){
            this.playerCollision(state, world, pos, (PlayerEntity) entity);
        }

        if (entity instanceof LivingEntity livingEntity){
            this.livingEntityCollision(state, world, pos, livingEntity);
        }

        this.entityCollision(state, world, pos, entity);
        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.fullCube();
    }
}
