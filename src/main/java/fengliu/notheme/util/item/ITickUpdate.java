package fengliu.notheme.util.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

/**
 * 将在每 tick 在背包调用物品
 */
public interface ITickUpdate {

    /**
     * 在背包每 tick 调用
     * @param stack 物品
     * @param player 玩家
     */
    void update(ItemStack stack, PlayerEntity player);
}
