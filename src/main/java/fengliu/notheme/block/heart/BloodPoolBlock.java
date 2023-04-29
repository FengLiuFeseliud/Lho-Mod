package fengliu.notheme.block.heart;

import fengliu.notheme.block.entity.BloodPoolBlockEntity;
import fengliu.notheme.block.entity.CustomersBlockEntity;
import fengliu.notheme.block.entity.ModBlockEntitys;
import fengliu.notheme.item.ModItems;
import fengliu.notheme.item.heart.EmptyHeart;
import fengliu.notheme.item.heart.fake.FakeHeart;
import fengliu.notheme.util.block.IPoolBlock;
import fengliu.notheme.util.block.ScreenBlock;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class BloodPoolBlock extends ScreenBlock implements IPoolBlock {
    public static final IntProperty LEVEL = IntProperty.of("level", 0, 10);
    public static final IntProperty HEART_TYPE = IntProperty.of("heart_type", 0, 7);
    public static final Map<Item, Integer> POOL_ITEMS = new LinkedHashMap<>();

    public BloodPoolBlock(Settings settings) {
        super(settings);

        if (!POOL_ITEMS.isEmpty()){
            return;
        }

        POOL_ITEMS.put(ModItems.HEART, 1);
        POOL_ITEMS.put(ModItems.GOLD_HEART, 2);
        POOL_ITEMS.put(ModItems.POISON_HEART, 3);
        POOL_ITEMS.put(ModItems.FROST_HEART, 4);
        POOL_ITEMS.put(ModItems.WITHER_HEART, 5);
        POOL_ITEMS.put(ModItems.ANIMAL_HEART, 6);
    }

    @Override
    public void openHandledScreen(BlockEntity entity, PlayerEntity player) {
        if (entity instanceof BloodPoolBlockEntity){
            player.openHandledScreen((BloodPoolBlockEntity) entity);
        }
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return ModBlockEntitys.BLOOD_POOL_BLOCK_ENTITY;
    }

    @Override
    public BlockEntityTicker<? super BlockEntity> uesTick() {
        return BloodPoolBlockEntity::tick;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        Item item = stack.getItem();
        if (!(item instanceof EmptyHeart) || item instanceof FakeHeart || stack.isOf(ModItems.EMPTY_HEART)){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        if (this.putItemToPool(state, stack, world, pos)){
            return ActionResult.SUCCESS;
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0, 0, 0.0625, 1, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.9375, 0, 0, 1, 1, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0, 0, 0.9375, 1, 0.0625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0, 0.9375, 0.9375, 1, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.9375, 0.0625, 0.9375, 1, 0.9375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0, 0.0625, 0.9375, 0.0625, 0.9375));

        return shape;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
        builder.add(HEART_TYPE);
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BloodPoolBlockEntity(pos, state);
    }

    @Override
    public String getBlockName() {
        return "blood_pool_block";
    }

    @Override
    public IntProperty getLevelType() {
        return LEVEL;
    }

    @Override
    public IntProperty getPoolType() {
        return HEART_TYPE;
    }

    @Override
    public Map<Item, Integer> getPoolItems() {
        return POOL_ITEMS;
    }
}
