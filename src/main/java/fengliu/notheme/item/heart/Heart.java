package fengliu.notheme.item.heart;

import fengliu.notheme.util.player.IExtendPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class Heart extends EmptyHeart {
    public Heart(String id) {
        super(id);
    }

    @Override
    public void useHeart(World world, PlayerEntity user, Hand hand) {
        if (!((IExtendPlayer) user).addAddHealth(2)){
            return;
        }

        user.setHealth(user.getHealth() + 2);
    }
}
