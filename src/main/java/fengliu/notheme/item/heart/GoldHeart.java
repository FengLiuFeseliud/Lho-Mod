package fengliu.notheme.item.heart;

import fengliu.notheme.item.ModItems;
import fengliu.notheme.util.SpawnUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class GoldHeart extends EmptyHeart {
    public GoldHeart(Settings settings, String id) {
        super(settings, id);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()){
            return super.use(world, user, hand);
        }

        user.setAbsorptionAmount(user.getAbsorptionAmount() + 2);
        SpawnUtil.spawnItemToPlayer(new ItemStack(ModItems.EMPTY_HEART, 1), user, world);
        return super.use(world, user, hand);
    }
}
