package fengliu.lho.util;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SpawnUtil {

    public static void spawnItemToPlayer(ItemStack stack, PlayerEntity player, World world){
        Vec3d pos = player.getPos();
        world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack));
    }
}
