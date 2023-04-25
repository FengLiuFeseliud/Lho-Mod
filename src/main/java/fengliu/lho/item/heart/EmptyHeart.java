package fengliu.lho.item.heart;

import fengliu.lho.util.item.BaseItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EmptyHeart extends BaseItem {
    public EmptyHeart(Settings settings, String id) {
        super(settings, id);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()){
            return super.use(world, user, hand);
        }

        user.getStackInHand(hand).setCount(0);
        return super.use(world, user, hand);
    }
}
