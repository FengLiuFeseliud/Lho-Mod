package fengliu.notheme.util.block;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

/**
 * 水池方块
 * <p/>
 * getLevelType 会比最大多一, 此为凝固状态
 * getPoolType 会比最大多一, 此为错误状态
 */
public interface IPoolBlock {
    /**
     * 水池方块等级 IntProperty
     * @return IntProperty
     */
    IntProperty getLevelType();

    /**
     * 水池类型 IntProperty
     * @return IntProperty
     */
    IntProperty getPoolType();

    /**
     * 物品对应水池类型
     * @return 物品对应的水池类型
     */
    Map<Item, Integer> getPoolItems();

    /**
     * 获取水池等级
     * @param state 方块状态
     * @return 水池等级
     */
    default int getLevel(BlockState state){
        return state.get(this.getLevelType());
    }

    /**
     * 增加水池等级
     * @param world 世界
     * @param pos 方块坐标
     * @param state 方块状态
     */
    default void addLevel(World world, BlockPos pos, BlockState state){
        if (this.getLevel(state) >= this.getMexLevel()){
            return;
        }

         world.setBlockState(pos, state.with(this.getLevelType(), this.getLevel(state)+1));
    }

    /**
     * 获取水池类型
     * @param state 方块状态
     * @return 水池类型
     */
    default int getType(BlockState state){
        return state.get(this.getPoolType());
    }

    /**
     * 设置水池类型
     * @param state 方块状态
     * @param type 水池类型
     * @return 新方块状态
     */
    default BlockState setType(BlockState state, int type){
        return state.with(this.getPoolType(), type);
    }

    /**
     * 清空水池
     * @param world 世界
     * @param pos 方块坐标
     * @param state 方块状态
     */
    default void clear(World world, BlockPos pos, BlockState state){
        world.setBlockState(pos, this.setType(state, 0).with(this.getLevelType(), 0));
    }

    /**
     * 获取水池最大等级 (不包含凝固状态)
     * @return 水池最大等级
     */
    default int getMexLevel(){
        return this.getLevelType().stream().toList().get(this.getLevelType().getValues().size() - 1).value();
    }

    /**
     * 水池是否为满
     * @param level 水池等级
     * @return 满 true
     */
    default boolean isFull(int level){
        return level >= getMexLevel() - 1;
    }

    /**
     * 向水池存入物品, 并更新水池状态
     * @param state 水池状态
     * @param stack 物品格
     * @param world 世界
     * @param pos 方块坐标
     * @return 成功 true
     */
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
