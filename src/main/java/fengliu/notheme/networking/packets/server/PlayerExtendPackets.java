package fengliu.notheme.networking.packets.server;

import fengliu.notheme.util.IPersistentData;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class PlayerExtendPackets {
    public static void syncData(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        if(client.player == null){
            return;
        }

        ((IPersistentData) client.player).writePersistentData(buf.readNbt());
    }
}
