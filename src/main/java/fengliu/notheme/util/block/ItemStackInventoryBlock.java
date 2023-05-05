package fengliu.notheme.util.block;

import fengliu.notheme.util.SpawnUtil;
import fengliu.notheme.util.block.entity.InventoryBlockEntity;
import fengliu.notheme.util.block.entity.ItemStackInventoryBlockEntity;
import net.minecraft.block.Block;
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

public abstract class ItemStackInventoryBlock extends BlockWithEntity {
    protected IntProperty Inventory;
    private final int size;

    protected ItemStackInventoryBlock(Settings settings, int size) {
        super(settings);
        this.size = size;
    }

    public abstract BlockItem getItem();
    public abstract ItemStack getItemStack();
    public abstract IntProperty getInventoryProperty();

    public int getSize(){
        return this.size;
    }

    public boolean canTake(ItemStack stack){
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

        if (inventoryBlockEntity.getItems().stream().allMatch(ItemStack::isEmpty) && player.isCreative()){
            super.onBreak(world, pos, state, player);
            return;
        }

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
