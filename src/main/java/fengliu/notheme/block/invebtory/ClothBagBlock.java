package fengliu.notheme.block.invebtory;

import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.block.entity.ClothBagBlockEntity;
import fengliu.notheme.item.block.BaseBlockItem;
import fengliu.notheme.item.block.ModBlockItems;
import fengliu.notheme.util.SpawnUtil;
import fengliu.notheme.util.block.IBaseBlock;
import fengliu.notheme.util.block.ItemStackInventoryBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ClothBagBlock extends ItemStackInventoryBlock implements IBaseBlock {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public ClothBagBlock(Settings settings, int size) {
        super(settings, size);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof ClothBagBlockEntity clothBagBlock)){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        ItemStack stack = player.getStackInHand(hand);
        if (player.isSneaking() && stack.isEmpty()){
            player.setStackInHand(hand, clothBagBlock.writeInventoryToItemStack(this.getItemStack()));
            world.removeBlock(pos, false);
            return super.onUse(state, world, pos, player, hand, hit);
        }

        if (stack.isEmpty()){
            SpawnUtil.spawnItemToPos(clothBagBlock.takeItemStack(), pos, world);
        } else if (this.canTake(stack)){
            clothBagBlock.saveItemStack(stack);
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (player.isSneaking()){
            super.onBreak(world, pos, state, player);
            return;
        }

        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof ClothBagBlockEntity clothBagBlock) || world.isClient()){
            return;
        }

        clothBagBlock.dropAllItemStack(pos, world);
        if (player.getStackInHand(player.getActiveHand()).isOf(Items.SHEARS)){
            return;
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.2, 0.2, 0.2, 0.80, 0.9, 0.80);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ClothBagBlockEntity(pos, state);
    }

    @Override
    public BlockItem getItem() {
        return new BaseBlockItem((IBaseBlock) ModBlocks.CLOTH_BAG_BLOCK, new FabricItemSettings().maxCount(1).maxDamageIfAbsent(this.getSize()));
    }

    @Override
    public ItemStack getItemStack() {
        return ModBlockItems.CLOTH_BAG_BLOCK_ITEM.getDefaultStack();
    }

    @Override
    public String getBlockName() {
        return "cloth_bag";
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
