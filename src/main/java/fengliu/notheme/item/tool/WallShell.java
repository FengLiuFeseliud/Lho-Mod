package fengliu.notheme.item.tool;

import fengliu.notheme.util.color.IColor;
import fengliu.notheme.util.item.BaseItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.util.DyeColor;

public class WallShell extends BaseItem implements IColor {
    private final DyeColor color;

    public WallShell(FabricItemSettings settings, DyeColor dyeColor, String wallShell) {
        super(settings, dyeColor, wallShell);
        this.color = dyeColor;
    }

    @Override
    public DyeColor getColor() {
        return this.color;
    }
}
