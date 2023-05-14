package fengliu.notheme.item.food.ice.cream;

import fengliu.notheme.util.level.ILevelItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;

public interface IIceCreamLevel extends ILevelItem {

    ItemStack getAllThawItemStack();
    FoodComponent getFoodComponent();
    int getThawTime();
    String getThawName();
}
