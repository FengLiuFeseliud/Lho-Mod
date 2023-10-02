package fengliu.notheme.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;


@Mixin(LivingEntity.class)
public abstract class PlayerCanSleepInBlockMixin extends Entity {
    @Shadow public abstract void remove(RemovalReason reason);

    @Unique
    public Optional<BlockPos> getSleepingPosition(){return Optional.empty();};

    public PlayerCanSleepInBlockMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "isSleepingInBed", at = @At("RETURN"), cancellable = true)
    public void canSleepBlock(CallbackInfoReturnable<Boolean> cir){
        if (!this.isPlayer()){
            return;
        }

        if (this.getSleepingPosition().map(pos -> this.world.getBlockState(pos).isAir() && !this.isSpectator()).orElse(false)){
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
