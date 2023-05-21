package fengliu.notheme.block.mini.device;

import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.block.entity.CraftingTableSlabBlockEntity;
import fengliu.notheme.block.entity.ModBlockEntitys;
import fengliu.notheme.block.inventory.VendingMachineBlock;
import fengliu.notheme.item.block.BaseBlockItem;
import fengliu.notheme.item.block.ModBlockItems;
import fengliu.notheme.util.block.IBaseBlock;
import fengliu.notheme.util.block.ItemStackInventoryBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
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

public class CraftingTableSlabBlock extends ItemStackInventoryBlock implements IBaseBlock {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final IntProperty CHOOSE = IntProperty.of("choose", 0, 10);
    public static final VoxelShape NORTH_SHAPE = VoxelShapes.cuboid(-0.45, 0, 0, 1, 0.2, 1);
    public static final VoxelShape SOUTH_SHAPE = VoxelShapes.cuboid(0, 0, 0, 1.45, 0.2, 1);
    public static final VoxelShape WEST_SHAPE = VoxelShapes.cuboid(0, 0, 0, 1, 0.2, 1.45);
    public static final VoxelShape EAST_SHAPE = VoxelShapes.cuboid(0, 0, -0.45, 1, 0.2, 1);

    /**
     * 移动库存方块
     *
     * @param settings 方块属性
     * @param size     库存大小
     */
    public CraftingTableSlabBlock(Settings settings, int size) {
        super(settings, size);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!(world.getBlockEntity(pos) instanceof CraftingTableSlabBlockEntity craftingTableSlab) || world.isClient()){
            return ActionResult.SUCCESS;
        }

        ItemStack stack = player.getStackInHand(hand);
        if (stack.isEmpty()){
            if (player.isSneaking()){
                craftingTableSlab.delMaterial();
                return ActionResult.SUCCESS;
            }
            world.setBlockState(pos, state.with(CHOOSE, craftingTableSlab.choose()));
            return ActionResult.SUCCESS;
        }

        craftingTableSlab.setMaterial(stack);
        return ActionResult.SUCCESS;
    }

    @Override
    public String getBlockName() {
        return "crafting_table_slab";
    }

    @Override
    public BlockItem getItem() {
        return new BaseBlockItem((IBaseBlock) ModBlocks.CRAFTING_TABLE_SLAB_BLOCK);
    }

    @Override
    public ItemStack getItemStack() {
        return ModBlockItems.CRAFTING_TABLE_SLAB_BLOCK_ITEM.getDefaultStack();
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntitys.CRAFTING_TABLE_SLAB_BLOCK_ENTITY, CraftingTableSlabBlockEntity::tick);
    }

    @Override
    public IntProperty getInventoryProperty() {
        return null;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(CHOOSE);
        super.appendProperties(builder);
    }

    public static VoxelShape getShape(BlockState state){
        switch (state.get(VendingMachineBlock.FACING)) {
            case NORTH -> {
                return NORTH_SHAPE;
            }
            case SOUTH -> {
                return SOUTH_SHAPE;
            }
            case WEST -> {
                return WEST_SHAPE;
            }
        }

        return EAST_SHAPE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return CraftingTableSlabBlock.getShape(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return CraftingTableSlabBlock.getShape(state);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CraftingTableSlabBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
