package fengliu.notheme.block.mini.device;

import fengliu.notheme.block.entity.BedrockBreakerBlockEntity;
import fengliu.notheme.block.entity.ModBlockEntitys;
import fengliu.notheme.item.block.ModBlockItems;
import fengliu.notheme.util.SpawnUtil;
import fengliu.notheme.util.block.FacingEntityBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BedrockBreakerBlock extends FacingEntityBlock {
    public static final BooleanProperty USE = BooleanProperty.of("use");
    public static final IntProperty CLICK = IntProperty.of("click", 0, 2);
    public final String name;

    public BedrockBreakerBlock(Settings settings, String name) {
        super(settings);
        this.name = name;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof BedrockBreakerBlockEntity breakerBlockEntity)){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        if (breakerBlockEntity.getDamage() == 1 && breakerBlockEntity.usePlayer == null){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        if (breakerBlockEntity.getUsePlayer() != null){
            breakerBlockEntity.addClick();
            return super.onUse(state, world, pos, player, hand, hit);
        }

        breakerBlockEntity.setDamage(1);
        breakerBlockEntity.setUsePlayer(player);
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof BedrockBreakerBlockEntity breakerBlockEntity)){
            super.onPlaced(world, pos, state, placer, itemStack);
            return;
        }

        breakerBlockEntity.setDamage(itemStack.getDamage());
        if (itemStack.getDamage() == 0){
            world.setBlockState(pos, state.with(USE, false));
        }

        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof BedrockBreakerBlockEntity breakerBlockEntity)){
            super.onBreak(world, pos, state, player);
            return;
        }

        ItemStack stack = new ItemStack(ModBlockItems.BEDROCK_BREAKER_BLOCK_ITEM, 1);
        stack.setDamage(breakerBlockEntity.getDamage());
        SpawnUtil.spawnItemToPos(stack, pos, world);

        super.onBreak(world, pos, state, player);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(USE);
        builder.add(CLICK);
        super.appendProperties(builder);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0, 0, 0, 1, 0.63, 1);
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return ModBlockEntitys.BEDROCK_BREAKER_BLOCK_ENTITY;
    }

    @Override
    public BlockEntityTicker<? super BlockEntity> uesTick() {
        return BedrockBreakerBlockEntity::tick;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BedrockBreakerBlockEntity(pos, state);
    }

    @Override
    public String getBlockName() {
        return this.name;
    }
}
