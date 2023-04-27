package fengliu.notheme.mixin;

import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerManager.class)
public abstract class PlayerRespawnSaveNbtMixin {

    @Inject(method = "respawnPlayer", at = @At("RETURN"))
    public void onPlayerRespawn(ServerPlayerEntity player, boolean alive, CallbackInfoReturnable<ServerPlayerEntity> cir) {
//        ((IPersistentData) cir.getReturnValue()).writePersistentData(player.writeNbt(new NbtCompound()));
    }
}
