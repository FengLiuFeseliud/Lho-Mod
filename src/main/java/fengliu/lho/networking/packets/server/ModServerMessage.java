package fengliu.lho.networking.packets.server;

import fengliu.lho.util.IdUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

public class ModServerMessage {
    public static final Identifier SYNC_DATA = IdUtil.get("sync_data");

    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(SYNC_DATA, PlayerExtendPackets::syncData);
    }
}
