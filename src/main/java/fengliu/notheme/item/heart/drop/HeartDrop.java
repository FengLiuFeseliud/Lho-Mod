package fengliu.notheme.item.heart.drop;

import fengliu.notheme.util.item.BaseItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class HeartDrop extends BaseItem {
    protected int addHealth = 2;
    public HeartDrop(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()){
            return super.use(world, user, hand);
        }

        user.setHealth(user.getHealth() + this.addHealth);
        user.getStackInHand(hand).decrement(1);
        return super.use(world, user, hand);
    }


}
