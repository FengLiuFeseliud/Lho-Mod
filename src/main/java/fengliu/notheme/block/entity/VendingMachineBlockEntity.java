package fengliu.notheme.block.entity;

import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.networking.packets.server.ModServerMessage;
import fengliu.notheme.util.block.ItemStackInventoryBlock;
import fengliu.notheme.util.block.entity.CraftBlockEntity;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class VendingMachineBlockEntity extends CraftBlockEntity {
    /**
     * 商家 UUID
     */
    private UUID merchantUuid;
    /**
     * 选中商品
     */
    private int choose = 0;
    /**
     * 商品价格
     */
    private int[] prices = new int[9];

    public VendingMachineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntitys.VENDING_MACHINE_BLOCK_ENTITY, pos, state);

        this.setMaxItemStack(((ItemStackInventoryBlock) ModBlocks.VENDING_MACHINE_BLOCK).getSize());
    }

    /**
     * 是否为商品
     * @param allocation 库存分配
     * @return 是 true
     */
    public boolean isSlot(Allocation allocation){
        return !(allocation == InventoryAllocation.CURRENCY || allocation == InventoryAllocation.CLAIM || allocation == InventoryAllocation.CURRENCY_CLAIM);
    }

    /**
     * 是否为商家
     * @param player 玩家
     * @return 是 true
     */
    public boolean isMerchant(PlayerEntity player){
        if (this.merchantUuid == null){
            return false;
        }

        return this.merchantUuid.equals(player.getUuid());
    }

    /**
     * 设置商家
     * @param player 玩家
     */
    public void setMerchant(PlayerEntity player){
        if (this.merchantUuid != null){
            return;
        }

        this.merchantUuid = player.getUuid();
    }

    /**
     * 获取商品位
     * @param allocation 库存位
     * @return 商品 slot
     */
    public int getCommoditySlot(Allocation allocation){
        List<ItemStack> stacks = this.getInventory(allocation);
        for(int index = stacks.size() - 1; index > -1; index--){
            ItemStack stack = stacks.get(index);
            if (stack.isEmpty()){
                continue;
            }
            return index;
        }

        return -1;
    }

    /**
     * 获取商品
     * @param allocation 库存分配
     * @return 商品
     */
    public ItemStack getCommodityItemStack(Allocation allocation){
        if (this.choose == 0){
            return ItemStack.EMPTY;
        }

        int slot = this.getCommoditySlot(allocation);
        if (slot == -1){
            return this.getItemStack(0, allocation);
        }

        return this.getItemStack(slot, allocation);
    }

    public int getCanSaveCount(ItemStack stack){
        return stack.getMaxCount() - stack.getCount();
    }

    /**
     * 可以存入货币
     * @param price 商品价格
     * @return 可以 true
     */
    public boolean canSaveCurrency(int price){
        if (this.getCanSaveCount(this.getCommodityItemStack(InventoryAllocation.CURRENCY_CLAIM)) >= price){
            return true;
        }

        return this.getEmptySlot(InventoryAllocation.CURRENCY_CLAIM) != -1;
    }

    /**
     * 存入货币
     * @param stack 货币
     * @param price 商品价格
     * @return 成功 true
     */
    public boolean saveCurrency(ItemStack stack, int price){
        if (!this.canSaveCurrency(price)){
            return false;
        }

        stack.decrement(price);
        ItemStack currencyStack = this.getCommodityItemStack(InventoryAllocation.CURRENCY_CLAIM);
        int canSaveCount = this.getCanSaveCount(currencyStack);

        Item currency = this.getItemStack(0, InventoryAllocation.CURRENCY).getItem();
        int currencySlot = this.getCommoditySlot(InventoryAllocation.CURRENCY_CLAIM);
        if (currencySlot == -1){
            currencySlot = 0;
        }

        int surplusCurrency = canSaveCount - price;
        if (surplusCurrency >= 0){
            if (currencyStack.isEmpty()){
                this.setItemStack(currencySlot, new ItemStack(currency, price), InventoryAllocation.CURRENCY_CLAIM);
            } else {
                currencyStack.setCount(currencyStack.getCount() + price);
            }
            return true;
        }

        if (currencyStack.getCount() < currencyStack.getMaxCount()){
            if (currencyStack.isEmpty()){
                this.setItemStack(currencySlot, new ItemStack(currency, canSaveCount), InventoryAllocation.CURRENCY_CLAIM);
                return true;
            } else {
                currencyStack.setCount(currencyStack.getCount() + canSaveCount);
            }
        }

        this.setItemStack(this.getEmptySlot(InventoryAllocation.CURRENCY_CLAIM), new ItemStack(currency, Math.abs(surplusCurrency)), InventoryAllocation.CURRENCY_CLAIM);
        return true;
    }

    /**
     * 购买商品
     * @param stack 货币
     */
    public void purchase(ItemStack stack){
        if (this.choose == 0){
            return;
        }

        int price = this.getPrice(this.choose - 1);
        int slot = this.getEmptySlot(InventoryAllocation.CLAIM);

        if (stack.getCount() < price || slot == -1 || !this.canSaveCurrency(price)){
            return;
        }

        Allocation allocation = Arrays.stream(InventoryAllocation.values()).toList().get(this.choose - 1);
        ItemStack commodity = this.getCommodityItemStack(allocation);
        if (commodity.isEmpty()){
            return;
        }

        if (!this.saveCurrency(stack, price)){
            return;
        }

        this.setItemStack(slot, commodity.getItem().getDefaultStack(), InventoryAllocation.CLAIM);
        commodity.decrement(1);
        this.syncInventory();
    }

    protected ItemStack pick(Allocation allocation){
        ItemStack stack = this.getCommodityItemStack(allocation);
        if (stack.isEmpty()){
            return stack;
        }

        ItemStack pick = stack.copy();
        stack.setCount(0);
        return pick;
    }

    /**
     * 取出已购买商品
     * @return 商品
     */
    public ItemStack pickUp(){
        ItemStack pick = this.pick(InventoryAllocation.CLAIM);
        this.syncInventory();
        return pick;
    }

    /**
     * 取出货币
     * @return 货币
     */
    public ItemStack pickUpCurrency(){
        ItemStack stack = this.pick(InventoryAllocation.CURRENCY_CLAIM);
        if (stack.isEmpty()){
            stack = this.pick(InventoryAllocation.CURRENCY);
            this.syncInventory();
        }
        return stack;
    }

    /**
     * 选择商品
     * @return 商品索引
     */
    public int choose(){
        this.choose++;
        if (choose == 10){
            this.choose = 0;
            return 0;
        }
        return this.choose;
    }

    /**
     * 获取商品价格
     * @param choose 商品索引
     * @return 商品价格
     */
    public int getPrice(int choose) {
        return this.prices[choose];
    }

    /**
     * 增加选中商品价格
     */
    public void addPrice(){
        if (this.choose -1 < 0){
            return;
        }

        if (this.prices[this.choose-1] == 64){
            this.prices[this.choose-1] = 0;
        } else {
            this.prices[this.choose-1] += 1;
        }
        this.syncPrice();
    }

    /**
     * 降低选中商品价格
     */
    public void dropPrice(){
        if (this.choose -1 < 0){
            return;
        }

        if (this.prices[this.choose-1] == 0){
            return;
        }

        this.prices[this.choose] -= 1;
        this.syncPrice();
    }

    /**
     * 设置选中商品价格
     * @param prices 价格
     */
    public void setPrice(int[] prices){
        this.prices = prices;
    }

    /**
     * 向客户端同步商品价格
     */
    public void syncPrice(){
        if (this.getWorld().isClient()){
            return;
        }

        PacketByteBuf data = PacketByteBufs.create();
        data.writeBlockPos(this.getPos());
        data.writeIntArray(this.prices);

        for (ServerPlayerEntity player: PlayerLookup.tracking((ServerWorld) this.world, this.pos)){
            ServerPlayNetworking.send(player, ModServerMessage.SYNC_VENDING_MACHINE_PRICE, data);
        }
    }

    /**
     * 获取商品列表
     * @return 商品列表
     */
    public ItemStack[] getCommodity(){
        ItemStack[] stacks = new ItemStack[9];
        Allocation[] allocation = InventoryAllocation.values();
        for(int index = 0; index < stacks.length; index++){
            if (!this.isSlot(allocation[index])){
                break;
            }

            stacks[index] = this.getItemStack(0, allocation[index]);
        }
        return stacks;
    }

    /**
     * 存入商品
     * @param itemStack 商品
     * @return 成功 true
     */
    public boolean saveItem(ItemStack itemStack){
        if (itemStack.isEmpty()){
            return false;
        }

        for(Allocation allocation: InventoryAllocation.values()){
            ItemStack stack = this.getItemStack(0, allocation);
            if (!this.isSlot(allocation)){
                if (!this.getItemStack(0, InventoryAllocation.CURRENCY).isEmpty() || allocation != InventoryAllocation.CURRENCY) {
                    return false;
                }

                this.setItemStack(0, new ItemStack(itemStack.getItem(), 1), allocation);
                itemStack.decrement(1);
                break;
            }

            if (stack.isEmpty()){
                this.setItemStack(0, itemStack.copy(), allocation);
                itemStack.setCount(0);
                break;
            }

            if (!itemStack.isOf(stack.getItem())){
                continue;
            }

            int slot = this.getEmptySlot(allocation);
            if (slot == -1){
                return false;
            }

            this.setItemStack(slot, itemStack.copy(), allocation);
            itemStack.setCount(0);
            break;
        }

        this.syncInventory();
        return true;
    }

    @Override
    public void syncAll() {
        super.syncAll();
        this.syncPrice();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("prices", NbtElement.INT_ARRAY_TYPE)){
            this.prices = nbt.getIntArray("prices");
        }

        if (nbt.contains("merchant")){
            this.merchantUuid = nbt.getUuid("merchant");
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putIntArray("prices", this.prices);
        if (this.merchantUuid != null){
            nbt.putUuid("merchant", this.merchantUuid);
        }
    }

    @Override
    public Text getName() {
        return null;
    }

    /**
     * 库存分配
     *<p/>
     * SLOT_1 ~ SLOT_9 商品
     * CURRENCY 货币种类
     * CLAIM  已购买商品
     */
    public enum InventoryAllocation implements Allocation{
        SLOT_1(9),
        SLOT_2(9),
        SLOT_3(9),
        SLOT_4(9),
        SLOT_5(9),
        SLOT_6(9),
        SLOT_7(9),
        SLOT_8(9),
        SLOT_9(9),
        CURRENCY(1),
        CLAIM(9),
        CURRENCY_CLAIM(27);

        private final int size;

        InventoryAllocation(int size){
            this.size = size;
        }

        @Override
        public int getSize() {
            return this.size;
        }

        @Override
        public Allocation[] getAllocations() {
            return InventoryAllocation.values();
        }
    }
}
