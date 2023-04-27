package fengliu.notheme.mixin;

import fengliu.notheme.item.ModItems;
import fengliu.notheme.item.heart.drop.HeartDropDevice;
import fengliu.notheme.util.level.LevelsUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
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

        Item item = LevelsUtil.playerInventoryContainsItems(player, ModItems.HEART_DROP_DEVICE);
        if (item == null) {
            item =  LevelsUtil.playerInventoryContainsItems(player, ModItems.HEART_ABSORPTION_DEVICE);
            if (item == null){
                return;
            }
        }

        ((HeartDropDevice) item).dropHeart(this.getPos(), player);
    }
}
