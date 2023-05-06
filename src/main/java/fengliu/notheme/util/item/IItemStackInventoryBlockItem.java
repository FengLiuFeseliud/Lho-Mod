package fengliu.notheme.util.item;

import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;

import fengliu.notheme.util.block.ItemStackInventoryBlock;

import static net.minecraft.item.BlockItem.BLOCK_ENTITY_TAG_KEY;

public interface IItemStackInventoryBlockItem {

    public ItemStackInventoryBlock getInventoryBlock();

    /**
     * Nbt 转库存
     * @param nbt nbt
     * @return 库存
     */
    default DefaultedList<ItemStack> getStacks(NbtCompound nbt){
        DefaultedList<ItemStack> stacks = DefaultedList.ofSize(this.getInventoryBlock().getSize(), ItemStack.EMPTY);
        Inventories.readNbt(nbt.getCompound(BLOCK_ENTITY_TAG_KEY), stacks);
        return stacks;
    }
}
