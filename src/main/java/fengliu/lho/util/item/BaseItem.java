package fengliu.lho.util.item;

import fengliu.lho.util.IdUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseItem extends Item {
    public final String name;
    public final String tooltipKey;

    public BaseItem(Settings settings, String name) {
        super(settings);
        this.name = name;
        this.tooltipKey = IdUtil.getItemTooltip(name);
    }

    public BaseItem(String name) {
        super(new Settings().maxCount(64));
        this.name = name;
        this.tooltipKey = IdUtil.getItemTooltip(name);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(this.tooltipKey));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
