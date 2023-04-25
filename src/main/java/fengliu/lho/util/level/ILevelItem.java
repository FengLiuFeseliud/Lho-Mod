package fengliu.lho.util.level;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface ILevelItem extends ILevel {
    /**
     * 实例等级物品
     * @return 等级物品
     */
    Item getItem();
}
