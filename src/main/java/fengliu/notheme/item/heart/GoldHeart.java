package fengliu.notheme.item.heart;

import fengliu.notheme.item.ModItems;
import fengliu.notheme.util.SpawnUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class GoldHeart extends EmptyHeart {
    public GoldHeart(String id) {
        super(id);
    }

    @Override
    public void useHeart(World world, PlayerEntity user, Hand hand) {
        user.setAbsorptionAmount(user.getAbsorptionAmount() + 2);
        SpawnUtil.spawnItemToPlayer(new ItemStack(ModItems.EMPTY_HEART, 1), user, world);
        super.useHeart(world, user, hand);
    }
}
