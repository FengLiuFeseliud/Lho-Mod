package fengliu.notheme.item.food.ice.cream;

import fengliu.notheme.item.ModItems;
import fengliu.notheme.util.level.ILevelItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;

/**
 * 冰淇淋等级
 */
public interface IIceCreamLevel extends ILevelItem {
    /**
     * 冰淇淋食品效果
     * @return 食品效果
     */
    FoodComponent getFoodComponent();
    /**
     * 冰淇淋融化时间
     * @return 融化时间
     */
    int getThawTime();

    /**
     * 完全融化后返回的物品
     * @return 物品
     */
    default ItemStack getAllThawItemStack(){
        return ModItems.BAR.getDefaultStack();
    }

    /**
     * 冰淇淋融化状态 ID
     * @return 状态 ID
     */
    String getThawName();

    @Override
    default String getPath() {
        return this.getIdName() + "_" + this.getThawName();
    }
}
