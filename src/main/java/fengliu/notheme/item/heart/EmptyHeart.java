package fengliu.notheme.item.heart;

import fengliu.notheme.util.item.BaseItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

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
