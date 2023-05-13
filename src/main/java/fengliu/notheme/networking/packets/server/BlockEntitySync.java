package fengliu.notheme.networking.packets.server;

import fengliu.notheme.block.entity.VendingMachineBlockEntity;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class BlockEntitySync {

    public static void syncVendingMachine(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender){
        if (buf == null){
            return;
        }

        BlockPos pos = buf.readBlockPos();
        if (!(client.world.getBlockEntity(pos) instanceof VendingMachineBlockEntity vendingMachine)){
            return;
        }

        int size = buf.readInt();
        List<ItemStack> stacks = new ArrayList<>();
        for (int index = 0; index < size; index++){
            stacks.add(buf.readItemStack());
        }

        vendingMachine.setInventory(stacks);
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
