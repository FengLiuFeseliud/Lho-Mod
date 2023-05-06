package fengliu.notheme.util.block;

import fengliu.notheme.util.SpawnUtil;
import fengliu.notheme.util.block.entity.InventoryBlockEntity;
import fengliu.notheme.util.block.entity.ItemStackInventoryBlockEntity;
import fengliu.notheme.util.item.IItemStackInventoryBlockItem;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.item.BlockItem.BLOCK_ENTITY_TAG_KEY;

/**
 * 移动库存方块
 */
public abstract class ItemStackInventoryBlock extends BlockWithEntity {
    protected IntProperty Inventory;
    private final int size;

    /**
     * 移动库存方块
     * @param settings 方块属性
     * @param size 库存大小
     */
    protected ItemStackInventoryBlock(Settings settings, int size) {
        super(settings);
        this.size = size;
    }

    /**
     * 方块物品
     * @return 物品
     */
    public abstract BlockItem getItem();

    /**
     * 方块物品格
     * @return 物品格
     */
    public abstract ItemStack getItemStack();

    /**
     * 方块库存状态 IntProperty
     * @return IntProperty
     */
    public abstract IntProperty getInventoryProperty();

    /**
     * 方块库存大小
     * @return 库存大小
     */
    public int getSize(){
        return this.size;
    }

    public boolean canTake(ItemStack stack){
        if(stack.getItem() instanceof BlockItem blockItem){
            if(blockItem.getBlock() instanceof ItemStackInventoryBlock || blockItem.getBlock() instanceof ShulkerBoxBlock){
                return false;
            }
        }

        NbtCompound nbt = stack.getOrCreateNbt();
        if (!nbt.contains(BLOCK_ENTITY_TAG_KEY, NbtElement.COMPOUND_TYPE)){
            return true;
        }
        return !nbt.getCompound(BLOCK_ENTITY_TAG_KEY).contains("Items");
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof InventoryBlockEntity inventoryBlockEntity)){
            super.onPlaced(world, pos, state, placer, itemStack);
            return;
        }

        // 从物品格读取库存
        inventoryBlockEntity.readNbt(itemStack.getOrCreateNbt());
        world.setBlockState(pos, state.with(this.Inventory, inventoryBlockEntity.getUseSize()));
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof ItemStackInventoryBlockEntity inventoryBlockEntity)){
            super.onBreak(world, pos, state, player);
            return;
        }

        // 如果库存为空并且创造模式, 直接破坏方块不掉落
        if (inventoryBlockEntity.getItems().stream().allMatch(ItemStack::isEmpty) && player.isCreative()){
            super.onBreak(world, pos, state, player);
            return;
        }

        // 掉落库存
        SpawnUtil.spawnItemToPos(inventoryBlockEntity.writeInventoryToItemStack(this.getItemStack()), pos, world);
        super.onBreak(world, pos, state, player);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        this.Inventory = getInventoryProperty();
        builder.add(this.Inventory);
        super.appendProperties(builder);
    }
}
