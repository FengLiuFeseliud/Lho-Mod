package fengliu.notheme.mixin;

import fengliu.notheme.util.item.ITickUpdate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(PlayerEntity.class)
public abstract class PlayerTickMixin extends LivingEntity {
    @Shadow @Final private PlayerInventory inventory;

    protected PlayerTickMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("RETURN"))
    public void tick(CallbackInfo info) {
        this.inventory.main.forEach(stack -> {
            if (stack.getItem() instanceof ITickUpdate tickUpdate){
                tickUpdate.update(stack, (PlayerEntity) (Object) this);
            }
        });
    }
}
