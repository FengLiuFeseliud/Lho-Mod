package fengliu.notheme.item.tool;


import fengliu.notheme.util.item.BaseItem;
import net.minecraft.util.DyeColor;

public class EmptySprayGun extends BaseItem {
    public EmptySprayGun(Settings settings, String name) {
        super(settings, name);
    }

    public EmptySprayGun(Settings settings, DyeColor dyeColor, String textureName) {
        super(settings, dyeColor, textureName);
    }
}
