package fengliu.notheme.mixin;

import fengliu.notheme.NoThemeMod;
import fengliu.notheme.util.item.armor.BaseArmorItem;
import fengliu.notheme.util.player.IExtendPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *  应用扩展玩家设置
 */
@Mixin(LivingEntity.class)
public abstract class PlayerAttributeMixin extends Entity {
    @Shadow public abstract Iterable<ItemStack> getArmorItems();

    @Shadow public abstract void remove(RemovalReason reason);

    public PlayerAttributeMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    /**
     * 死亡后清除装备效果
     */
    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onDeath(DamageSource damageSource, CallbackInfo ci){
        if (this.world.isClient() || !this.isPlayer()){
            return;
        }

        if (this.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
            return;
        }

        this.getArmorItems().forEach(stack -> {
            Item item = stack.getItem();
            if (!(item instanceof BaseArmorItem)){
                return;
            }

            ((BaseArmorItem) item).material.remove((IExtendPlayer) this, ((BaseArmorItem) item).getType());
        });
    }

    @Inject(method = "getAttributeValue(Lnet/minecraft/entity/attribute/EntityAttribute;)D", at = @At("RETURN"), cancellable = true)
    public void addNbtHealth(EntityAttribute attribute, CallbackInfoReturnable<Double> cir){
        if (!attribute.equals(EntityAttributes.GENERIC_MAX_HEALTH) || !this.isPlayer()){
            return;
        }

        float deleteHealth = ((IExtendPlayer) this).getDeleteHealth();
        float addHealth = ((IExtendPlayer) this).getAddHealth();
        double maxHealth = cir.getReturnValue();

        double resetMaxHealth = maxHealth - deleteHealth + addHealth;
        if (resetMaxHealth <= 0){
            cir.setReturnValue(0.1D);
            return;
        }

        cir.setReturnValue(resetMaxHealth);
    }

//    @Inject(method = "getMaxHealth", at = @At("RETURN"), cancellable = true)
//    public void nbtSetMaxHealth(CallbackInfoReturnable<Float> info) {
//        if (!this.isPlayer()){
//            return;
//        }
//
//        float deleteHealth = ((IExtendPlayer) this).getDeleteHealth();
//        float addHealth = ((IExtendPlayer) this).getAddHealth();
//        float maxHealth = info.getReturnValue();
//
//        float resetMaxHealth = maxHealth - deleteHealth + addHealth;
//        if (resetMaxHealth <= 0){
//            info.setReturnValue(0.1F);
//            return;
//        }
//
//        info.setReturnValue(resetMaxHealth);
//        info.cancel();
//    }
}
