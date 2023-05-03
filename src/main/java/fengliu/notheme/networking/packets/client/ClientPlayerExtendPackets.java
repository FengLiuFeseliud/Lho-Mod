package fengliu.notheme.networking.packets.client;

import fengliu.notheme.util.IPersistentData;
import fengliu.notheme.util.player.IExtendPlayer;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class ClientPlayerExtendPackets {
    public static void loadNbtData(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        if (player == null) {
            return;
        }

        ((IPersistentData) player).syncData();
    }
}
