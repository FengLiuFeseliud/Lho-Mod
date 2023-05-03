package fengliu.notheme.mixin;

import fengliu.notheme.NoThemeMod;
import fengliu.notheme.util.IPersistentData;
import fengliu.notheme.util.player.IExtendPlayer;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerManager.class)
public abstract class PlayerRespawnSaveNbtMixin {

    @Inject(method = "respawnPlayer", at = @At("RETURN"))
    public void onPlayerRespawn(ServerPlayerEntity player, boolean alive, CallbackInfoReturnable<ServerPlayerEntity> cir) {
        ((IPersistentData) cir.getReturnValue()).writePersistentData(player.writeNbt(new NbtCompound()));
        ((IPersistentData) cir.getReturnValue()).syncData();
    }
}
