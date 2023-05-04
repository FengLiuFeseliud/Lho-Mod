package fengliu.notheme.mixin;

import fengliu.notheme.NoThemeMod;
import fengliu.notheme.item.ModItems;
import fengliu.notheme.item.heart.drop.HeartDropDevice;
import fengliu.notheme.util.level.LevelsUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class MobHeartDropMixin extends Entity {

    public MobHeartDropMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "dropLoot", at = @At("HEAD"))
    public void dropLoot(DamageSource source, boolean causedByPlayer, CallbackInfo ci){
        if (!(source.getAttacker() instanceof PlayerEntity player)) {
            return;
        }

        if (player.getHealth() >= player.getMaxHealth()) {
            return;
        }

        ItemStack stack = LevelsUtil.playerInventoryContainsItemStacks(player, ModItems.HEART_DROP_DEVICE);
        if (stack == null) {
            stack = LevelsUtil.playerInventoryContainsItemStacks(player, ModItems.HEART_ABSORPTION_DEVICE);
            if (stack == null){
                return;
            }
        }

        ((HeartDropDevice) stack.getItem()).dropHeart(this.getPos(), player, stack);
    }
}
