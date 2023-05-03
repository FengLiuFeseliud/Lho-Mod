package fengliu.notheme.mixin;

import fengliu.notheme.NoThemeMod;
import fengliu.notheme.networking.packets.client.ModClientMessage;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientLoadSaveNbtMixin {

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "onGameJoin", at = @At("RETURN"))
    public void getPlayerNbtData(GameJoinS2CPacket packet, CallbackInfo ci){
        ClientPlayNetworking.send(ModClientMessage.LOAD_NBT_DATA, PacketByteBufs.create());
    }
}
