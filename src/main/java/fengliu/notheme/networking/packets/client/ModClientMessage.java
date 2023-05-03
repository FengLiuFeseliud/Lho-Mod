package fengliu.notheme.networking.packets.client;

import fengliu.notheme.networking.packets.server.PlayerExtendPackets;
import fengliu.notheme.util.IdUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModClientMessage {

    public static final Identifier LOAD_NBT_DATA = IdUtil.get("load_nbt_data");

    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(LOAD_NBT_DATA, ClientPlayerExtendPackets::loadNbtData);
    }
}
