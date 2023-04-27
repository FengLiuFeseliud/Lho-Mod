package fengliu.notheme.item.block;

import fengliu.notheme.util.IdUtil;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseBlockItem extends BlockItem {
    private final String tooltipKey;

    public BaseBlockItem(Block block, String id) {
        super(block, new FabricItemSettings().maxCount(64));
        this.tooltipKey = IdUtil.getBlockItemTooltip(id);
    }

    public BaseBlockItem(Block block, String id, Settings settings) {
        super(block, settings);
        this.tooltipKey = IdUtil.getBlockItemTooltip(id);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(this.tooltipKey));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
