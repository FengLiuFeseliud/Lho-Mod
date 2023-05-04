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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class ItemStackInventoryBlock extends BlockWithEntity {
    private final int size;

    protected ItemStackInventoryBlock(Settings settings, int size) {
        super(settings);
        this.size = size;
    }

    public abstract BlockItem getItem();
    public abstract ItemStack getItemStack();

    public int getSize(){
        return this.size;
    }

    public boolean canTake(ItemStack stack){
        NbtCompound nbt = stack.getOrCreateNbt();
        if (!nbt.contains("BlockEntityTag", NbtElement.COMPOUND_TYPE)){
            return true;
        }
        return !nbt.getCompound("BlockEntityTag").contains("Items");
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof InventoryBlockEntity inventoryBlockEntity)){
            super.onPlaced(world, pos, state, placer, itemStack);
            return;
        }

        inventoryBlockEntity.readNbt(itemStack.getOrCreateNbt());
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
}
