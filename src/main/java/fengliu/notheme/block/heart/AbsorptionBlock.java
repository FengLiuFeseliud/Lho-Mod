package fengliu.notheme.block.heart;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AbsorptionBlock extends HeartBlock {
    public AbsorptionBlock(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public void playerCollision(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (player.getAbsorptionAmount() > 4){
            return;
        }

        player.setAbsorptionAmount(4);
    }
}
