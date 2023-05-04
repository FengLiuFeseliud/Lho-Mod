package fengliu.notheme.block.entity;

import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.util.IdUtil;
import fengliu.notheme.util.block.ItemStackInventoryBlock;
import fengliu.notheme.util.block.entity.ItemStackInventoryBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class ClothBagBlockEntity extends ItemStackInventoryBlockEntity {
    public ClothBagBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntitys.CLOTH_BAG_BLOCK_ENTITY, pos, state);

        this.setMaxItemStack(((ItemStackInventoryBlock) ModBlocks.CLOTH_BAG_BLOCK).getSize());
    }

    public ItemStack takeItemStack(){
        if (this.isTake()){
            return ItemStack.EMPTY;
        }

        for(ItemStack stack: this.getItems()){
            if (stack.isEmpty()){
                continue;
            }

            this.removeStack(this.getItems().indexOf(stack));
            return stack;
        }
        return ItemStack.EMPTY;
    }

    public void saveItemStack(ItemStack stack){
        if (this.isTake()){
            return;
        }

        int slot = this.getEmptySlot();
        if (slot == -1){
            return;
        }

        this.setStack(slot, stack.copy());
        stack.setCount(0);
    }

    @Override
    public Text getName() {
        return IdUtil.getItemName("cloth_bag");
    }

}
