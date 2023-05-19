package fengliu.notheme.item.heart;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class WitherHeart extends Heart {
    public WitherHeart(String id) {
        super(id);
    }

    @Override
    public void useHeart(World world, PlayerEntity user, Hand hand) {
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 60*100, 1));
        super.useHeart(world, user, hand);
    }

}
