package fengliu.notheme.util.item;

import fengliu.notheme.criterion.ModCriteria;
import fengliu.notheme.util.IdUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
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

    public BaseItem(DyeColor dyeColor, String textureName) {
        super(new Settings().maxCount(64));
        this.name = dyeColor.getName() + "_" + textureName;
        this.tooltipKey = IdUtil.getItemTooltip(textureName);
    }

    public BaseItem(Settings settings, DyeColor dyeColor, String textureName) {
        super(settings);
        this.name = dyeColor.getName() + "_" + textureName;
        this.tooltipKey = IdUtil.getItemTooltip(textureName);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()){
            return super.use(world, user, hand);
        }

        ModCriteria.MOD_ITEM_USE.trigger((ServerPlayerEntity) user, user.getBlockPos(), user.getStackInHand(hand));
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(this.tooltipKey));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
