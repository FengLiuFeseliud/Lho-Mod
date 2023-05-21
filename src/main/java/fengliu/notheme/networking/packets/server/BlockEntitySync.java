package fengliu.notheme.networking.packets.server;

import fengliu.notheme.block.entity.VendingMachineBlockEntity;
import fengliu.notheme.util.block.entity.CraftBlockEntity;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public class BlockEntitySync {

    public static void syncInventory(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        if (buf == null){
            return;
        }

        BlockPos pos = buf.readBlockPos();
        if (!(client.world.getBlockEntity(pos) instanceof CraftBlockEntity craft)){
            return;
        }

        craft.syncResetInventory(buf);
    }

    public static void syncVendingMachinePrice(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        if (buf == null){
            return;
        }

        BlockPos pos = buf.readBlockPos();
        if (!(client.world.getBlockEntity(pos) instanceof VendingMachineBlockEntity vendingMachine)){
            return;
        }

        vendingMachine.setPrice(buf.readIntArray());
    }
}
