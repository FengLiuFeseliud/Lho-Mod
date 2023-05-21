package fengliu.notheme.util.block.entity;

import fengliu.notheme.networking.packets.server.ModServerMessage;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.List;

public abstract class CraftBlockEntity extends ItemStackInventoryBlockEntity {

    public CraftBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    /**
     * 获取指定库存位物品
     * @param slot 物品 slot
     * @param allocation 库存位
     * @return 物品
     */
    public ItemStack getItemStack(int slot, Allocation allocation){
        return allocation.getItemStack(slot, this.getItems());
    }

    /**
     * 获取指定库存位空位
     * @param allocation 库存位
     * @return 库存位 slot
     */
    public int getEmptySlot(Allocation allocation){
        List<ItemStack> stacks = this.getInventory(allocation);
        for (ItemStack stack: stacks){
            if (!stack.isEmpty()){
                continue;
            }

            return stacks.indexOf(stack);
        }

        return -1;
    }

    /**
     * 设置指定库存位物品
     * @param slot slot
     * @param stack 物品
     * @param allocation 库存位
     */
    public void setItemStack(int slot, ItemStack stack, Allocation allocation){
        allocation.setItemStack(slot, stack, this.getItems());
    }

    /**
     * 获取指定库存位库存
     * @param allocation 库存位
     * @return 指定库存位库存
     */
    public List<ItemStack> getInventory(Allocation allocation){
        return allocation.getInventory(this.getItems());
    }

    public void syncResetInventory(PacketByteBuf buf){
        int size = buf.readInt();
        for (int index = 0; index < size; index++){
            this.setStack(index, buf.readItemStack());
        }
    }

    /**
     * 向客户端同步库存
     * @param stacks 库存
     * @param channelName 数据包 ID
     */
    public void syncInventory(List<ItemStack> stacks, Identifier channelName){
        if (this.world.isClient()){
            return;
        }

        PacketByteBuf data = PacketByteBufs.create();
        data.writeBlockPos(this.pos);
        data.writeInt(stacks.size());

        for(ItemStack stack: stacks){
            data.writeItemStack(stack);
        }

        for (ServerPlayerEntity player: PlayerLookup.tracking((ServerWorld) this.world, this.pos)){
            ServerPlayNetworking.send(player, channelName, data);
        }
    }

    /**
     * 同步库存所有物品
     */
    public void syncInventory(){
        this.syncInventory(this.getItems(), ModServerMessage.SYNC_INVENTORY);
    }

    /**
     * 同步所有数据
     */
    public void syncAll(){
        this.syncInventory();
    }

    @Override
    public void markDirty() {
        if (this.world == null){
            super.markDirty();
            return;
        }

        if (!this.world.isClient()){
            this.syncAll();
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.markDirty();
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        this.markDirty();
    }

    /**
     * 库存位
     * <p/>
     *
     * 分割当前库存, 设置不同位置
     */
    public interface Allocation {
        int getSize();
        Allocation[] getAllocations();

        /**
         * 获取当前库存位偏移量
         * @return 偏移量
         */
        default int getOffSet(){
            int offSet = 0;
            List<Allocation> allocations = Arrays.stream(this.getAllocations()).toList();
            for(Allocation allocation: allocations.subList(0, allocations.indexOf(this))){
                offSet += allocation.getSize();
            }
            return offSet;
        }

        /**
         * 获取当前库存位库存
         * @param itemStacks 所有库存
         * @return 当前库存位库存
         */
        default List<ItemStack> getInventory(DefaultedList<ItemStack> itemStacks){
            int offSet = this.getOffSet();
            return itemStacks.subList(offSet, offSet + this.getSize());
        }

        /**
         * 获取当前库存位 slot 所在所有库存的 slot
         * @param slot slot
         * @return 所在所有库存 slot
         */
        default int getSlot(int slot){
            return this.getOffSet() + slot;
        }

        /**
         * 获取当前库存位 slot 物品
         * @param slot slot
         * @param itemStacks 所有库存
         * @return 物品
         */
        default ItemStack getItemStack(int slot, DefaultedList<ItemStack> itemStacks){
            return itemStacks.get(this.getSlot(slot));
        }

        /**
         * 设置当前库存位 slot 物品
         * @param slot slot
         * @param stack 物品
         * @param itemStacks 所有库存
         */
        default void setItemStack(int slot, ItemStack stack, DefaultedList<ItemStack> itemStacks){
            itemStacks.set(this.getSlot(slot), stack);
        }
    }
}
