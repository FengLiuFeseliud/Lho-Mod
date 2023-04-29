package fengliu.notheme.block.heart;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LongForLifeBlock extends WitherBlock {
    public final static IntProperty TICK = IntProperty.of("tick", 0, 21);

    public LongForLifeBlock(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public void livingEntityCollision(BlockState state, World world, BlockPos pos, LivingEntity livingEntity) {
        super.livingEntityCollision(state, world, pos, livingEntity);

        if (state.get(TICK) != 20) {
            world.setBlockState(pos ,state.with(TICK, state.get(TICK)+1));
            return;
        }

        livingEntity.damage(livingEntity.getDamageSources().magic(), 2);
        world.setBlockState(pos ,state.with(TICK, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TICK);
    }
}
