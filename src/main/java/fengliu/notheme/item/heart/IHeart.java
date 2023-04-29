package fengliu.notheme.item.heart;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public interface IHeart {

    void useHeart(World world, PlayerEntity user, Hand hand);
}
