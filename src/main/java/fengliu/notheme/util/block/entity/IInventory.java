package fengliu.notheme.util.block.entity;

import fengliu.notheme.util.ImplementedInventory;
import fengliu.notheme.util.SpawnUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IInventory extends ImplementedInventory {

    default int getUseSize(){
        int size = 0;
        for(ItemStack stack: this.getItems()){
            if (stack.isEmpty()){
                continue;
            }
            size++;
        }
        return size;
    }

    default int getEmptySlot(){
        for(ItemStack stack: this.getItems()){
            if (!stack.isEmpty()){
                continue;
            }

            return this.getItems().indexOf(stack);
        }

        return -1;
    }

    default void dropAllItemStack(BlockPos pos, World world){
        for (ItemStack stack: this.getItems()){
            SpawnUtil.spawnItemToPos(stack, pos, world);
        }

        this.getItems().clear();
    }
}
