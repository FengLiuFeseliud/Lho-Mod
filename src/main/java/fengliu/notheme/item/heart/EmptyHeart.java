package fengliu.notheme.item.heart;

import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.util.item.BaseItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class EmptyHeart extends BaseItem implements IHeart {
    public EmptyHeart(Settings settings, String id) {
        super(settings, id);
    }

    @Override
    public void useHeart(World world, PlayerEntity user, Hand hand) {}

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()){
            return super.use(world, user, hand);
        }

        BlockState lookState = world.getBlockState(raycast(world, user, RaycastContext.FluidHandling.WATER).getBlockPos());
        if (lookState.isOf(ModBlocks.BLOOD_POOL_BLOCK)){
            return super.use(world, user, hand);
        }

        this.useHeart(world, user, hand);
        user.getStackInHand(hand).decrement(1);
        return super.use(world, user, hand);
    }
}
