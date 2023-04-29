package fengliu.notheme.block.heart;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FrostBlock extends HeartBlock {
    public FrostBlock(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public void livingEntityCollision(BlockState state, World world, BlockPos pos, LivingEntity livingEntity) {
        super.livingEntityCollision(state, world, pos, livingEntity);
    }
}
