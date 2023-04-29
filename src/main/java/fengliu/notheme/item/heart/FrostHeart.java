package fengliu.notheme.item.heart;

import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FrostHeart extends Heart {
    public FrostHeart(Settings settings, String id) {
        super(settings, id);
    }

    @Override
    public void useHeart(World world, PlayerEntity user, Hand hand) {
        BlockPos pos = user.getBlockPos().down();

        world.setBlockState(pos, Blocks.POWDER_SNOW.getDefaultState());
        world.setBlockState(pos.east(), Blocks.POWDER_SNOW.getDefaultState());
        world.setBlockState(pos.north(), Blocks.POWDER_SNOW.getDefaultState());
        world.setBlockState(pos.south(), Blocks.POWDER_SNOW.getDefaultState());
        world.setBlockState(pos.west(), Blocks.POWDER_SNOW.getDefaultState());

        user.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 60*40, 5));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60*40, 5));
        super.useHeart(world, user, hand);
    }
}
