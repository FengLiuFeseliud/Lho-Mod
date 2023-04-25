package fengliu.lho.item.heart;

import fengliu.lho.util.player.IExtendPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Heart extends EmptyHeart {
    public Heart(Settings settings, String id) {
        super(settings, id);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()){
            return super.use(world, user, hand);
        }

        if (!((IExtendPlayer) user).addAddHealth(2)){
            return super.use(world, user, hand);
        }

        user.setHealth(user.getHealth() + 2);
        return super.use(world, user, hand);
    }
}
