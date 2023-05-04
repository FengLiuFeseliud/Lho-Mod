package fengliu.notheme.util;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SpawnUtil {

    public static void spawnItemToPlayer(ItemStack stack, PlayerEntity player, World world){
        Vec3d pos = player.getPos();
        world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack));
    }

    public static void spawnItemToPos(ItemStack stack, BlockPos pos, World world){
        if (stack.isEmpty()){
            return;
        }

        ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, stack);
        itemEntity.setToDefaultPickupDelay();
        world.spawnEntity(itemEntity);
    }
}
