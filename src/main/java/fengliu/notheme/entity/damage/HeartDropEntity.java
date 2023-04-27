package fengliu.notheme.entity.damage;

import fengliu.notheme.item.ModItems;
import fengliu.notheme.util.level.LevelsUtil;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HeartDropEntity extends ItemEntity {
    public HeartDropEntity(World world, double x, double y, double z, ItemStack stack) {
        super(world, x, y, z, stack);
    }

    public void addPlayerHeart(PlayerEntity player, int heart){
        ItemStack stack = this.getStack();
        player.setHealth(player.getHealth() + heart * stack.getCount());
        stack.setCount(0);
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (player.getHealth() >= player.getMaxHealth()){
            super.onPlayerCollision(player);
            return;
        }

        if (LevelsUtil.playerInventoryContainsItems(player, ModItems.HEART_ABSORPTION_DEVICE) == null){
            super.onPlayerCollision(player);
            return;
        }

        if (this.getStack().isOf(ModItems.HEART_HALF_DROP)){
            this.addPlayerHeart(player, 1);
        }else if (this.getStack().isOf(ModItems.HEART_DROP)){
            this.addPlayerHeart(player, 2);
        }

        this.discard();
    }
}
