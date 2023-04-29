package fengliu.notheme.block.heart;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WitherBlock extends HeartBlock{
    public WitherBlock(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public void livingEntityCollision(BlockState state, World world, BlockPos pos, LivingEntity livingEntity) {
        if (livingEntity.hasStatusEffect(StatusEffects.WITHER)){
            return;
        }

        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 120, 1));
    }

}
