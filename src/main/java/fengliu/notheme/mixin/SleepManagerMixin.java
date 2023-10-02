package fengliu.notheme.mixin;


import net.minecraft.server.world.SleepManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SleepManager.class)
public class SleepManagerMixin {

    @Inject(method = "canSkipNight", at = @At("RETURN"), cancellable = true)
    public void canAllTimeSkin(int percentage, CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(true);
        cir.cancel();
    }
}
