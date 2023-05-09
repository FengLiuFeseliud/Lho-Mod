package fengliu.notheme.util.item;

import fengliu.notheme.util.block.ItemStackInventoryBlock;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;

import static net.minecraft.item.BlockItem.BLOCK_ENTITY_TAG_KEY;

public interface IItemStackInventoryBlockItem {

   ItemStackInventoryBlock getInventoryBlock();

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

    /**
     * 获取当前使用物品
     * @param stacks 库存
     * @return 物品格
     */
    default ItemStack getUseItemStack(DefaultedList<ItemStack> stacks){
        for(ItemStack stack: stacks){
            if (stack.isEmpty() || stack.getItem() instanceof ToolItem){
                continue;
            }
            return stack;
        }
        return ItemStack.EMPTY;
    }

    default ItemStack getUseItemStack(NbtCompound nbt){
        return this.getUseItemStack(this.getStacks(nbt));
    }
}
