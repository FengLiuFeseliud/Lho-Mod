package fengliu.notheme.block.inventory.bottle;

import fengliu.notheme.block.entity.BottleBoxBlockEntity;
import fengliu.notheme.block.inventory.ClothBagBlock;
import fengliu.notheme.item.block.ModBlockItems;
import fengliu.notheme.item.inventory.block.bottle.BottleBox;
import fengliu.notheme.util.block.entity.ItemStackInventoryBlockEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static fengliu.notheme.block.inventory.bottle.BottleBlock.BOTTLE_BLOCK_SHAPE;
import static net.minecraft.item.BlockItem.BLOCK_ENTITY_TAG_KEY;

public class BottleBoxBlock extends ClothBagBlock {
    public static final BooleanProperty CAPPING = BooleanProperty.of("capping");

    public BottleBoxBlock(Settings settings, int size) {
        super(settings, size);
    }

    @Override
    public ItemStack writeInventoryItemStack(BlockState state, ItemStackInventoryBlockEntity be) {
        ItemStack stack = super.writeInventoryItemStack(state, be);
        stack.getOrCreateNbt().putBoolean("capping", state.get(CAPPING));
        return stack;
    }

    @Override
    public boolean canTake(ItemStack stack, BlockState state) {
        return super.canTake(stack, state) && !state.get(CAPPING);
    }

    @Override
    public boolean canSave(ItemStack stack, BlockState state) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (!nbt.contains(BLOCK_ENTITY_TAG_KEY)){
            return true;
        }

        return !nbt.getCompound(BLOCK_ENTITY_TAG_KEY).contains("Items");
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient()){
            return ActionResult.SUCCESS;
        }

        ItemStack stack = player.getStackInHand(hand);
        boolean capping = state.get(CAPPING);

        if (capping){
            if (stack.isOf(Items.SHEARS)){
                world.setBlockState(pos, state.with(CAPPING, false));
                stack.damage(1, Random.create(), (ServerPlayerEntity) player);
            }
            return super.onUse(state, world, pos, player, hand, hit);
        }

        if (stack.isOf(Items.PAPER) && state.get(this.Inventory) == this.getSize()){
            stack.decrement(1);
            world.setBlockState(pos, state.with(CAPPING, true));
            return ActionResult.SUCCESS;
        }

        if (!stack.isOf(ModBlockItems.BOTTLE_BLOCK_ITEM) && !stack.isEmpty()){
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);

        state = world.getBlockState(pos);
        NbtCompound nbt = itemStack.getOrCreateNbt();
        if (!nbt.contains("capping")){
            world.setBlockState(pos, state.with(CAPPING, false));
            return;
        }

        if (!nbt.getBoolean("capping")){
            world.setBlockState(pos, state.with(CAPPING, false));
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CAPPING);
        super.appendProperties(builder);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BottleBoxBlockEntity(pos, state);
    }

    @Override
    public BlockItem getItem() {
        return new BottleBox(this, new FabricItemSettings().maxCount(1).maxDamageIfAbsent(16));
    }

    @Override
    public ItemStack getItemStack() {
        return ModBlockItems.BOTTLE_BOX_BLOCK_ITEM.getDefaultStack();
    }

    @Override
    public IntProperty getInventoryProperty() {
        return IntProperty.of("inventory", 0, 16);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BOTTLE_BLOCK_SHAPE;
    }

    @Override
    public String getBlockName() {
        return "bottle_box";
    }
}
