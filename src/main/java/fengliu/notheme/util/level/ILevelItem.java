package fengliu.notheme.util.level;

import net.minecraft.item.Item;

/**
 * 等级物品
 */
public interface ILevelItem extends ILevel {
    /**
     * 实例等级物品
     * @return 等级物品
     */
    Item getItem();
}
