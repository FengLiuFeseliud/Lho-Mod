package fengliu.notheme.block.inventory;

import fengliu.notheme.block.entity.VendingMachineBlockEntity;
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
import net.minecraft.entity.LivingEntity;
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

public class VendingMachineBlock extends ItemStackInventoryBlock implements IBaseBlock {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final VoxelShape NORTH_SHAPE = VoxelShapes.cuboid(-0.65, 0, 0, 1, 2, 1);
    public static final VoxelShape SOUTH_SHAPE = VoxelShapes.cuboid(0, 0, 0, 1.65, 2, 1);
    public static final VoxelShape WEST_SHAPE = VoxelShapes.cuboid(0, 0, 0, 1, 2, 1.65);
    public static final VoxelShape EAST_SHAPE = VoxelShapes.cuboid(0, 0, -0.65, 1, 2, 1);
    public static final IntProperty CHOOSE = IntProperty.of("choose", 0, 9);

    /**
     * 移动库存方块
     *
     * @param settings 方块属性
     * @param size     库存大小
     */
    public VendingMachineBlock(Settings settings, int size) {
        super(settings, size);
    }

    public void pick(BlockState state, ItemStack pick, BlockPos pos, World world){
        switch (state.get(FACING).getOpposite()){
            case NORTH -> {
                SpawnUtil.spawnItemToPos(pick, pos.north(-1), world);
            }
            case SOUTH -> {
                SpawnUtil.spawnItemToPos(pick, pos.south(-1), world);
            }
            case WEST -> {
                SpawnUtil.spawnItemToPos(pick, pos.west(-1), world);
            }
            case EAST -> {
                SpawnUtil.spawnItemToPos(pick, pos.east(-1), world);
            }
        }
    }

    public void choose(VendingMachineBlockEntity vendingMachineBlock, BlockState state, BlockPos pos, World world){
        int choose = vendingMachineBlock.choose();
        if (choose == 0){
            choose = vendingMachineBlock.choose();
        }
        world.setBlockState(pos, state.with(CHOOSE, choose));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof VendingMachineBlockEntity vendingMachineBlock)){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        if (world.isClient()){
            return ActionResult.SUCCESS;
        }

        ItemStack stack = player.getStackInHand(hand);
        ItemStack currency = vendingMachineBlock.getItemStack(0, VendingMachineBlockEntity.InventoryAllocation.CURRENCY);
        if (currency.isOf(stack.getItem()) && !stack.isEmpty()){
            if (vendingMachineBlock.isMerchant(player)){
                if (hand == Hand.OFF_HAND){
                    vendingMachineBlock.dropPrice();
                } else {
                    vendingMachineBlock.addPrice();
                }
                return ActionResult.SUCCESS;
            }

            vendingMachineBlock.purchase(stack);
            return ActionResult.SUCCESS;
        }

        if (stack.isEmpty()){
            ItemStack pick = vendingMachineBlock.pickUp();
            if (!pick.isEmpty()){
                this.pick(state, pick, pos, world);
                return ActionResult.SUCCESS;
            }

            if (vendingMachineBlock.isMerchant(player)){
                pick = vendingMachineBlock.pickUpCurrency();
                if (!pick.isEmpty()){
                    this.pick(state, pick, pos, world);
                    return ActionResult.SUCCESS;
                }
            }
        }

        if (!vendingMachineBlock.isMerchant(player)){
            this.choose(vendingMachineBlock, state, pos, world);
            return ActionResult.SUCCESS;
        }

        if (!vendingMachineBlock.saveItem(stack)){
            this.choose(vendingMachineBlock, state, pos, world);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof VendingMachineBlockEntity vendingMachine) || !(placer instanceof PlayerEntity player)){
            return;
        }

        vendingMachine.setMerchant(player);
    }

    @Override
    public String getBlockName() {
        return "vending_machine";
    }

    @Override
    public BlockItem getItem() {
        return new BaseBlockItem(this, new FabricItemSettings().maxCount(1));
    }

    @Override
    public ItemStack getItemStack() {
        return ModBlockItems.VENDING_MACHINE_BLOCK_ITEM.getDefaultStack();
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
        return VendingMachineBlock.getShape(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VendingMachineBlock.getShape(state);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new VendingMachineBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
