package fengliu.notheme.block.entity;

import fengliu.notheme.util.IdUtil;
import fengliu.notheme.util.block.ItemStackInventoryBlock;
import fengliu.notheme.util.block.entity.ItemStackInventoryBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class ClothBagBlockEntity extends ItemStackInventoryBlockEntity {
    public ClothBagBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntitys.CLOTH_BAG_BLOCK_ENTITY, pos, state);

        this.setMaxItemStack(((ItemStackInventoryBlock) state.getBlock()).getSize());
    }

    public ItemStack takeItemStack(){
        for(ItemStack stack: this.getItems()){
            if (stack.isEmpty()){
                continue;
            }

            this.removeStack(this.getItems().indexOf(stack));
            return stack;
        }
        return ItemStack.EMPTY;
    }

    public boolean saveItemStack(ItemStack stack, PlayerEntity player){
        int slot = this.getEmptySlot();
        if (slot == -1){
            return false;
        }

        this.setStack(slot, stack.copy());
        if(!player.isCreative()){
            stack.setCount(0);
        }

        return true;
    }

    @Override
    public Text getName() {
        return IdUtil.getItemName("cloth_bag");
    }

}
