package fengliu.notheme.networking.packets.server;

import fengliu.notheme.util.IdUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

public class ModServerMessage {
    public static final Identifier SYNC_DATA = IdUtil.get("sync_data");
    public static final Identifier SYNC_INVENTORY = IdUtil.get("sync_inventory");
    public static final Identifier SYNC_VENDING_MACHINE_PRICE = IdUtil.get("sync_vending_machine_price");

    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(SYNC_DATA, PlayerExtendPackets::syncData);
        ClientPlayNetworking.registerGlobalReceiver(SYNC_INVENTORY, BlockEntitySync::syncInventory);
        ClientPlayNetworking.registerGlobalReceiver(SYNC_VENDING_MACHINE_PRICE, BlockEntitySync::syncVendingMachinePrice);
    }
}
