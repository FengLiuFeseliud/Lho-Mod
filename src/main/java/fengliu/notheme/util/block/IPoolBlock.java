package fengliu.notheme.util.block;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

public interface IPoolBlock {
    IntProperty getLevelType();
    IntProperty getPoolType();
    Map<Item, Integer> getPoolItems();

    default int getLevel(BlockState state){
        return state.get(this.getLevelType());
    }

    default void addLevel(World world, BlockPos pos, BlockState state){
        if (this.getLevel(state) >= this.getMexLevel()){
            return;
        }

         world.setBlockState(pos, state.with(this.getLevelType(), this.getLevel(state)+1));
    }

    default int getType(BlockState state){
        return state.get(this.getPoolType());
    }

    default BlockState setType(BlockState state, int type){
        return state.with(this.getPoolType(), type);
    }

    default void clear(World world, BlockPos pos, BlockState state){
        world.setBlockState(pos, this.setType(state, 0).with(this.getLevelType(), 0));
    }

    default int getMexLevel(){
        return this.getLevelType().stream().toList().get(this.getLevelType().getValues().size() - 1).value();
    }

    default boolean isFull(int level){
        return level >= getMexLevel() - 1;
    }

    default boolean putItemToPool(BlockState state, ItemStack stack, World world, BlockPos pos){
        int level = state.get(this.getLevelType());
        if (this.isFull(level)){
            return false;
        }

        int poolType = state.get(this.getPoolType());
        if ((poolType != this.getPoolItems().get(stack.getItem()) && poolType != 0) || poolType == this.getPoolItems().size()+1){
            world.setBlockState(pos, state.with(this.getPoolType(),  this.getPoolItems().size()+1).with(this.getLevelType(), level+1));
            return true;
        }

        this.getPoolItems().forEach(((item, integer) -> {
            if (stack.isOf(item)){
                world.setBlockState(pos, state.with(this.getPoolType(), integer).with(this.getLevelType(), level+1));
            }
        }));
        return true;
    }
}
