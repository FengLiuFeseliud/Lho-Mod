package fengliu.notheme.block.heart;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LifeBlock extends HeartBlock{
    public LifeBlock(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public void livingEntityCollision(BlockState state, World world, BlockPos pos, LivingEntity livingEntity) {
        if (livingEntity.hasStatusEffect(StatusEffects.REGENERATION)){
            return;
        }

        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 120, 1));
    }
}
